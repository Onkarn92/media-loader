/*
 * Created by Onkar Nene on 14/7/19 10:30 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.data

import org.junit.*

class MemoryCacheTest {
	
	@Throws(IllegalArgumentException::class)
	@Test(expected = IllegalArgumentException::class)
	fun memoryCache_ValidMaxCapacity_ThrowsException() {
		MemoryCache.setCapacity(0)
	}
	
	@Throws(IllegalArgumentException::class)
	@Test(expected = IllegalArgumentException::class)
	fun memoryCache_ValidTimeout_ThrowsException() {
		MemoryCache.setTimeout(1234)
	}
	
	@Test
	fun memoryCache_EmptyKey_ReturnsNull() {
		Assert.assertNull("Object should be null.", MemoryCache.getInstance().get(""))
	}
	
	@Test
	fun memoryCache_TestKey_ReturnsNull() {
		Assert.assertNull("Object should be null.", MemoryCache.getInstance().get("Test"))
	}
}