/*
 * Created by Onkar Nene on 15/7/19 9:13 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.randomuser

import com.google.gson.annotations.SerializedName

data class Login(
		@SerializedName("uuid") var uuid: String?,
		
		@SerializedName("username") var username: String?,
		
		@SerializedName("password") var password: String?,
		
		@SerializedName("salt") var salt: String?,
		
		@SerializedName("md5") var md5: String?,
		
		@SerializedName("sha1") var sha1: String?,
		
		@SerializedName("sha256") var sha256: String?
)