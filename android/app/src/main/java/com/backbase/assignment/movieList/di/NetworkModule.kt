package com.backbase.assignment.movieList.di

import com.backbase.assignment.BuildConfig
import com.backbase.assignment.core.EntityMapper
import com.backbase.assignment.core.debug
import com.backbase.assignment.movieList.models.Movie
import com.backbase.assignment.movieList.models.MovieDetail
import com.backbase.assignment.movieList.network.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideGsonBuilder() = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okhttp = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)

        debug {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            okhttp.addNetworkInterceptor(logging)
        }

        return okhttp.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) =
            Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Singleton
    @Provides
    fun provideMovieWebService(retrofit: Retrofit) =
            retrofit.create(MovieWebService::class.java)

    @Singleton
    @Provides
    @Named("api_key")
    fun provideApiKey() = BuildConfig.PRIVATE_KEY

    @Singleton
    @Provides
    fun provideMovieEntityMapper(mapper: MovieEntityMapper): EntityMapper<MovieEntity, List<Movie>> = mapper

    @Singleton
    @Provides
    fun provideMovieDetailEntityMapper(mapper: MovieDetailEntityMapper): EntityMapper<MovieDetailEntity, MovieDetail> = mapper
}