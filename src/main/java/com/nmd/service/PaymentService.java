package com.nmd.service;

import com.nmd.enums.PaymentCardType;
import com.nmd.model.Booking;
import com.nmd.model.CardPayment;
import com.nmd.model.PaymentDetail;
import org.springframework.stereotype.Service;

@Service
class PaymentService {


    public PaymentDetail pay(float fare, Booking booking) {
        //todo: Make a thirdParty Payment call. based on the response, return payment or null
        PaymentDetail payment = new CardPayment(1234543l, 12, 22, PaymentCardType.DEBIT);
        payment.setTxnId("txnId1");
        payment.setChargedAmount(fare);
        return payment;
    }
}
