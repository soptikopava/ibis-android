package eu.hrosek.ibis

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val REQUEST_ENABLE_BT = 1
        private const val REQUEST_BLUETOOTH_PERMISSIONS = 2
    }
    lateinit var buttonVypocitej: Button
    lateinit var buttonOdeslat: Button
    lateinit var prikaz: EditText
    lateinit var payloadTv: TextView
    private lateinit var adresa: EditText
    private var bluetoothManager: MyBluetoothManager? = null

    private val pickBluetoothDevice = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val deviceAddress = data?.getStringExtra("device_address")
            // Zde pracujte s adresou vybraného Bluetooth zařízení
            Toast.makeText(this, "Vybráno zařízení s adresou: $deviceAddress", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonVypocitej = findViewById(R.id.buttonVypocitej)
        buttonVypocitej.setOnClickListener(this)
        buttonOdeslat= findViewById(R.id.buttonOdeslat)
        buttonOdeslat.setOnClickListener(this)
        prikaz = findViewById(R.id.editTextPrikaz)
        adresa = findViewById(R.id.editTextAdresa) // Inicializace nového pole pro adresu

        val buttonSelectDevice: Button = findViewById(R.id.buttonSelectDevice)
        buttonSelectDevice.setOnClickListener(this)

        enableEdgeToEdge()
        checkBluetoothPermissions()

        this.payloadTv = findViewById(R.id.textPyload)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets -> val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Připojení k Bluetooth zařízení
        initBluetoothManager()
        bluetoothManager?.connect()
        // Nastavení posluchačů tlačítek
        buttonVypocitej.setOnClickListener(this)
        buttonOdeslat.setOnClickListener(this)

        // Přidání posluchače změn v poli pro adresu
        adresa.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nepotřebujeme implementovat
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Nepotřebujeme implementovat
            }

            override fun afterTextChanged(s: Editable?) {
                // Zde sledujeme změny v poli a povolíme nebo zakážeme tlačítko "Odeslat na displej"
                buttonOdeslat.isEnabled = s?.isNotBlank() == true
            }
        })
    }
    private fun initBluetoothManager() {
        adresa = findViewById(R.id.editTextAdresa)

        // Zkontrolovat, zda bluetoothManager je null
        if (bluetoothManager == null) {
            val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            val bluetoothAdapter = bluetoothManager.adapter

            if (bluetoothAdapter == null) {
                // Pokud není Bluetooth adaptér dostupný na zařízení
                Toast.makeText(this, "Bluetooth adaptér není dostupný na tomto zařízení.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val deviceAddress = adresa.text.toString() // Předpokládám, že adresa je řetězec
                val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
                val bluetoothAdapter = bluetoothManager.adapter
                if (bluetoothAdapter != null) {
                    val bluetoothManager: MyBluetoothManager? = null
                    bluetoothManager?.connect()
                }
            }
        }
    }

    private fun getBluetoothAdapter(): BluetoothAdapter? {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return bluetoothManager.adapter
    }

    override fun onClick(v: View?) {

        Log.d("MainActivity", "onClick called")
        var payload = ""
        val pr = prikaz
        val hexResult = textToHex(pr.text.toString())

        when (v?.id) {
            R.id.buttonVypocitej -> {
                payload = hexResult
                payloadTv.text = payload
            }
            R.id.buttonOdeslat -> {
                val deviceAddress = adresa.text.toString()
                val payload = prikaz.text.toString()

                if (payload.isEmpty()) {
                    Toast.makeText(this, "Nelze odeslat prázdný payload.", Toast.LENGTH_SHORT).show()
                } else {
                    val bluetoothManager = MyBluetoothManager(deviceAddress)
                    bluetoothManager.connect()
                    bluetoothManager.odeslatNaDisplej(payload)
                    bluetoothManager.disconnect()
                }
            }
            R.id.buttonSelectDevice -> {
                showBluetoothDevicePicker()
            }
        }

        // Kontrola hodnoty payload před přiřazením do payloadTv
        Log.d("MainActivity", "Prikaz:    ${pr.text.toString()}")
        Log.d("MainActivity", "Payload:   $payload")
    }

    private fun showBluetoothDevicePicker() {
        val bluetoothManager: BluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth není dostupný na tomto zařízení.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    REQUEST_ENABLE_BT
                )
                return
            }
            val enableBtIntent2 = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            enableBluetoothResultLauncher.launch(enableBtIntent2)
        }

        val pairedDevices = bluetoothAdapter.bondedDevices

        if (pairedDevices.isEmpty()) {
            Toast.makeText(this, "Nemáte žádná párovaná Bluetooth zařízení.", Toast.LENGTH_SHORT).show()
            return
        }

        val pairedDeviceList = ArrayList<BluetoothDevice>(pairedDevices)
        val deviceList = ArrayList<String>()

        pairedDeviceList.forEach { device ->
            deviceList.add(device.name) // Zde přidáváme pouze název zařízení
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Vyberte Bluetooth zařízení")
        builder.setItems(deviceList.toTypedArray()) { _, which ->
            val selectedDevice = pairedDeviceList[which]
            adresa.setText(selectedDevice.address) // Nastavíme text pole editTextAdresa na adresu vybraného zařízení
        }

        val dialog = builder.create()
        dialog.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                // Uživatel zapnul Bluetooth, můžete pokračovat s výběrem zařízení
                showBluetoothDevicePicker()
            } else {
                Toast.makeText(this, "Bluetooth je potřeba zapnout pro výběr zařízení.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Odpojení od Bluetooth při zničení aktivity
        bluetoothManager?.disconnect()

    }

    private fun checkBluetoothPermissions() {
        val requiredPermissions = arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
        )

        val missingPermissions = requiredPermissions.filter { permission ->
            ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, missingPermissions.toTypedArray(), REQUEST_BLUETOOTH_PERMISSIONS)
        }
    }

    private val enableBluetoothResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Bluetooth was enabled by the user, continue with your logic here
        } else {
            // The user did not enable Bluetooth or an error occurred
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) // Přidáno volání super
        when (requestCode) {
            REQUEST_BLUETOOTH_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    // Všechna potřebná oprávnění byla udělena
                } else {
                    // Některá oprávnění nebyla udělena, zobrazte upozornění
                    Toast.makeText(this, "Aplikace potřebuje oprávnění Bluetooth pro správné fungování.", Toast.LENGTH_SHORT).show()
                }
                return
            }
            // Zpracujte další výsledky žádosti o oprávnění, pokud existují.
        }
    }
}
