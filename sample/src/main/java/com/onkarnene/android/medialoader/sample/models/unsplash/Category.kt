/*
 * Created by Onkar Nene on 15/7/19 7:17 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.unsplash

import com.google.gson.annotations.SerializedName

data class Category(
		@SerializedName("id") var id: Int = 0,
		
		@SerializedName("title") var title: String = "",
		
		@SerializedName("photo_count") var photoCount: Int = 0,
		
		@SerializedName("links") var links: Links = Links()
)