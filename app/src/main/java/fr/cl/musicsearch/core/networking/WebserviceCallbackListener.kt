package fr.cl.musicsearch.core.networking

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface WebserviceCallbackListener {
    fun onFailure(call: Call<*>, t: Throwable)

    fun onResponse(call: Call<*>, response: Response<*>)


    companion object{

        val notConnectedCall = object : Call<String> {
            override fun enqueue(callback: Callback<String>) {}
            override fun isExecuted(): Boolean {
                return true
            }
            override fun clone(): Call<String> {
                return this
            }
            override fun isCanceled(): Boolean {
                return true
            }
            override fun cancel() {}
            override fun execute(): Response<String>? {
                return null
            }
            override fun request(): Request? {
                return null
            }
        }

        val wsPreparationError = object : Call<String> {
            override fun enqueue(callback: Callback<String>) {}
            override fun isExecuted(): Boolean {
                return true
            }
            override fun clone(): Call<String> {
                return this
            }
            override fun isCanceled(): Boolean {
                return true
            }
            override fun cancel() {}
            override fun execute(): Response<String>? {
                return null
            }
            override fun request(): Request? {
                return null
            }
        }
    }
}

class ThrowNonConnected() : Throwable()