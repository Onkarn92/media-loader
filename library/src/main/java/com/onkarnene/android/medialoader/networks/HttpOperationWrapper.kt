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

internal class HttpOperationWrapper constructor(
		private val isSynchronous: Boolean = true,
		private val call: Call,
		private val httpCallbacks: HttpCallback
) {
	
	fun init() {
		if (isSynchronous) {
			executeRequest()
		} else {
			enqueueRequest()
		}
	}
	
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
		
		fun onResponse(
				call: Call,
				result: ByteArray? = null,
				error: Pair<String?, Throwable?>,
				body: ResponseBody? = null
		)
	}
}