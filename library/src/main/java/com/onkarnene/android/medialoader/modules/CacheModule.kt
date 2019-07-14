/*
 * Created by Onkar Nene on 14/7/19 12:49 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.modules

import com.onkarnene.android.medialoader.data.DiskCache
import com.onkarnene.android.medialoader.data.MemoryCache
import dagger.Module
import dagger.Provides

@Module
internal class CacheModule {
	
	@Provides
	fun provideMemoryCache(): MemoryCache = MemoryCache.getInstance()
	
	@Provides
	fun provideDiskCache(): DiskCache = DiskCache.getInstance()
}