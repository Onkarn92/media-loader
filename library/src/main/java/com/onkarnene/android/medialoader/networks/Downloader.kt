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

/**
 * Base downloader implementation for fetching media data from remote source.
 */
internal class Downloader : HttpOperationWrapper.HttpCallback {
	
	private lateinit var call: Call
	private lateinit var url: String
	private lateinit var cache: Cache
	private lateinit var callback: DownloaderCallback
	
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
	
	/**
	 * Initialize the HTTP call.
	 */
	fun init(
			isSynchronous: Boolean,
			url: String,
			cache: Cache,
			callback: DownloaderCallback,
			httpOperationWrapper: HttpOperationWrapper
	) {
		this.call = NetworkUtils.getCaller(url)
		this.url = url
		this.cache = cache
		this.callback = callback
		GlobalScope.launch(Dispatchers.IO) {
			if (this@Downloader::call.isInitialized) {
				callback.inProgress()
				httpOperationWrapper.init(isSynchronous, call, this@Downloader)
			} else {
				callback.onCancel()
			}
		}
	}
	
	/**
	 * Cancels the current ongoing HTTP call.
	 */
	fun cancel() {
		if (this@Downloader::call.isInitialized && !call.isCanceled() && call.isExecuted()) {
			call.cancel()
			callback.onCancel()
		}
	}
	
	/**
	 * Provides callbacks to core module.
	 */
	interface DownloaderCallback {
		
		/**
		 * Calls when response is successful and response body is not empty.
		 * @param result downloaded data in byte array.
		 * @param mediaType of currently downloaded data.
		 */
		fun onSuccess(
				result: ByteArray,
				mediaType: MediaType? = null
		)
		
		/**
		 * Triggers when request is initialized and ready to execute.
		 */
		fun inProgress()
		
		/**
		 * Triggers when response is not successful or call failed due to some reason.
		 */
		fun onFailed(
				errorPair: Pair<String?, Throwable?>,
				body: ResponseBody? = null
		)
		
		/**
		 * Triggers when user manually cancels the executed call.
		 */
		fun onCancel()
	}
}