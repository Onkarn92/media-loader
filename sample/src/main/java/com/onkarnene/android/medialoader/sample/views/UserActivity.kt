/*
 * Created by Onkar Nene on 15/7/19 6:09 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.onkarnene.android.medialoader.sample.R
import com.onkarnene.android.medialoader.sample.adapters.UserAdapter
import com.onkarnene.android.medialoader.sample.interfaces.HttpEventTracker
import com.onkarnene.android.medialoader.sample.models.randomuser.RandomUserResponse
import com.onkarnene.android.medialoader.sample.services.RandomUserResponseRouter
import kotlinx.android.synthetic.main.activity_user.*
import okhttp3.ResponseBody
import org.jetbrains.anko.longToast

class UserActivity : AppCompatActivity(), HttpEventTracker<RandomUserResponse> {
	
	private lateinit var router: RandomUserResponseRouter
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_user)
		userRecyclerView.layoutManager = LinearLayoutManager(this)
		userRecyclerView.adapter = UserAdapter()
	}
	
	override fun onStart() {
		super.onStart()
		router = RandomUserResponseRouter(this)
	}
	
	override fun onResume() {
		super.onResume()
		router.init()
	}
	
	override fun onStop() {
		super.onStop()
		router.stop()
	}
	
	override fun onCallSuccess(response: RandomUserResponse) {
		(userRecyclerView.adapter as UserAdapter).setItems(response.users)
	}
	
	override fun onCallFail(
			cause: String,
			throwable: Throwable,
			responseBody: ResponseBody?
	) {
		longToast("$cause -> ${throwable.message}")
	}
}