/*
 * Created by Onkar Nene on 14/7/19 5:50 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.components

import com.onkarnene.android.medialoader.data.DiskCache
import com.onkarnene.android.medialoader.data.MemoryCache
import com.onkarnene.android.medialoader.modules.NetworkModule
import com.onkarnene.android.medialoader.networks.Downloader
import com.onkarnene.android.medialoader.networks.HttpOperationWrapper
import com.onkarnene.android.medialoader.repositories.MediaLoaderRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component responsible for integration with NetworkModule and CacheComponent.
 */
@Singleton
@Component(modules = [NetworkModule::class], dependencies = [CacheComponent::class])
internal interface MediaLoaderComponent {
	
	/**
	 * Provides MemoryCache instance.
	 */
	fun getMemoryCache(): MemoryCache
	
	/**
	 * Provides DiskCache instance.
	 */
	fun getDiskCache(): DiskCache
	
	/**
	 * Provides Downloader instance.
	 */
	fun getDownloader(): Downloader
	
	/**
	 * Provides HttpOperationWrapper instance.
	 */
	fun getHttpOperationWrapper(): HttpOperationWrapper
	
	/**
	 * Provides MediaLoaderRepository instance.
	 */
	fun injectMediaLoaderRepository(mediaLoaderRepository: MediaLoaderRepository)
}