/*
 * Created by Onkar Nene on 15/7/19 9:13 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.randomuser

import com.google.gson.annotations.SerializedName

data class RandomUserResponse(
		@SerializedName("results") var users: ArrayList<User> = arrayListOf(),
		
		@SerializedName("info") var info: Info = Info(),
		
		@SerializedName("error") var error: String = ""
)