/*
 * Created by Onkar Nene on 15/7/19 9:13 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.randomuser

import com.google.gson.annotations.SerializedName

data class Info(
		@SerializedName("seed") var seed: String = "",
		
		@SerializedName("results") var results: Int = 0,
		
		@SerializedName("page") var page: Int = 0,
		
		@SerializedName("version") var version: String = ""
)