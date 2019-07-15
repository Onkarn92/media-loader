/*
 * Created by Onkar Nene on 15/7/19 8:38 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.services

import com.onkarnene.android.medialoader.sample.interfaces.HttpEventTracker
import com.onkarnene.android.medialoader.sample.interfaces.HttpOperationCallback
import com.onkarnene.android.medialoader.sample.interfaces.UnsplashResponseCallback
import com.onkarnene.android.medialoader.sample.models.unsplash.UnsplashResponse
import com.onkarnene.android.medialoader.sample.networks.HttpOperationWrapper
import com.onkarnene.android.medialoader.sample.utilities.NetworkUtils
import okhttp3.ResponseBody
import retrofit2.Call

class UnsplashResponseRouter(private val eventTracker: HttpEventTracker<ArrayList<UnsplashResponse>>) :
		HttpOperationCallback<ArrayList<UnsplashResponse>> {
	
	private lateinit var call: Call<ArrayList<UnsplashResponse>>
	
	private val unsplashResponseCallback: UnsplashResponseCallback by lazy {
		NetworkUtils.getRetrofitBuilder("http://pastebin.com/").build().create(UnsplashResponseCallback::class.java)
	}
	
	fun init() {
		call = unsplashResponseCallback.getData()
		HttpOperationWrapper<ArrayList<UnsplashResponse>>().initCall(call, this)
	}
	
	/**
	 * Cancel all Retrofit calls
	 */
	fun stop() {
		if (this::call.isInitialized && call.isExecuted && !call.isCanceled) {
			call.cancel()
		}
	}
	
	override fun onResponse(
			call: Call<ArrayList<UnsplashResponse>>,
			result: ArrayList<UnsplashResponse>?,
			errorPair: Pair<String, Throwable?>,
			errorBody: ResponseBody?
	) {
		when {
			result != null -> eventTracker.onCallSuccess(result)
			else -> eventTracker.onCallFail(errorPair.first, errorPair.second ?: Throwable("Unknown..."), errorBody)
		}
	}
}