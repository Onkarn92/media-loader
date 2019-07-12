/*
 * Created by Onkar Nene on 10/7/19 10:13 AM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.annotation.DrawableRes
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

class MediaLoader<T> private constructor(
		private val appContext: Context,
		private val url: String,
		private var isSynchronous: Boolean,
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
					val bitmap = BitmapFactory.decodeByteArray(result, 0, result.size)
					if (bitmap != null && weakView.get() is ImageView) {
						(weakView.get() as ImageView).setImageBitmap(bitmap)
					} else {
						throw IllegalStateException("Response cannot be converted in bitmap or view is not supporting bitmap images.")
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
			if (weakView.get() is ImageView) {
				(weakView.get() as ImageView).setImageResource(placeholder)
			}
		}
	}
	
	override fun onFailed(
			errorPair: Pair<String?, Throwable?>,
			body: ResponseBody?
	) {
		GlobalScope.launch(Dispatchers.Main) {
			if (weakView.get() is ImageView) {
				(weakView.get() as ImageView).setImageResource(errorPlaceholder)
			}
		}
	}
	
	override fun onCancel() {
	}
	
	fun download() {
		downloader = MediaLoaderRepository.initDownloader(isSynchronous, url, this)
	}
	
	fun cancel() {
		if (this::downloader.isInitialized) {
			MediaLoaderRepository.cancelLoad(downloader)
		}
	}
	
	class Builder<T> constructor(private val context: Context) {
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
			if (!this::weakView.isInitialized) {
				throw IllegalArgumentException("View should not be empty.")
			}
			return MediaLoader(this.context.applicationContext, this.url, this.isSynchronous, this.weakView, this.placeholder, this.errorPlaceholder)
		}
	}
}