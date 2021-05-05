package com.mrcaracal.nasaroversphoto.service

import com.mrcaracal.nasaroversphoto.model.PhotosModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NasaAPIService {

    // https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=cJoyDKbVhUx8CLDZy0tiaE24FZX0rOdFyAdkdabD

    private val BASE_URL = "https://api.nasa.gov/mars-photos/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NasaAPI::class.java)

    fun getDataService(carName: String, cameraName: String): Single<PhotosModel> {

        if (cameraName.equals("")) {
            return api.getData2(carName)
        } else {
            return api.getData(carName, cameraName)
        }
    }

}