/*
 * Created by Onkar Nene on 15/7/19 7:17 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.unsplash

import com.google.gson.annotations.SerializedName

data class User(
		@SerializedName("id") var id: String = "",
		
		@SerializedName("username") var username: String = "",
		
		@SerializedName("name") var name: String = "",
		
		@SerializedName("profile_image") var profileImage: ProfileImage = ProfileImage(),
		
		@SerializedName("links") var links: LinksXX = LinksXX()
)