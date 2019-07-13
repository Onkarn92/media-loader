/*
 * Created by Onkar Nene on 13/7/19 11:56 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType

internal object MemoryCache : Cache {
	
	/**
	 * Item will expire after 1 minute by default; If not used.
	 */
	const val DEFAULT_TIMEOUT: Long = 60000
	const val DEFAULT_CAPACITY: Int = 100
	
	private val map by lazy {LinkedHashMap<String, Triple<Long, ByteArray, MediaType>>()}
	
	private val lock = Any()
	private var maxCapacity: Int = DEFAULT_CAPACITY
	private var timeout: Long = DEFAULT_TIMEOUT
	
	fun getInstance(): MemoryCache = this
	
	fun setCapacity(capacity: Int) {
		maxCapacity = capacity
	}
	
	fun setTimeout(millis: Long) {
		timeout = millis
	}
	
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
}