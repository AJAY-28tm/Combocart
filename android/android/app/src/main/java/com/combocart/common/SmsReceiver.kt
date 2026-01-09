package com.combocart.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage

class SmsReceiver : BroadcastReceiver() {
    private var listener: ((String) -> Unit)? = null

    fun setOnSmsReceivedListener(listener: (String) -> Unit) {
        this.listener = listener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent?.action) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<*>
                val messages = arrayOfNulls<SmsMessage>(pdus.size)
                for (i in pdus.indices) {
                    messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                }
                for (message in messages) {
                    val body = message?.messageBody ?: ""
                    // Simple OTP extraction (4 digits)
                    val otp = extractOtp(body)
                    if (otp != null) {
                        listener?.invoke(otp)
                    }
                }
            }
        }
    }

    private fun extractOtp(message: String): String? {
        val pattern = "\\d{4}".toRegex()
        return pattern.find(message)?.value
    }
}
