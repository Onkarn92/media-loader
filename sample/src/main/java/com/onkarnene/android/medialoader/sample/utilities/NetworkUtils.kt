/*
 * Created by Onkar Nene on 15/7/19 6:28 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.utilities

import android.content.Context
import android.net.ConnectivityManager
import com.onkarnene.android.medialoader.sample.App
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit.*

object NetworkUtils {
	
	const val RANDOM_USER_API_URL = ""
	private const val CODE_UNKNOWN: Int = 0
	private const val HTTP_REQUEST_FAIL = "Request Fail"
	private val BAD_REQUEST = "Bad Request" to Throwable("The requested data is invalid, Please try again later.")
	private val UNAUTHORIZED = "User Unauthorized" to Throwable("This user is not authorized to access the data.")
	private val PAGE_NOT_FOUND = "Page Not Found" to Throwable("The server page you are looking for is not available.")
	private val TIMEOUT = "Request Timeout" to Throwable("Due to the server or network unavailability request cannot proceed.")
	private val MAINTENANCE_BREAK = "Maintenance Break" to Throwable("Sorry, the server is under maintenance. Please try again after some time.")
	
	/**
	 * Creates single instance of retrofit.
	 * Set headers created by {{@link HttpRequestHeader#getInstance()}}
	 *
	 * @return retrofit instance.
	 */
	private val retrofitBuilder: Retrofit.Builder by lazy {
		val client = OkHttpClient().newBuilder().connectTimeout(30, SECONDS).readTimeout(30, SECONDS).build()
		Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
	}
	
	fun getRetrofitBuilder(url: String): Retrofit.Builder {
		if (url.isEmpty()) {
			throw NullPointerException("Url should not be empty.")
		}
		if (!url.last().equals('/', true)) {
			throw IllegalArgumentException("Url must end with /")
		}
		return retrofitBuilder.baseUrl(url)
	}
	
	/**
	 * Check for response validity with following conditions:
	 * NotNull, Non-empty body, {[Response.isSuccessful]}
	 *
	 * @param response object to be examined.
	 *
	 * @return true if response is valid, otherwise false.
	 */
	fun isValidResponse(response: Response<*>): Boolean = response.isSuccessful && response.body() != null
	
	fun getRequestFailReason(
			code: Int = CODE_UNKNOWN,
			throwable: Throwable? = null
	) = when (code) {
		CODE_UNKNOWN -> HTTP_REQUEST_FAIL to (throwable ?: Throwable("Something went wrong!"))
		HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_BAD_METHOD, HttpURLConnection.HTTP_UNSUPPORTED_TYPE -> BAD_REQUEST
		HttpURLConnection.HTTP_UNAUTHORIZED -> UNAUTHORIZED
		HttpURLConnection.HTTP_NOT_FOUND, HttpURLConnection.HTTP_NOT_ACCEPTABLE -> PAGE_NOT_FOUND
		HttpURLConnection.HTTP_CLIENT_TIMEOUT, HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> TIMEOUT
		HttpURLConnection.HTTP_INTERNAL_ERROR, HttpURLConnection.HTTP_BAD_GATEWAY, HttpURLConnection.HTTP_UNAVAILABLE -> MAINTENANCE_BREAK
		else -> "" to null
	}
	
	/**
	 * Check for internet connection availability.
	 *
	 * @return true if device is currently connected to the internet (WiFi or Mobile Data), otherwise false.
	 */
	fun isNetworkAvailable(): Boolean {
		val networkInfo = (App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.activeNetworkInfo
		return networkInfo != null && networkInfo.isConnected
	}
}