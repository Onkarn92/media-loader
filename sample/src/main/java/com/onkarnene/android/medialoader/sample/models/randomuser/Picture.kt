/*
 * Created by Onkar Nene on 15/7/19 9:13 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.randomuser

import com.google.gson.annotations.SerializedName

data class Picture(
		@SerializedName("large") var large: String?,
		
		@SerializedName("medium") var medium: String?,
		
		@SerializedName("thumbnail") var thumbnail: String?
)