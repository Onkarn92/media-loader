/*
 * Created by Onkar Nene on 15/7/19 6:02 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onkarnene.android.medialoader.sample.R.layout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layout.activity_main)
		tryUnsplashBtn.setOnClickListener {
			startActivity(Intent(this, UnsplashActivity::class.java))
		}
		tryUserBtn.setOnClickListener {
			startActivity(Intent(this, UserActivity::class.java))
		}
		trySameImageBtn.setOnClickListener {
			startActivity(Intent(this, SameImageActivity::class.java))
		}
	}
}