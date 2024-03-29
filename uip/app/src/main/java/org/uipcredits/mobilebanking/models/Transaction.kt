package org.uipcredits.mobilebanking.models

import android.os.Parcelable

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

import org.uipcredits.mobilebanking.models.client.Currency
import  org.uipcredits.mobilebanking.models.client.Type

import java.util.ArrayList

/**
 * @author Vishwajeet
 * @since 10/8/16.
 */

@Parcelize
data class Transaction(
        @SerializedName("id")
        var id: Long? = null,

        @SerializedName("officeId")
        var officeId: Long? = null,

        @SerializedName("officeName")
        var officeName: String,

        @SerializedName("type")
        var type: Type,

        @SerializedName("date")
        var date: List<Int> = ArrayList(),

        @SerializedName("currency")
        var currency: Currency,

        @SerializedName("amount")
        var amount: Double? = null,

        @SerializedName("submittedOnDate")
        var submittedOnDate: List<Int> = ArrayList(),

        @SerializedName("reversed")
        var reversed: Boolean? = null

) : Parcelable