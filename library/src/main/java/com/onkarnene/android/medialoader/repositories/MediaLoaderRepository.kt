/*
 * Created by Onkar Nene on 11/7/19 12:40 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.repositories

import com.onkarnene.android.medialoader.components.DaggerCacheComponent
import com.onkarnene.android.medialoader.components.DaggerMediaLoaderComponent
import com.onkarnene.android.medialoader.networks.Downloader
import com.onkarnene.android.medialoader.networks.Downloader.DownloaderCallback

internal object MediaLoaderRepository {
	
	private val lock = Any()
	
	private val component = DaggerMediaLoaderComponent.builder().cacheComponent(DaggerCacheComponent.create()).build()
	
	/**
	 * Check if data is cached or not.
	 * @return true if and only if data is cached and not expired.
	 */
	fun isCached(
			url: String,
			isMemoryCache: Boolean = true,
			callback: DownloaderCallback
	): Boolean = synchronized(lock) {
		val value = if (isMemoryCache) component.getMemoryCache().get(url) else component.getDiskCache().get(url)
		if (value != null) {
			callback.onSuccess(value.first, value.second)
			return@synchronized true
		}
		false
	}
	
	/**
	 * @return executed Downloader instance.
	 */
	fun getDownloader(
			isSynchronous: Boolean,
			url: String,
			isMemoryCache: Boolean = true,
			callback: DownloaderCallback
	): Downloader = synchronized(lock) {
		val downloader = component.getDownloader()
		downloader.init(isSynchronous,
		                url,
		                if (isMemoryCache) component.getMemoryCache() else component.getDiskCache(),
		                callback,
		                component.getHttpOperationWrapper())
		downloader
	}
	
	/**
	 * Force executed call to be cancel.
	 */
	fun cancelLoad(downloader: Downloader) {
		synchronized(lock) {
			downloader.cancel()
		}
	}
}