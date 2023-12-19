package com.example.loginsample.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDataSource{
    companion object{
        // @TODO Replace me with a legitimate address
        private const val BASE_URL = "http://192.168.1.65:8000/"
    }


    fun<Api> buildApi(
        api: Class<Api>,
        authToken: String? = null
    ) : Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor{chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            // Using backend JWT for authentication, depending on application this might need to be "Bearer "
                            it.addHeader("Authorization", "JWT $authToken")
                        }.build())
                    }
                    .also { client ->
//                    if(BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
//                    }

                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}