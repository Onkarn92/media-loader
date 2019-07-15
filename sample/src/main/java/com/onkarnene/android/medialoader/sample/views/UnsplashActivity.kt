/*
 * Created by Onkar Nene on 15/7/19 6:08 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.onkarnene.android.medialoader.sample.R
import com.onkarnene.android.medialoader.sample.adapters.UnsplashAdapter
import com.onkarnene.android.medialoader.sample.interfaces.HttpEventTracker
import com.onkarnene.android.medialoader.sample.models.unsplash.UnsplashResponse
import com.onkarnene.android.medialoader.sample.services.UnsplashResponseRouter
import kotlinx.android.synthetic.main.activity_unsplash.*
import okhttp3.ResponseBody
import org.jetbrains.anko.longToast

class UnsplashActivity : AppCompatActivity(), HttpEventTracker<ArrayList<UnsplashResponse>> {
	
	private lateinit var router: UnsplashResponseRouter
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_unsplash)
		unsplashRecyclerView.layoutManager = LinearLayoutManager(this)
		unsplashRecyclerView.adapter = UnsplashAdapter()
	}
	
	override fun onStart() {
		super.onStart()
		router = UnsplashResponseRouter(this)
	}
	
	override fun onResume() {
		super.onResume()
		router.init()
	}
	
	override fun onStop() {
		super.onStop()
		router.stop()
	}
	
	override fun onCallSuccess(response: ArrayList<UnsplashResponse>) {
		(unsplashRecyclerView.adapter as UnsplashAdapter).setItems(response)
	}
	
	override fun onCallFail(
			cause: String,
			throwable: Throwable,
			responseBody: ResponseBody?
	) {
		longToast("$cause -> ${throwable.message}")
	}
}