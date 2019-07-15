/*
 * Created by Onkar Nene on 10/7/19 10:13 AM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Patterns
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.onkarnene.android.medialoader.data.MemoryCache
import com.onkarnene.android.medialoader.networks.Downloader
import com.onkarnene.android.medialoader.networks.Downloader.DownloaderCallback
import com.onkarnene.android.medialoader.repositories.MediaLoaderRepository
import com.onkarnene.android.medialoader.utilities.CONTENT_TYPE_JPEG
import com.onkarnene.android.medialoader.utilities.CONTENT_TYPE_JPG
import com.onkarnene.android.medialoader.utilities.CONTENT_TYPE_JSON
import com.onkarnene.android.medialoader.utilities.CONTENT_TYPE_PDF
import com.onkarnene.android.medialoader.utilities.CONTENT_TYPE_PNG
import com.onkarnene.android.medialoader.utilities.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.ResponseBody
import java.lang.ref.WeakReference

/**
 * Core module; Responsible for handling every request and loading respective responses in provide views.
 */
@Suppress("unused")
class MediaLoader<T : View> private constructor(
		private val appContext: Context,
		private val url: String,
		private val isSynchronous: Boolean,
		private val weakView: WeakReference<T>,
		private val placeholder: Int,
		private val errorPlaceholder: Int
) : DownloaderCallback {
	
	private lateinit var downloader: Downloader
	
	override fun onSuccess(
			result: ByteArray,
			mediaType: MediaType?
	) {
		when ("${mediaType?.type}/${mediaType?.subtype}") {
			CONTENT_TYPE_JPG, CONTENT_TYPE_JPEG, CONTENT_TYPE_PNG -> {
				GlobalScope.launch(Dispatchers.Main) {
					val view: View = weakView.get() as View
					try {
						if (view is ImageView) {
							val bitmap = BitmapFactory.decodeByteArray(result, 0, result.size)
							if (bitmap != null) {
								view.setImageBitmap(bitmap)
								view.startAnimation(AnimationUtils.loadAnimation(appContext, android.R.anim.fade_in))
							} else {
								setImage(view, errorPlaceholder)
							}
						} else {
							throw IllegalStateException("View does not support bitmap images.")
						}
					} catch (e: OutOfMemoryError) {
						setImage(view, errorPlaceholder)
					}
				}
			}
			CONTENT_TYPE_JSON, CONTENT_TYPE_PDF -> {
				TODO("Implement file downloader")
			}
			else -> onFailed(NetworkUtils.getRequestFailReason())
		}
	}
	
	override fun inProgress() {
		GlobalScope.launch(Dispatchers.Main) {
			setImage(weakView.get() as View, placeholder)
		}
	}
	
	override fun onFailed(
			errorPair: Pair<String?, Throwable?>,
			body: ResponseBody?
	) {
		GlobalScope.launch(Dispatchers.Main) {
			setImage(weakView.get() as View, errorPlaceholder)
		}
	}
	
	override fun onCancel() {
	}
	
	/**
	 * Custom configuration for MemoryCache instance.
	 */
	fun configMemoryCache(
			capacity: Int,
			timeoutInMillis: Long
	) {
		MemoryCache.setCapacity(capacity)
		MemoryCache.setTimeout(timeoutInMillis)
	}
	
	/**
	 * Responsible to execute the request and load the result.
	 */
	fun download(useMemoryCache: Boolean = true) {
		if (!MediaLoaderRepository.isCached(url, useMemoryCache, this)) {
			downloader = MediaLoaderRepository.getDownloader(isSynchronous, url, useMemoryCache, this)
		}
	}
	
	/**
	 * Force execute http call to cancel if not completed.
	 */
	fun cancel() {
		if (this::downloader.isInitialized) {
			MediaLoaderRepository.cancelLoad(downloader)
		}
	}
	
	private fun setImage(
			view: View,
			resourceId: Int
	) {
		if (view is ImageView) {
			view.setImageResource(resourceId)
			view.startAnimation(AnimationUtils.loadAnimation(appContext, android.R.anim.fade_in))
		}
	}
	
	/**
	 * Builder pattern implementation for MediaLoader class.
	 * @param context of the application or active window.
	 */
	class Builder<T : View> constructor(private val context: Context) {
		
		private lateinit var weakView: WeakReference<T>
		private var url: String = ""
		private var placeholder: Int = R.drawable.loading_placeholder
		private var errorPlaceholder: Int = R.drawable.error_placeholder
		private var isSynchronous: Boolean = true
		
		fun load(url: String): Builder<T> {
			this.url = url
			return this
		}
		
		fun isSynchronousCall(isSynchronous: Boolean): Builder<T> {
			this.isSynchronous = isSynchronous
			return this
		}
		
		fun into(view: T): Builder<T> {
			this.weakView = WeakReference(view)
			return this
		}
		
		fun placeholder(@DrawableRes resourceId: Int): Builder<T> {
			this.placeholder = resourceId
			return this
		}
		
		fun errorPlaceholder(@DrawableRes resourceId: Int): Builder<T> {
			this.errorPlaceholder = resourceId
			return this
		}
		
		fun create(): MediaLoader<T> {
			if (url.isEmpty()) {
				throw IllegalArgumentException("Url should not be empty.")
			}
			if (!Patterns.WEB_URL.matcher(url).matches()) {
				throw IllegalArgumentException("Invalid Url detected.")
			}
			if (!this::weakView.isInitialized) {
				throw IllegalArgumentException("View should not be empty.")
			}
			return MediaLoader(this.context.applicationContext, this.url, this.isSynchronous, this.weakView, this.placeholder, this.errorPlaceholder)
		}
	}
}