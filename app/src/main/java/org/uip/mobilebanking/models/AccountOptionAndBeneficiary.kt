package org.uip.mobilebanking.models

import org.uip.mobilebanking.models.beneficiary.Beneficiary
import org.uip.mobilebanking.models.templates.account.AccountOptionsTemplate

/**
 * Created by dilpreet on 23/6/17.
 */

data class AccountOptionAndBeneficiary(
        val accountOptionsTemplate: AccountOptionsTemplate,
        val beneficiaryList: List<Beneficiary>)
