package com.nmd.model;

public class PaymentDetail {
    private String txnId;
    private Float chargedAmount;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public Float getChargedAmount() {
        return chargedAmount;
    }

    public void setChargedAmount(Float chargedAmount) {
        this.chargedAmount = chargedAmount;
    }
}
