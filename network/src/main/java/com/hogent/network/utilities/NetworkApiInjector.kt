package com.hogent.network.utilities

import com.hogent.network.ApiService
import com.hogent.network.NetworkDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://dikke-ploaten-backend.herokuapp.com/api/"

/**
 * This class injects the network API, necessary for [Retrofit] calls and init [Moshi] objects.
 */
object NetworkApiInjector {

    /**
     * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
     * full Kotlin compatibility.
     */
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
     * object.
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(
            ApiService::class.java
        )
    }

    fun provideNetworkDataSource() =
        NetworkDataSource(retrofitService)
}
