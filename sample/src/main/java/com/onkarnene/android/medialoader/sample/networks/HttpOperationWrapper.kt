/*
 * Created by Onkar Nene on 15/7/19 6:46 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.networks

import androidx.annotation.WorkerThread
import com.onkarnene.android.medialoader.sample.interfaces.HttpOperationCallback
import com.onkarnene.android.medialoader.sample.utilities.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HttpOperationWrapper<T> {
	
	private lateinit var callback: HttpOperationCallback<T>
	
	/**
	 * Initialize HTTP call.
	 * Check for internet connection.
	 *
	 * @param call              to be execute.
	 * @param callback          of registered API wrapper.
	 * @param isSynchronousCall true if call is synchronous, default is false
	 */
	fun initCall(
			call: Call<T>,
			callback: HttpOperationCallback<T>,
			isSynchronousCall: Boolean = false
	) {
		this.callback = callback
		if (NetworkUtils.isNetworkAvailable()) {
			if (isSynchronousCall) executeRequest(call) else enqueueRequest(call)
		} else {
			this.callback.onResponse(call, null, NetworkUtils.getRequestFailReason(), null)
		}
	}
	
	/**
	 * Enqueue Http Request and return response/failure using registered callback function.
	 *
	 * @param call to be executed.
	 */
	private fun enqueueRequest(call: Call<T>) = call.enqueue(object : Callback<T> {
		
		override fun onResponse(
				call: Call<T>,
				response: Response<T>
		) {
			this@HttpOperationWrapper.callback.onResponse(call,
			                                              if (NetworkUtils.isValidResponse(response)) response.body() else null,
			                                              NetworkUtils.getRequestFailReason(response.code()),
			                                              response.errorBody())
		}
		
		override fun onFailure(
				call: Call<T>,
				t: Throwable
		) {
			this@HttpOperationWrapper.callback.onResponse(call = call, errorPair = NetworkUtils.getRequestFailReason())
		}
	})
	
	/**
	 * Execute Http Request and return response/failure using registered callback function.
	 *
	 * @param call to be executed.
	 */
	@WorkerThread
	private fun executeRequest(call: Call<T>) = try {
		val response = call.execute()
		this@HttpOperationWrapper.callback.onResponse(call,
		                                              if (NetworkUtils.isValidResponse(response)) response.body() else null,
		                                              NetworkUtils.getRequestFailReason(response.code()),
		                                              response.errorBody())
	} catch (e: IOException) {
		e.printStackTrace()
		this@HttpOperationWrapper.callback.onResponse(call = call, errorPair = NetworkUtils.getRequestFailReason())
	} catch (e: RuntimeException) {
		e.printStackTrace()
		this@HttpOperationWrapper.callback.onResponse(call = call, errorPair = NetworkUtils.getRequestFailReason())
	}
}