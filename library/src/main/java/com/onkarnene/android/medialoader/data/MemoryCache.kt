/*
 * Created by Onkar Nene on 13/7/19 11:56 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.data

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType

/**
 * Responsible for maintaining limited memory cache.
 */
internal class MemoryCache private constructor() : Cache {
	
	companion object {
		/**
		 * Item will expire after 1 minute by default; If not used.
		 */
		private const val DEFAULT_TIMEOUT: Long = 60000
		
		/**
		 * Default capacity of map.
		 */
		private const val DEFAULT_CAPACITY: Int = 100
		
		@Volatile private var memoryCache: MemoryCache? = null
		
		private val lock = Any()
		
		/**
		 * Singleton implementation for MemoryCache instance.
		 */
		fun getInstance(): MemoryCache = memoryCache ?: synchronized(lock) {
			if (memoryCache == null) {
				memoryCache = MemoryCache()
			}
			memoryCache ?: throw NullPointerException("Object creation failed.")
		}
		
		/**
		 * Configure map capacity.
		 * @param capacity should not be less than 1.
		 */
		fun setCapacity(capacity: Int = DEFAULT_CAPACITY) {
			if (capacity < 1) {
				throw IllegalArgumentException("Capacity must be greater than 0.")
			}
			getInstance().maxCapacity = capacity
		}
		
		/**
		 * Configure cache timeout for each item.
		 * @param millis should not be less than 10000 (i.e. 10 seconds).
		 */
		fun setTimeout(millis: Long = DEFAULT_TIMEOUT) {
			if (millis < 10000) {
				throw IllegalArgumentException("Timeout must be greater than 10 seconds.")
			}
			getInstance().timeout = millis
		}
	}
	
	private val map by lazy {LinkedHashMap<String, Triple<Long, ByteArray, MediaType>>()}
	
	@VisibleForTesting private var maxCapacity: Int = DEFAULT_CAPACITY
	private var timeout: Long = DEFAULT_TIMEOUT
	
	override fun get(key: String): Pair<ByteArray, MediaType>? = synchronized(lock) {
		val value = map[key]
		if (value != null) {
			if (value.first > System.currentTimeMillis()) {
				// Item is not expired.
				return@synchronized value.second to value.third
			}
			// Item is expired and should be deleted
			map.remove(key)
		}
		null
	}
	
	override fun put(
			key: String,
			value: Pair<ByteArray, MediaType>
	) {
		GlobalScope.launch(Dispatchers.IO) {
			synchronized(lock) {
				if (map.size >= maxCapacity) {
					map.filterValues {
						it.first < System.currentTimeMillis()
					}.forEach {
						map.remove(it.key)
					}
					if (map.size < maxCapacity) {
						map[key] = Triple((System.currentTimeMillis() + timeout), value.first, value.second)
					} else {
						// Item has been discarded.
						return@launch
					}
				} else {
					map[key] = Triple((System.currentTimeMillis() + timeout), value.first, value.second)
				}
			}
		}
	}
	
	override fun clearCache() {
		synchronized(lock) {
			map.clear()
		}
	}
}