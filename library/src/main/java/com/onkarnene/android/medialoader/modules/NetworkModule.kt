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

@Module
internal class NetworkModule {
	
	@Provides
	fun provideDownloader(): Downloader = Downloader()
	
	@Provides
	fun provideHttpOperationWrapper(): HttpOperationWrapper = HttpOperationWrapper()
}