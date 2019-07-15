/*
 * Created by Onkar Nene on 15/7/19 6:25 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class App: Application() {
	
	companion object{
		private lateinit var app: App
		
		/**
		 * @return current context of this application
		 */
		@JvmStatic
		fun getContext(): Context = app.applicationContext
	}
	
	override fun onCreate() {
		super.onCreate()
		app = this
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
	}
}