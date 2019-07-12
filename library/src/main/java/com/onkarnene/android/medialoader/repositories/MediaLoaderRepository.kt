/*
 * Created by Onkar Nene on 11/7/19 12:40 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.repositories

import com.onkarnene.android.medialoader.networks.Downloader
import com.onkarnene.android.medialoader.networks.Downloader.DownloaderCallback

object MediaLoaderRepository {
	
	fun initDownloader(
			isSynchronous: Boolean,
			url: String,
			callback: DownloaderCallback
	): Downloader {
		val downloader = Downloader(url)
		downloader.init(isSynchronous, callback)
		return downloader
	}
	
	fun cancelLoad(downloader: Downloader) {
		downloader.cancel()
	}
}