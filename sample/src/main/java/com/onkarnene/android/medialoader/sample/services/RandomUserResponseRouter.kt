/*
 * Created by Onkar Nene on 15/7/19 9:33 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.services

import com.onkarnene.android.medialoader.sample.interfaces.HttpEventTracker
import com.onkarnene.android.medialoader.sample.interfaces.HttpOperationCallback
import com.onkarnene.android.medialoader.sample.interfaces.RandomUserResponseCallback
import com.onkarnene.android.medialoader.sample.models.randomuser.RandomUserResponse
import com.onkarnene.android.medialoader.sample.networks.HttpOperationWrapper
import com.onkarnene.android.medialoader.sample.utilities.NetworkUtils
import okhttp3.ResponseBody
import retrofit2.Call

class RandomUserResponseRouter(private val eventTracker: HttpEventTracker<RandomUserResponse>) : HttpOperationCallback<RandomUserResponse> {
	
	private lateinit var call: Call<RandomUserResponse>
	
	private val randomUserResponseCallback: RandomUserResponseCallback by lazy {
		NetworkUtils.getRetrofitBuilder("https://randomuser.me/").build().create(RandomUserResponseCallback::class.java)
	}
	
	fun init() {
		call = randomUserResponseCallback.getUsers()
		HttpOperationWrapper<RandomUserResponse>().initCall(call, this)
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
			call: Call<RandomUserResponse>,
			result: RandomUserResponse?,
			errorPair: Pair<String, Throwable?>,
			errorBody: ResponseBody?
	) {
		when {
			result != null -> eventTracker.onCallSuccess(result)
			else -> eventTracker.onCallFail(errorPair.first, errorPair.second ?: Throwable("Unknown..."), errorBody)
		}
	}
}