/*
 * Created by Onkar Nene on 15/7/19 7:17 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.unsplash

import com.google.gson.annotations.SerializedName

data class ProfileImage(
		@SerializedName("small") var small: String = "",
		
		@SerializedName("medium") var medium: String = "",
		
		@SerializedName("large") var large: String = ""
)