package eu.hrosek.ibis

import android.util.Log
import kotlin.experimental.xor

fun calculateXORChecksum(data: String): Byte {
    var checksum: Byte = 0x00
    for (i in data.indices step 2) {
        val bytePair = data.substring(i, i + 2)
        val byteValue = bytePair.toInt(16).toByte()
        checksum = checksum xor byteValue
        Log.d("Calculate", byteValue.toString())
    }
    checksum = checksum xor 0x7F // Přidání hodnoty 7F k vypočtenému XOR kontrolnímu součtu
    checksum = checksum xor 0x0D // Přidání hodnoty 0D (CR) jako návratového vozíku
    Log.d("CalculateSum", checksum.toString())
    return checksum
}

fun textToHex(text: String): String {
    val byteArray = text.toByteArray()
    // Převod původního textu do hexadecimální podoby
    val hexStringBuilder = StringBuilder()
    for (byte in byteArray) {
        val hexValue = String.format("%02X", byte.toInt() and 0xFF)
        hexStringBuilder.append(hexValue)
    }
    // Přidání XOR kontrolního součtu
    //val byteArray1 = hexStringToByteArray(hexStringBuilder.toString())
    //val byteArray2 = byteArray1.toString()
    val checksum = calculateXORChecksum(hexStringBuilder.toString())
    val checksumHex = String.format("%02X", checksum)
    hexStringBuilder.append("0D") // Přidání hodnoty 0D (CR)
    hexStringBuilder.append(checksumHex)
    return hexStringBuilder.toString().trim().toUpperCase()
}
