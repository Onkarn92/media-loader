/*
 * Created by Onkar Nene on 15/7/19 12:20 AM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader

import android.content.Context
import android.widget.ImageView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
class MediaLoaderTest {
	
	private val validUrl = "https://techcrunch.com/wp-content/uploads/2018/05/00100dportrait_00100_burst20180227132234437_cover.jpg?w=730&crop=1"
	private val invalidUrl = "htps://techcrunch.com/wp-content/uploads/2018/05/00100dportrait_00100_burst20180227132234437_cover.jpg?w=730&crop=1"
	
	private lateinit var appContext: Context
	
	@Before
	fun initTestWith() {
		appContext = InstrumentationRegistry.getInstrumentation().targetContext
	}
	
	@Throws(IllegalArgumentException::class)
	@Test(expected = IllegalArgumentException::class)
	fun mediaLoader_EmptyUrl_throwsException() {
		MediaLoader.Builder<ImageView>(appContext).into(ImageView(appContext)).create()
	}
	
	@Test
	fun mediaLoader_ValidUrl_ReturnsTrue() {
		MediaLoader.Builder<ImageView>(appContext).load(validUrl).into(ImageView(appContext)).create()
		Assert.assertTrue(true)
	}
	
	@Throws(IllegalArgumentException::class)
	@Test(expected = IllegalArgumentException::class)
	fun mediaLoader_InvalidUrl_throwsException() {
		MediaLoader.Builder<ImageView>(appContext).load(invalidUrl).into(ImageView(appContext)).create()
	}
	
	@Throws(IllegalArgumentException::class)
	@Test(expected = IllegalArgumentException::class)
	fun mediaLoader_EmptyView_throwsException() {
		MediaLoader.Builder<ImageView>(appContext).load(validUrl).create()
	}
}