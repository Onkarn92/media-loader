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

@Component(modules = [CacheModule::class])
internal interface CacheComponent {
	
	fun getMemoryCache(): MemoryCache
	
	fun getDiskCache(): DiskCache
}