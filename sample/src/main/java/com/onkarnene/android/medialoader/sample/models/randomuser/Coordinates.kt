/*
 * Created by Onkar Nene on 15/7/19 9:10 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.randomuser

import com.google.gson.annotations.SerializedName

data class Coordinates(
		@SerializedName("latitude") var latitude: String?,
		
		@SerializedName("longitude") var longitude: String?
)