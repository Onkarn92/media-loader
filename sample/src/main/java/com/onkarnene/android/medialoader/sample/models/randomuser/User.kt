/*
 * Created by Onkar Nene on 15/7/19 9:12 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.randomuser

import com.google.gson.annotations.SerializedName

data class User(
		@SerializedName("gender") var gender: String?,
		
		@SerializedName("name") var name: Name?,
		
		@SerializedName("location") var location: Location?,
		
		@SerializedName("email") var email: String?,
		
		@SerializedName("login") var login: Login?,
		
		@SerializedName("dob") var dob: Dob?,
		
		@SerializedName("registered") var registered: Registered?,
		
		@SerializedName("phone") var phone: String?,
		
		@SerializedName("cell") var cell: String?,
		
		@SerializedName("id") var id: Id?,
		
		@SerializedName("picture") var picture: Picture?,
		
		@SerializedName("nat") var nat: String?
)