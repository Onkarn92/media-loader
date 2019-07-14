/*
 * Created by Onkar Nene on 14/7/19 12:17 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.data

import okhttp3.MediaType

internal object DiskCache : Cache {
	
	private val lock = Any()
	
	fun getInstance(): DiskCache = this
	
	override fun get(key: String): Pair<ByteArray, MediaType>? {
		TODO("Not supported in release v1.0.0")
	}
	
	override fun put(
			key: String,
			value: Pair<ByteArray, MediaType>
	) {
		TODO("Not supported in release v1.0.0")
	}
	
	override fun clearCache() {
		TODO("Not supported in release v1.0.0")
	}
}