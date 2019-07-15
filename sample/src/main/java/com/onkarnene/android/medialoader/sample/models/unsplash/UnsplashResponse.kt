/*
 * Created by Onkar Nene on 15/7/19 7:17 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.unsplash

import com.google.gson.annotations.SerializedName

data class UnsplashResponse(
		@SerializedName("id") var id: String = "",
		
		@SerializedName("created_at") var createdAt: String = "",
		
		@SerializedName("width") var width: Int = 0,
		
		@SerializedName("height") var height: Int = 0,
		
		@SerializedName("color") var color: String = "",
		
		@SerializedName("likes") var likes: Int = 0,
		
		@SerializedName("liked_by_user") var likedByUser: Boolean = false,
		
		@SerializedName("user") var user: User = User(),
		
		@SerializedName("current_user_collections") var currentUserCollections: List<Any> = listOf(),
		
		@SerializedName("urls") var urls: Urls = Urls(),
		
		@SerializedName("categories") var categories: List<Category> = listOf(),
		
		@SerializedName("links") var links: LinksX = LinksX()
)