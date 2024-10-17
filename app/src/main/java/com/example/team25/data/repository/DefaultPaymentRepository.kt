package com.example.team25.data.repository


import com.example.team25.data.network.services.PaymentService
import javax.inject.Inject

class DefaultPaymentRepository @Inject constructor(
    private val paymentService: PaymentService
){

}
