package org.uip.mobilebanking.models.accounts.loan;

import java.io.Serializable;

public class TitleDeed implements Serializable {

    public String titleNumber;
    public String productName;
    public String unitPrice;
    public double loanAmount;
    public TitleDeed(String title_number, String productname, String unit_Price, double loanAmt) {
        this.titleNumber = title_number;
        this.productName = productname;
        this.unitPrice = unit_Price;
        this.loanAmount = loanAmt;
    }
}
