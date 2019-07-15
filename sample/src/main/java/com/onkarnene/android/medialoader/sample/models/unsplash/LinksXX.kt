/*
 * Created by Onkar Nene on 15/7/19 7:17 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.unsplash

import com.google.gson.annotations.SerializedName

data class LinksXX(
		@SerializedName("self") var self: String = "",
		
		@SerializedName("html") var html: String = "",
		
		@SerializedName("photos") var photos: String = "",
		
		@SerializedName("likes") var likes: String = ""
)