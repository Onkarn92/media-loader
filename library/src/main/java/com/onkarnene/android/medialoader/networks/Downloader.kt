/*
 * Created by Onkar Nene on 12/7/19 11:02 AM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.networks

import com.onkarnene.android.medialoader.data.Cache
import com.onkarnene.android.medialoader.utilities.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.ResponseBody

internal class Downloader(
		private val url: String,
		private val cache: Cache,
		private val callback: DownloaderCallback
) : HttpOperationWrapper.HttpCallback {
	
	private lateinit var call: Call
	
	override fun onResponse(
			call: Call,
			result: ByteArray?,
			error: Pair<String?, Throwable?>,
			body: ResponseBody?
	) {
		if (result != null) {
			val mediaType = body?.contentType()
			if (mediaType != null) {
				cache.put(url, result to mediaType)
			}
			callback.onSuccess(result, mediaType)
		} else {
			callback.onFailed(error, body)
		}
	}
	
	fun init(isSynchronous: Boolean) {
		call = NetworkUtils.getCaller(url)
		processRequest(isSynchronous)
	}
	
	fun cancel() {
		if (this@Downloader::call.isInitialized && !call.isCanceled() && call.isExecuted()) {
			call.cancel()
			callback.onCancel()
		}
	}
	
	private fun processRequest(isSynchronous: Boolean) {
		GlobalScope.launch(Dispatchers.IO) {
			if (this@Downloader::call.isInitialized) {
				callback.inProgress()
				HttpOperationWrapper(isSynchronous, call, this@Downloader).init()
			} else {
				callback.onCancel()
			}
		}
	}
	
	interface DownloaderCallback {
		fun onSuccess(
				result: ByteArray,
				mediaType: MediaType? = null
		)
		
		fun inProgress()
		
		fun onFailed(
				errorPair: Pair<String?, Throwable?>,
				body: ResponseBody? = null
		)
		
		fun onCancel()
	}
}