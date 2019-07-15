/*
 * Created by Onkar Nene on 15/7/19 6:52 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.onkarnene.android.medialoader.MediaLoader
import com.onkarnene.android.medialoader.sample.R
import com.onkarnene.android.medialoader.sample.adapters.UnsplashAdapter.ViewHolder
import com.onkarnene.android.medialoader.sample.models.unsplash.UnsplashResponse
import kotlinx.android.synthetic.main.item_unsplash_list.view.*

class UnsplashAdapter : RecyclerView.Adapter<ViewHolder>() {
	
	private var items: ArrayList<UnsplashResponse>? = null
	
	override fun onCreateViewHolder(
			parent: ViewGroup,
			viewType: Int
	): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_unsplash_list, parent, false))
	
	override fun getItemCount(): Int = items?.size ?: 0
	
	override fun onBindViewHolder(
			holder: ViewHolder,
			position: Int
	) {
		val unsplashResponse = items?.get(position)
		if (unsplashResponse != null) holder.setData(unsplashResponse)
	}
	
	fun setItems(items: ArrayList<UnsplashResponse>) {
		this.items = items
		notifyDataSetChanged()
	}
	
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		
		@SuppressLint("SetTextI18n")
		fun setData(unsplashResponse: UnsplashResponse) {
			this.itemView.unsplashUserNameText.text = unsplashResponse.user.name
			this.itemView.unsplashUserIdText.text = "ID: ${unsplashResponse.user.id}"
			this.itemView.unsplashLikesText.text = unsplashResponse.likes.toString()
			val drawable = if (unsplashResponse.likedByUser) R.drawable.ic_thumb_up_activated else R.drawable.ic_thumb_up_deactivated
			this.itemView.unsplashLikesText.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)
			MediaLoader.Builder<AppCompatImageView>(this.itemView.context).load(unsplashResponse.user.profileImage.medium)
					.into(this.itemView.unsplashUserProfileImage).create().download()
			MediaLoader.Builder<AppCompatImageView>(this.itemView.context).load(unsplashResponse.urls.thumb).into(this.itemView.unsplashImage)
					.create().download()
		}
	}
}