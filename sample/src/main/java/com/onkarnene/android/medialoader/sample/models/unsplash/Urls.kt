/*
 * Created by Onkar Nene on 15/7/19 7:17 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.unsplash

import com.google.gson.annotations.SerializedName

data class Urls(
		@SerializedName("raw") var raw: String = "",
		
		@SerializedName("full") var full: String = "",
		
		@SerializedName("regular") var regular: String = "",
		
		@SerializedName("small") var small: String = "",
		
		@SerializedName("thumb") var thumb: String = ""
)