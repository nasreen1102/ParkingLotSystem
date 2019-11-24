package com.nmd.model;

import com.nmd.enums.PaymentCardType;

public class CardPayment extends PaymentDetail{
    private Long cardNumber;
    private Integer expiryMM;
    private Integer expiryYY;
    private PaymentCardType cardType;

    public CardPayment(Long cardNumber, Integer expiryMM, Integer expiryYY, PaymentCardType cardType) {
        this.cardNumber = cardNumber;
        this.expiryMM = expiryMM;
        this.expiryYY = expiryYY;
        this.cardType = cardType;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getExpiryMM() {
        return expiryMM;
    }

    public void setExpiryMM(Integer expiryMM) {
        this.expiryMM = expiryMM;
    }

    public Integer getExpiryYY() {
        return expiryYY;
    }

    public void setExpiryYY(Integer expiryYY) {
        this.expiryYY = expiryYY;
    }

    public PaymentCardType getCardType() {
        return cardType;
    }

    public void setCardType(PaymentCardType cardType) {
        this.cardType = cardType;
    }
}
