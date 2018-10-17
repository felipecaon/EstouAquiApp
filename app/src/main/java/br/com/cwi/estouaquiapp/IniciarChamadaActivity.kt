package br.com.cwi.estouaquiapp

import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.net.wifi.p2p.WifiP2pManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_iniciar_chamada.*

class IniciarChamadaActivity : AppCompatActivity() {

    var isWifiP2pEnabled = false

    private val intentFilter = IntentFilter()
    private lateinit var mChannel: WifiP2pManager.Channel
    private lateinit var mManager: WifiP2pManager
    private lateinit var receiver: WiFiDirectBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_chamada)

        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

        mManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        mChannel = mManager.initialize(this, mainLooper, null)

        discoverButton.setOnClickListener {
            mManager.discoverPeers(mChannel, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    discoverButton.setBackgroundResource(R.drawable.button_enviado)
                    Toast.makeText(this@IniciarChamadaActivity, "Presença sendo enviada...", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(reasonCode: Int) {
                    Toast.makeText(this@IniciarChamadaActivity, "Algo deu errado", Toast.LENGTH_LONG).show()
                }
            })
        }

    }

    public override fun onResume() {
        super.onResume()
        receiver = WiFiDirectBroadcastReceiver(mManager, mChannel, this)
        registerReceiver(receiver, intentFilter)
    }

    public override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

}
