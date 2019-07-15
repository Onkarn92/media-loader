/*
 * Created by Onkar Nene on 15/7/19 9:11 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.randomuser

import com.google.gson.annotations.SerializedName

data class Dob(
		@SerializedName("date") var date: String?,
		
		@SerializedName("age") var age: Int = 0
)