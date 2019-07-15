/*
 * Created by Onkar Nene on 15/7/19 8:34 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.interfaces

import com.onkarnene.android.medialoader.sample.models.unsplash.UnsplashResponse
import retrofit2.Call
import retrofit2.http.GET

interface UnsplashResponseCallback {
	
	@GET("raw/wgkJgazE")
	fun getData(): Call<ArrayList<UnsplashResponse>>
}