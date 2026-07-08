package com.alejandrossm.actualizaciondsy.util

/**
 * Función de extensión para validar el RUT chileno (con dígito verificador)
 */
fun String.esRutValido(): Boolean {
    val rutLimpio = this.replace(".", "").replace("-", "").uppercase()
    if (rutLimpio.length < 8) return false

    val cuerpo = rutLimpio.substring(0, rutLimpio.length - 1)
    val dv = rutLimpio.substring(rutLimpio.length - 1)

    if (!cuerpo.all { it.isDigit() }) return false

    return validarDV(cuerpo) == dv
}

/**
 * Algoritmo de Módulo 11 para calcular el dígito verificador
 */
private fun validarDV(cuerpo: String): String {
    var suma = 0
    var multiplo = 2
    
    for (i in cuerpo.length - 1 downTo 0) {
        suma += multiplo * (cuerpo[i] - '0')
        if (multiplo < 7) multiplo++ else multiplo = 2
    }
    
    val dvEsperado = 11 - (suma % 11)
    return when (dvEsperado) {
        11 -> "0"
        10 -> "K"
        else -> dvEsperado.toString()
    }
}
