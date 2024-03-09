package eu.hrosek.ibis

import android.util.Log
import kotlin.experimental.xor

fun calculateXORChecksum(data: ByteArray): Byte {
    Log.d("calculateXORChecksum", "--------")
    var checksum: Byte = 0
    for (byte in data) {
        checksum = checksum xor byte
        Log.d("calculateXORChecksum", "vypocet checksum: $checksum")
    }
    checksum = checksum xor 0x0D // Přidání hodnoty 0D (CR) jako návratového vozíku
    checksum = checksum xor 0x7F // Přidání hodnoty 7F k vypočtenému XOR kontrolnímu součtu

    Log.d("calculateXORChecksum", "checksum: $checksum")
    return checksum
}

fun textToHex(text: String): String {
    var byteArray = text.toByteArray()
    // Převod původního textu do hexadecimální podoby
    val hexStringBuilder = StringBuilder()
    for (byte in byteArray) {
        val hexValue = String.format("%02X", byte.toInt() and 0xFF)
        hexStringBuilder.append(hexValue)
    }
    // Přidání XOR kontrolního součtu
    var byteArray2 = hexStringBuilder.toString().toByteArray()
    val checksum = calculateXORChecksum(byteArray2)
    val checksumHex = String.format("%02X", checksum)
    hexStringBuilder.append("0D") // Přidání hodnoty 0D (CR)
    hexStringBuilder.append(checksumHex)
    return hexStringBuilder.toString().trim().toUpperCase()
}

fun hexToByteArray(hex: String): ByteArray {  //převede text hex na hodnoty hex
    val result = ByteArray(hex.length / 2)
    for (i in 0 until hex.length step 2) {
        result[i / 2] = hex.substring(i, i + 2).toInt(16).toByte()
    }
    return result
}