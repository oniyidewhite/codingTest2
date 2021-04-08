package com.backbase.assignment.movieList.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieWebService {
    @GET("movie/now_playing")
    suspend fun nowPlaying(@Query("api_key") key: String): MovieEntity

    @GET("movie/popular")
    suspend fun popular(@Query("page") page: Int, @Query("api_key") key: String): MovieEntity

    @GET("movie/{id}")
    suspend fun details(@Path("id") id: String, @Query("api_key") key: String): MovieDetailEntity
}