package com.ekades.fcmpushnotification.ui.fcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ekades.fcmpushnotification.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.android.synthetic.main.activity_subscribe_topic.*

class SubscribeTopicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe_topic)
        logToken()
        setupBtnClickListener()
    }

    private fun logToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("FCM_TOKEN", token)
        })
    }

    private fun getTopic() : String {
        return editText.text.toString()
    }

    private fun setupBtnClickListener() {
        btnUnsubscribe.setOnClickListener {
            doUnSubscribeTopic()
        }

        btnSubscribe.setOnClickListener {
            doSubscribeTopic()
        }
    }

    private fun doSubscribeTopic() {
        Firebase.messaging.subscribeToTopic(getTopic())
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
    }

    private fun doUnSubscribeTopic() {
        Firebase.messaging.unsubscribeFromTopic(getTopic())
            .addOnCompleteListener { task ->
                var msg = "Unsubscribed"
                if (!task.isSuccessful) {
                    msg = "Unsubscribe failed"
                }
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
    }
}