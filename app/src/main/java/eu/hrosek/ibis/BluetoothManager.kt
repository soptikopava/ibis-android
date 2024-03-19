package eu.hrosek.ibis

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

class MyBluetoothManager(private val context: Context, private val deviceAddress: String) {

    private val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    fun connect() {
        val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)
        Log.d("connect", "Device: $device")
        // UUID pro SPP (Serial Port Profile)
        val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

        try { //pokud je opravnení pro BT device
            bluetoothSocket = device?.createRfcommSocketToServiceRecord(uuid)
            bluetoothSocket?.connect()

            outputStream = bluetoothSocket?.outputStream
        } catch (e: SecurityException) {
            // Ošetření výjimky, pokud uživatel odmítne udělit oprávnění
            // ...
        } catch (e: IOException) {
        // Zde můžete vypsat chybovou zprávu nebo provést jinou akci
        Log.e("MyBluetoothManager", "Připojení selhalo: ${e.message}")
        }
    }

    fun isConnected(): Boolean {
        return bluetoothSocket?.isConnected == true
    }

    fun odeslatNaDisplej(payload: String) {
        Log.d("odeslatNaDisplej", "Payload:   $payload")
        try {
            outputStream?.write(payload.toByteArray())
            Log.d("odeslatNaDisplej", "Payload úspěšně odeslán: $payload")
        } catch (e: Exception) {
            Log.e("odeslatNaDisplej", "Chyba při odesílání payloadu: ${e.message}")
        }
    }

    fun disconnect() {
        try {
            // Uzavřít socket a výstupní proud
            outputStream?.close()
            bluetoothSocket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}