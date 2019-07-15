/*
 * Created by Onkar Nene on 15/7/19 6:23 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.onkarnene.android.medialoader.MediaLoader
import com.onkarnene.android.medialoader.sample.R
import kotlinx.android.synthetic.main.activity_same_image.*

class SameImageActivity : AppCompatActivity() {
	private val regular = "https://upload.wikimedia.org/wikipedia/commons/2/2b/Kingsborough_CC_MB.JPG"
	
	private lateinit var imageOneMediaLoader: MediaLoader<AppCompatImageView>
	private lateinit var imageTwoMediaLoader: MediaLoader<AppCompatImageView>
	private lateinit var imageThreeMediaLoader: MediaLoader<AppCompatImageView>
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_same_image)
		cancelImageOne.setOnClickListener {
			if (this::imageOneMediaLoader.isInitialized) {
				imageOneMediaLoader.cancel()
			}
		}
		cancelImageTwo.setOnClickListener {
			if (this::imageTwoMediaLoader.isInitialized) {
				imageTwoMediaLoader.cancel()
			}
		}
		cancelImageThree.setOnClickListener {
			if (this::imageThreeMediaLoader.isInitialized) {
				imageThreeMediaLoader.cancel()
			}
		}
	}
	
	override fun onResume() {
		super.onResume()
		imageOneMediaLoader = MediaLoader.Builder<AppCompatImageView>(this).load(regular).into(imageOne).create()
		imageTwoMediaLoader = MediaLoader.Builder<AppCompatImageView>(this).load(regular).into(imageTwo).create()
		imageThreeMediaLoader = MediaLoader.Builder<AppCompatImageView>(this).load(regular).into(imageThree).create()
		
		imageOneMediaLoader.download()
		imageTwoMediaLoader.download()
		imageThreeMediaLoader.download()
	}
}