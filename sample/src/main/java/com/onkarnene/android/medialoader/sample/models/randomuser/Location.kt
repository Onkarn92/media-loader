/*
 * Created by Onkar Nene on 15/7/19 9:13 PM
 *
 * Copyright (c) 2019 Onkar Nene. All rights reserved.
 */

package com.onkarnene.android.medialoader.sample.models.randomuser

import com.google.gson.annotations.SerializedName

data class Location(
		@SerializedName("street") var street: String?,
		
		@SerializedName("city") var city: String?,
		
		@SerializedName("state") var state: String?,
		
		@SerializedName("postcode") var postcode: String?,
		
		@SerializedName("coordinates") var coordinates: Coordinates?,
		
		@SerializedName("timezone") var timezone: Timezone?
)