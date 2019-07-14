/*
 * Created by Onkar Nene on 13/7/19 11:53 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.data

import okhttp3.MediaType

internal interface Cache {
	
	fun get(key: String): Pair<ByteArray, MediaType>?
	
	fun put(
			key: String,
			value: Pair<ByteArray, MediaType>
	)
	
	fun clearCache()
}