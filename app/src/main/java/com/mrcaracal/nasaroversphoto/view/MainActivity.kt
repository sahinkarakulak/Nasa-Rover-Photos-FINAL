package com.mrcaracal.nasaroversphoto.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.mrcaracal.nasaroversphoto.R
import com.mrcaracal.nasaroversphoto.adapter.NasaAdapter
import com.mrcaracal.nasaroversphoto.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=cJoyDKbVhUx8CLDZy0tiaE24FZX0rOdFyAdkdabD

    private lateinit var viewmodel: MainViewModel
    private lateinit var nasaAdapter: NasaAdapter

    private var carName = "spirit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val str_tab_name = tab?.text.toString().toLowerCase()
                carName = str_tab_name
                viewmodel.refreshData(carName, "")
                recycler_view.smoothScrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                recycler_view.smoothScrollToPosition(0)
            }

        })


        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewmodel.refreshData(carName, "")

        getLiveData()

        swipe_refresh_layout.setOnRefreshListener {
            recycler_view.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            viewmodel.refreshData(carName, "")

            swipe_refresh_layout.isRefreshing = false
        }

    }

    private fun getLiveData() {
        viewmodel.nasa_data.observe(this, Observer { data ->
            recycler_view.visibility = View.VISIBLE

            nasaAdapter = NasaAdapter(photos = data.photos)
            recycler_view.layoutManager = GridLayoutManager(this@MainActivity, 2)
            recycler_view.adapter = nasaAdapter

        })

        viewmodel.nasa_error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    tv_error.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    recycler_view.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        })

        viewmodel.nasa_loading.observe(this, Observer { load ->
            load?.let {
                if (load) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    recycler_view.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.camera_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fhaz -> {
                Toast.makeText(this@MainActivity, "fhaz", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "fhaz")
                return true
            }
            R.id.rhaz -> {
                Toast.makeText(this@MainActivity, "RHAZ", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "rhaz")
                true
            }
            R.id.mast -> {
                Toast.makeText(this@MainActivity, "MAST", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "mast")
                true
            }
            R.id.chemcam -> {
                Toast.makeText(this@MainActivity, "CHENCAM", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "chencam")
                true
            }
            R.id.mahli -> {
                Toast.makeText(this@MainActivity, "MAHLI", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "mahli")
                true
            }
            R.id.mardi -> {
                Toast.makeText(this@MainActivity, "MARDI", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "mardi")
                true
            }
            R.id.navcam -> {
                Toast.makeText(this@MainActivity, "NAVCAM", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "navcam")
                true
            }
            R.id.pancam -> {
                Toast.makeText(this@MainActivity, "PANCAM", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "pancam")
                true
            }
            R.id.minites -> {
                Toast.makeText(this@MainActivity, "MINITES", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "minites")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}