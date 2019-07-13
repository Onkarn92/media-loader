/*
 * Created by Onkar Nene on 11/7/19 12:40 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.repositories

import com.onkarnene.android.medialoader.data.Cache
import com.onkarnene.android.medialoader.networks.Downloader
import com.onkarnene.android.medialoader.networks.Downloader.DownloaderCallback

internal object MediaLoaderRepository {
	
	private val lock = Any()
	
	fun isCached(
			url: String,
			cache: Cache,
			callback: DownloaderCallback
	): Boolean = synchronized(lock) {
		val value = cache.get(url)
		if (value != null) {
			callback.onSuccess(value.first, value.second)
			return@synchronized true
		}
		false
	}
	
	fun getDownloader(
			isSynchronous: Boolean,
			url: String,
			cache: Cache,
			callback: DownloaderCallback
	): Downloader = synchronized(lock) {
		val downloader = Downloader(url, cache, callback)
		downloader.init(isSynchronous)
		downloader
	}
	
	fun cancelLoad(downloader: Downloader) {
		synchronized(lock) {
			downloader.cancel()
		}
	}
}