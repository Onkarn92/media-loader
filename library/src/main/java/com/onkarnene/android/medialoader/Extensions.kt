/*
 * Created by Onkar Nene on 11/7/19 11:59 AM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader

import android.content.Context
import android.net.ConnectivityManager

/**
 * Check for network connection availability.
 *
 * @return true if device is currently connected to the internet (WiFi or Mobile Data), otherwise false.
 */
fun Context.isNetworkAvailable(): Boolean {
	val networkInfo = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.activeNetworkInfo
	return networkInfo != null && networkInfo.isConnected
}