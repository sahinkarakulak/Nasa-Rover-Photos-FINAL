package com.mrcaracal.nasaroversphoto.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrcaracal.nasaroversphoto.model.PhotosModel
import com.mrcaracal.nasaroversphoto.service.NasaAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private val nasaApıService = NasaAPIService()
    private val disposable = CompositeDisposable()

    val nasa_data = MutableLiveData<PhotosModel>()
    val nasa_error = MutableLiveData<Boolean>()
    val nasa_loading = MutableLiveData<Boolean>()

    fun refreshData(carName: String, cameraName: String) {

        // from API
        getDataFromAPI(carName, cameraName)

        // from Local
        getDataFromLocal()
    }

    private fun getDataFromAPI(carName: String, cameraName: String) {
        nasa_loading.value = true
        disposable.add(
            nasaApıService.getDataService(carName, cameraName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PhotosModel>() {

                    override fun onSuccess(t: PhotosModel) {
                        nasa_data.value = t
                        nasa_error.value = false
                        nasa_loading.value = false
                        Log.i(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        nasa_error.value = true
                        nasa_loading.value = false
                        Log.i(TAG, "onError: Unsuccess")
                    }

                })
        )
    }

    private fun getDataFromLocal() {

    }

}