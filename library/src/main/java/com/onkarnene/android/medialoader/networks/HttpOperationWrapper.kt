/*
 * Created by Onkar Nene on 11/7/19 9:20 AM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.networks

import androidx.annotation.WorkerThread
import com.onkarnene.android.medialoader.utilities.NetworkUtils.getRequestFailReason
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

internal class HttpOperationWrapper {
	
	private lateinit var call: Call
	private lateinit var httpCallbacks: HttpCallback
	
	/**
	 * Initialize the HTTP call.
	 *
	 * @param call          to be execute.
	 * @param httpCallbacks of registered API wrapper.
	 * @param isSynchronous true if call is synchronous, default is false
	 */
	fun init(
			isSynchronous: Boolean = true,
			call: Call,
			httpCallbacks: HttpCallback
	) {
		this.call = call
		this.httpCallbacks = httpCallbacks
		if (isSynchronous) {
			executeRequest()
		} else {
			enqueueRequest()
		}
	}
	
	/**
	 * Enqueue Http Request and return response/failure using registered callback function.
	 * This is asynchronous call.
	 */
	private fun enqueueRequest() = call.enqueue(object : Callback {
		override fun onFailure(
				call: Call,
				e: IOException
		) {
			this@HttpOperationWrapper.httpCallbacks.onResponse(call = call, error = getRequestFailReason(throwable = e))
		}
		
		override fun onResponse(
				call: Call,
				response: Response
		) {
			try {
				val bytes = if (response.isSuccessful && response.body != null) response.body?.bytes() else null
				this@HttpOperationWrapper.httpCallbacks.onResponse(call, bytes, getRequestFailReason(response.code), response.body)
			} catch (e: IOException) {
				e.printStackTrace()
				this@HttpOperationWrapper.httpCallbacks.onResponse(call = call, error = getRequestFailReason())
			}
		}
	})
	
	/**
	 * Execute Http Request and return response/failure using registered callback function.
	 * This is synchronous call.
	 */
	@WorkerThread
	private fun executeRequest() {
		try {
			val response = call.execute()
			response.use {
				this.httpCallbacks.onResponse(call,
				                              if (response.isSuccessful && response.body != null) response.body?.bytes() else null,
				                              getRequestFailReason(response.code),
				                              response.body)
			}
		} catch (e: IOException) {
			e.printStackTrace()
			this.httpCallbacks.onResponse(call = call, error = getRequestFailReason())
		} catch (e: RuntimeException) {
			e.printStackTrace()
			this.httpCallbacks.onResponse(call = call, error = getRequestFailReason())
		}
	}
	
	interface HttpCallback {
		
		/**
		 * Callback function for any type of response.
		 *
		 * @param call      instance of executed [Call].
		 * @param result    contains response body.
		 * @param error     contains human readable exception.
		 * @param body      contains error body.
		 */
		fun onResponse(
				call: Call,
				result: ByteArray? = null,
				error: Pair<String?, Throwable?>,
				body: ResponseBody? = null
		)
	}
}