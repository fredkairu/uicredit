package org.uip.mobilebanking.models.client

import android.os.Parcelable

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

import org.uip.mobilebanking.models.accounts.loan.LoanAccount
import org.uip.mobilebanking.models.accounts.savings.SavingAccount
import org.uip.mobilebanking.models.accounts.share.ShareAccount

import java.util.ArrayList

@Parcelize
data class ClientAccounts(
        var loanAccounts: List<LoanAccount> = ArrayList(),
        var savingsAccounts: List<SavingAccount>? = ArrayList(),

        @SerializedName("shareAccounts")
        var shareAccounts: List<ShareAccount> = ArrayList()

) : Parcelable {

    fun recurringSavingsAccounts(): List<SavingAccount> {
        return getSavingsAccounts(true)
    }

    fun nonRecurringSavingsAccounts(): List<SavingAccount> {
        return getSavingsAccounts(false)
    }

    private fun getSavingsAccounts(wantRecurring: Boolean): List<SavingAccount> {
        val result = ArrayList<SavingAccount>()
        if (this.savingsAccounts != null) {
            for (account in savingsAccounts!!) {
                if (account.isRecurring() == wantRecurring) {
                    result.add(account)
                }
            }
        }
        return result
    }
}
