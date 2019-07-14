/*
 * Created by Onkar Nene on 9/7/19 5:35 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.onkarnene.android.medialoader.MediaLoader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
	private val full =
			"https://images.unsplash.com/photo-1464547323744-4edd0cd0c746?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=17b4934ff6fe2e8773896c87aa4ae85b"
	
	private val regular =
			"https://images.unsplash.com/photo-1464547323744-4edd0cd0c746?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=1080&fit=max&s=c990dc1cd803124b9e6c43b97c844ad3"
	
	private val url1 =
			"https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max&s=9fba74be19d78b1aa2495c0200b9fbce"
	
	private val url2 =
			"https://images.unsplash.com/photo-1464550838636-1a3496df938b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max&s=9947985b2095f1c8fbbbe09a93b9b1d9"
	
	private val url3 =
			"https://images.unsplash.com/photo-1464550580740-b3f73fd373cb?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max&s=32eedaa5d930578ff89cff9195472650"
	
	private val url4 =
			"https://images.unsplash.com/photo-1464547323744-4edd0cd0c746?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max&s=94a7331fc80787d57ba3b4b0c757131f"
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}
	
	override fun onResume() {
		super.onResume()
		MediaLoader.Builder<AppCompatImageView>(this).load(regular).into(image).create().download()
		val handler = Handler()
		handler.postDelayed({
			                    MediaLoader.Builder<AppCompatImageView>(this).load(regular).into(image1).create().download()
			                    MediaLoader.Builder<AppCompatImageView>(this).load(regular).into(image2).create().download()
			                    MediaLoader.Builder<AppCompatImageView>(this).load(regular).into(image3).create().download()
		                    }, 5000)
	}
}