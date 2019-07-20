package org.uipcredits.mobilebanking.models

import org.uipcredits.mobilebanking.models.beneficiary.Beneficiary
import org.uipcredits.mobilebanking.models.templates.account.AccountOptionsTemplate

/**
 * Created by dilpreet on 23/6/17.
 */

data class AccountOptionAndBeneficiary(
        val accountOptionsTemplate: AccountOptionsTemplate,
        val beneficiaryList: List<Beneficiary>)
