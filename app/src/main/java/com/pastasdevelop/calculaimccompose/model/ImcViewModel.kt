package com.pastasdevelop.calculaimccompose.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ImcViewModel: ViewModel() {
    var peso by mutableStateOf("")
        private set
    var altura by mutableStateOf("")
        private set
    var resultado by mutableStateOf("0.00")
        private set

    fun onPesoChange(novoPeso: String) {
        peso = novoPeso
    }

    fun onAlturaChange(novaAltura: String) {
        altura = novaAltura
    }

    fun calcularImc() {
        val pesoDouble = peso.toDoubleOrNull() ?: 0.0
        val alturaDouble = altura.toDoubleOrNull() ?: 0.0

        if (pesoDouble != 0.0 || alturaDouble != 0.0) {
            val imc = pesoDouble / (alturaDouble * alturaDouble)
            resultado = "%.2f".format(imc)
        }
    }

    fun limparTela() {
        peso = ""
        altura = ""
        resultado = "0.00"
    }
}