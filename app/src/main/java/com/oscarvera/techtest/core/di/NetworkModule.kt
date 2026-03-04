package com.oscarvera.techtest.core.di

import com.oscarvera.techtest.data.remote.FakeStoreApi
import org.koin.dsl.module
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://fakestoreapi.com/"

val networkModule = module {

    single { OkHttpClient.Builder().build() }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<FakeStoreApi> { get<Retrofit>().create(FakeStoreApi::class.java) }

}