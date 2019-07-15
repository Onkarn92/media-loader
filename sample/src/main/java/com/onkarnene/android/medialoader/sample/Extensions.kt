/*
 * Created by Onkar Nene on 15/7/19 6:21 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample

import android.app.Activity
import android.content.Intent

fun Activity.startAndFinish(intent: Intent) {
	this.startActivity(intent)
	this.finish()
}