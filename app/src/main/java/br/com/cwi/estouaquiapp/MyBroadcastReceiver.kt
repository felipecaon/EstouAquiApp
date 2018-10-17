package br.com.cwi.estouaquiapp
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import android.widget.Toast

class WiFiDirectBroadcastReceiver(val manager: WifiP2pManager, val channel: WifiP2pManager.Channel, val activity: IniciarChamadaActivity) : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val device = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE) as? WifiP2pDevice

        when(intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                // Determine if Wifi P2P mode is enabled or not, alert
                // the Activity.
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                activity.isWifiP2pEnabled = state == WifiP2pManager.WIFI_P2P_STATE_ENABLED
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                Log.d("PEERS_CHANGED_ACTION", "${device?.deviceName} ${device?.deviceAddress}")
            }

            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                Toast.makeText(activity, "WIFI_P2P_CONNECTION_CHANGED_ACTION", Toast.LENGTH_LONG).show()
            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                //Chamado quando o próprio device muda de status na rede, nesta situação é possível buscar o nome e address do device
                Toast.makeText(activity, "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION - ${device?.deviceName} ${device?.deviceAddress}", Toast.LENGTH_LONG).show()
            }
        }
    }

}
