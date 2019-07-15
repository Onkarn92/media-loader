/*
 * Created by Onkar Nene on 15/7/19 9:16 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.onkarnene.android.medialoader.MediaLoader
import com.onkarnene.android.medialoader.sample.R
import com.onkarnene.android.medialoader.sample.adapters.UserAdapter.ViewHolder
import com.onkarnene.android.medialoader.sample.models.randomuser.User
import kotlinx.android.synthetic.main.item_user_list.view.*

class UserAdapter : RecyclerView.Adapter<ViewHolder>() {
	
	private var items: ArrayList<User>? = null
	
	override fun onCreateViewHolder(
			parent: ViewGroup,
			viewType: Int
	): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false))
	
	override fun getItemCount(): Int = items?.size ?: 0
	
	override fun onBindViewHolder(
			holder: ViewHolder,
			position: Int
	) {
		val user = items?.get(position)
		if (user != null) {
			holder.setData(user)
		}
	}
	
	fun setItems(users: ArrayList<User>) {
		items = users
		notifyDataSetChanged()
	}
	
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		
		fun setData(user: User) {
			itemView.name.text = user.name?.title.plus(" ").plus(user.name?.first).plus(" ").plus(user.name?.last)
			itemView.gender.text = user.gender
			itemView.phone.text = user.phone
			val url = user.picture?.thumbnail
			if (url != null) {
				val loader = MediaLoader.Builder<AppCompatImageView>(itemView.context).load(url).into(itemView.profileImage).create()
				loader.configMemoryCache(500, 60000)
				loader.download()
			}
		}
	}
}