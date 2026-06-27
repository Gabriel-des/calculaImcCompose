package com.pastasdevelop.calculaimccompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pastasdevelop.calculaimccompose.ui.theme.CalculaImcComposeTheme
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculaImcComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculaIMCScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculaIMCScreen(modifier: Modifier = Modifier) {
    var peso by rememberSaveable { mutableStateOf("") }
    var altura by rememberSaveable { mutableStateOf("") }
    var resultado by rememberSaveable { mutableStateOf("0.00") }

    val focusRequester = remember { FocusRequester() }

    val calculaIMC = {
        val pesoDouble = peso.toDoubleOrNull() ?: 0.0
        val alturaDouble = altura.toDoubleOrNull() ?: 0.0

        if (pesoDouble != 0.0 || alturaDouble != 0.0) {
            val imc = pesoDouble / (alturaDouble * alturaDouble)
            resultado = "%.2f".format(imc)
        }
    }

    val limparTela = {
        peso = ""
        altura = ""
        resultado = "0.00"
        focusRequester.requestFocus()
    }

    Column (
        modifier = modifier
    ) {
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = {
                Text("Peso em kg")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = {
                Text("Altura em m")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        if (resultado != "0.00") {
            panelResult(resultado)
        }

        PanelButton(calculaIMC, limparTela)
    }
}

@Composable
fun panelResult( resultado: String ) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, end = 16.dp, start = 16.dp, bottom = 16.dp)
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Resultado:",
            modifier = Modifier.padding(8.dp)
        )

        Text (
            text = resultado,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun PanelButton (
    calculaIMC: () -> Unit,
    limparTela: () -> Unit
) {
    Row {
        Button(
            onClick = calculaIMC,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(text = "Calcular")
        }

        Button(
            onClick = limparTela,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(text = "Limpar")
        }
    }
}