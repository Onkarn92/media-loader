/*
 * Created by Onkar Nene on 15/7/19 9:32 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.interfaces

import com.onkarnene.android.medialoader.sample.models.randomuser.RandomUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserResponseCallback {
	
	@GET("api")
	fun getUsers(@Query("results") count: Int = 1000): Call<RandomUserResponse>
}