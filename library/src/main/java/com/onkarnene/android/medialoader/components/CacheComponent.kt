/*
 * Created by Onkar Nene on 14/7/19 12:55 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.components

import com.onkarnene.android.medialoader.data.DiskCache
import com.onkarnene.android.medialoader.data.MemoryCache
import com.onkarnene.android.medialoader.modules.CacheModule
import dagger.Component

/**
 * Dagger component responsible for integration with CacheModule and provide required objects.
 */
@Component(modules = [CacheModule::class])
internal interface CacheComponent {
	
	/**
	 * Provides MemoryCache instance.
	 */
	fun getMemoryCache(): MemoryCache
	
	/**
	 * Provides DiskCache instance.
	 */
	fun getDiskCache(): DiskCache
}