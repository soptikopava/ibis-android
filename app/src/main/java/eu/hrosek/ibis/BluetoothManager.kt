package eu.hrosek.ibis

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.OutputStream
import java.util.UUID


class MyBluetoothManager(private val deviceAddress: String) {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    fun connect() {
        val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)

        // UUID pro SPP (Serial Port Profile)
        val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

        bluetoothSocket = device?.createRfcommSocketToServiceRecord(uuid)
        bluetoothSocket?.connect()

        outputStream = bluetoothSocket?.outputStream
    }
    fun isConnected(): Boolean {
        return bluetoothSocket?.isConnected == true
    }

    fun vypocitejPayload(): String {
        // Váš kód pro výpočet payload
        return "Výsledek výpočtu payloadu"
    }
    fun odeslatNaDisplej(payload: String) {
        try {
            outputStream?.write(payload.toByteArray())
            Log.d("BluetoothManager", "Payload úspěšně odeslán: $payload")
        } catch (e: Exception) {
            Log.e("BluetoothManager", "Chyba při odesílání payloadu: ${e.message}")
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