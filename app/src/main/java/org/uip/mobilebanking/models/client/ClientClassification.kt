package org.uip.mobilebanking.models.client

import com.google.gson.annotations.SerializedName

/**
 * Created by dilpreet on 10/7/17.
 */

data class ClientClassification (

    @SerializedName("id") var id: Int,

    @SerializedName("name") var name: String,

    @SerializedName("active") var active: Boolean,

    @SerializedName("mandatory") var mandatory: Boolean
)
