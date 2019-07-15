/*
 * Created by Onkar Nene on 14/7/19 1:10 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.modules

import com.onkarnene.android.medialoader.networks.Downloader
import com.onkarnene.android.medialoader.networks.HttpOperationWrapper
import dagger.Module
import dagger.Provides

/**
 * Dagger implementation for NetworkModule
 */
@Module
internal class NetworkModule {
	
	/**
	 * Responsible to provide Downloader instance.
	 */
	@Provides
	fun provideDownloader(): Downloader = Downloader()
	
	/**
	 * Responsible to provide HttpOperationWrapper instance.
	 */
	@Provides
	fun provideHttpOperationWrapper(): HttpOperationWrapper = HttpOperationWrapper()
}