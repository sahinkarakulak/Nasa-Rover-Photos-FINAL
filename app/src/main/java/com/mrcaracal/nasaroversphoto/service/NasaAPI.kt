package com.mrcaracal.nasaroversphoto.service

import com.mrcaracal.nasaroversphoto.model.PhotosModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaAPI {

    // https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=cJoyDKbVhUx8CLDZy0tiaE24FZX0rOdFyAdkdabD

    @GET("api/v1/rovers/{carName}/photos?sol=100&&api_key=cJoyDKbVhUx8CLDZy0tiaE24FZX0rOdFyAdkdabD")
    fun getData(
        @Path("carName") carName: String,
        @Query("camera") cameraName: String,
        @Query("sol") solNumber: Int
    ): Single<PhotosModel>

    @GET("api/v1/rovers/{carName}/photos?sol=100&&api_key=cJoyDKbVhUx8CLDZy0tiaE24FZX0rOdFyAdkdabD")
    fun getData2(
        @Path("carName") carName: String,
        @Query("sol") solNumber: Int
    ): Single<PhotosModel>

}