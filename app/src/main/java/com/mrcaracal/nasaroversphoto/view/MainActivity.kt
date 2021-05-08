package com.mrcaracal.nasaroversphoto.view

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.mrcaracal.nasaroversphoto.R
import com.mrcaracal.nasaroversphoto.adapter.NasaAdapter
import com.mrcaracal.nasaroversphoto.model.Photo
import com.mrcaracal.nasaroversphoto.util.RecyclerClick
import com.mrcaracal.nasaroversphoto.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerClick {

    private lateinit var viewmodel: MainViewModel
    private lateinit var nasaAdapter: NasaAdapter

    private var carName = "spirit"
    private var sol_number = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val str_tab_name = tab?.text.toString().toLowerCase()
                carName = str_tab_name
                viewmodel.refreshData(carName, "", sol_number)
                recycler_view.smoothScrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                recycler_view.smoothScrollToPosition(0)
            }

        })


        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewmodel.refreshData(carName, "", sol_number)

        getLiveData()

        swipe_refresh_layout.setOnRefreshListener {
            recycler_view.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            viewmodel.refreshData(carName, "", sol_number)

            swipe_refresh_layout.isRefreshing = false
        }

        img_sol.setOnClickListener {
            sol_number = edt_sol.text.toString().toInt()
            viewmodel.refreshData(carName, "", sol_number)

        }

    }

    private fun getLiveData() {
        viewmodel.nasa_data.observe(this, Observer { data ->
            recycler_view.visibility = View.VISIBLE

            nasaAdapter = NasaAdapter(photos = data.photos, this)
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
                viewmodel.refreshData(carName, "fhaz", sol_number)
                return true
            }
            R.id.rhaz -> {
                Toast.makeText(this@MainActivity, "RHAZ", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "rhaz", sol_number)
                true
            }
            R.id.mast -> {
                Toast.makeText(this@MainActivity, "MAST", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "mast", sol_number)
                true
            }
            R.id.chemcam -> {
                Toast.makeText(this@MainActivity, "CHENCAM", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "chencam", sol_number)
                true
            }
            R.id.mahli -> {
                Toast.makeText(this@MainActivity, "MAHLI", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "mahli", sol_number)
                true
            }
            R.id.mardi -> {
                Toast.makeText(this@MainActivity, "MARDI", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "mardi", sol_number)
                true
            }
            R.id.navcam -> {
                Toast.makeText(this@MainActivity, "NAVCAM", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "navcam", sol_number)
                true
            }
            R.id.pancam -> {
                Toast.makeText(this@MainActivity, "PANCAM", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "pancam", sol_number)
                true
            }
            R.id.minites -> {
                Toast.makeText(this@MainActivity, "MINITES", Toast.LENGTH_SHORT).show()
                viewmodel.refreshData(carName, "minites", sol_number)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun openWindow(photoClick: Photo) {

        val window = PopupWindow(this)
        val view = layoutInflater.inflate(R.layout.layout_popup, null)
        window.contentView = view
        window.showAtLocation(
            tabLayout,
            Gravity.CENTER,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val cardNasa = view.findViewById<CardView>(R.id.popup_card)
        cardNasa.setOnClickListener {
            window.dismiss()
        }

        var pictureMars = view.findViewById<ImageView>(R.id.popup_resim)
        var cekildigiTarihMars = view.findViewById<TextView>(R.id.popup_cekildigi_tarih)
        var aracAdiMars = view.findViewById<TextView>(R.id.popup_arac_adi)
        var kameraAdiMars = view.findViewById<TextView>(R.id.popup_kamera_adi)
        var gorevDurumuMars = view.findViewById<TextView>(R.id.popup_gorev_durumu)
        var firlatmaMars = view.findViewById<TextView>(R.id.popup_firlatma_tarihi)
        var inisMars = view.findViewById<TextView>(R.id.popup_inis_tarihi)

        val image = "${photoClick.imgSrc}".replace("http", "https")
        Glide.with(this).load(image).into(pictureMars)
        cekildigiTarihMars.text = "Date: " + photoClick.earthDate
        aracAdiMars.text = "Rover Name: " + photoClick.rover.name
        kameraAdiMars.text = "Camera Name: " + photoClick.camera.name
        gorevDurumuMars.text = "Status: " + photoClick.rover.status
        firlatmaMars.text = "Launch Date: " + photoClick.rover.launchDate
        inisMars.text = "Landing Date: " + photoClick.rover.landingDate

    }

}