/*
 * Created by Onkar Nene on 15/7/19 7:33 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.views.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.ceil

/**
 * Maintains Aspect ratio of the image with respect to the width.
 * Height may be varied as per screen resolution.
 * Width is fixed i.e. Screen's width.
 */
class ResizableImageView(
		context: Context,
		attributeSet: AttributeSet
) : AppCompatImageView(context, attributeSet) {
	
	override fun onMeasure(
			widthMeasureSpec: Int,
			heightMeasureSpec: Int
	) {
		val drawable = drawable
		if (drawable == null) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec)
		} else {
			val width = MeasureSpec.getSize(widthMeasureSpec)
			val height = ceil((width.toFloat() * drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth.toFloat()).toDouble()).toInt()
			setMeasuredDimension(width, height)
		}
	}
}