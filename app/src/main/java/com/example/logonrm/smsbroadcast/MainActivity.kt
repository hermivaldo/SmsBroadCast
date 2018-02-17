package com.example.logonrm.smsbroadcast

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent



class MainActivity : AppCompatActivity() {

    lateinit var mReceiver : BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent != null){
            val i = intent
            var remetente = i.getStringExtra("remetente")
            var mensagem = i.getStringExtra("mensagem")
            tvSMSReceived.text = remetente + " : " + mensagem
        }

        requestPermission()
    }

    fun requestPermission() {

        val permission = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)

        if (grant != PackageManager.PERMISSION_GRANTED){
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = permission
            ActivityCompat.requestPermissions(this, permission_list, 1)
        }
    }

    override fun onResume() {
        super.onResume()

        val intentFilter = IntentFilter(
                "android.intent.action.SMSRECEBIDO")

        mReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val remetente = intent.getStringExtra("remetente")
                val mensagem = intent.getStringExtra("mensagem")
                tvSMSReceived.setText(remetente + " : " + mensagem)

            }
        }
        this.registerReceiver(mReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        this.unregisterReceiver(this.mReceiver)
    }
}
