/*
 * Created by Onkar Nene on 13/7/19 11:53 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.data

import okhttp3.MediaType

/**
 * Base implementation for MemoryCache and DiskCache module.
 */
internal interface Cache {
	
	/**
	 * Returns Pair<ByteArray, MediaType> if available in map, otherwise null.
	 */
	fun get(key: String): Pair<ByteArray, MediaType>?
	
	/**
	 * Add inputted key-value into map. If already available and expired then remove it from map.
	 */
	fun put(
			key: String,
			value: Pair<ByteArray, MediaType>
	)
	
	/**
	 * Provides interface to clear current cache.
	 */
	fun clearCache()
}