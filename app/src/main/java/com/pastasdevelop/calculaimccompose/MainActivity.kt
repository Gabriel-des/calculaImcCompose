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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pastasdevelop.calculaimccompose.ui.theme.CalculaImcComposeTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pastasdevelop.calculaimccompose.entity.ImcRecord
import com.pastasdevelop.calculaimccompose.model.ImcViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculaImcComposeTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home")
                {
                    composable("home") {
                        CalculaIMCScreen(
                            onNavigateToDeveloper = {
                                navController.navigate("developer")
                            }
                        )
                    }
                    composable("developer") {
                        DeveloperScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun CalculaIMCScreen(
    modifier: Modifier = Modifier,
    viewModel: ImcViewModel = viewModel(),
    onNavigateToDeveloper: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Column (
        modifier = modifier.padding(top = 32.dp)
    ) {
        OutlinedTextField(
            value = viewModel.peso,
            onValueChange = { viewModel.onPesoChange(it) },
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
            value = viewModel.altura,
            onValueChange = { viewModel.onAlturaChange(it) },
            label = {
                Text("Altura em m")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        if (viewModel.resultado != "0.00") {
            panelResult(viewModel.resultado)
        }

        PanelButton(
            { viewModel.calcularImc() },
            {
                viewModel.limparTela()
                focusRequester.requestFocus()
            }
        )

        Button(
            onClick = onNavigateToDeveloper,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Sobre o desenvolvedor")
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Histórico de cálculos:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        PanelHistorico(viewModel.historico)
    }
}

@Composable
fun PanelHistorico(historico: List<ImcRecord>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(historico) { registro ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Peso: ${registro.peso} kg",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Peso: ${registro.altura} m",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Text(
                        text = "IMC: ${registro.imc}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
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

@Composable
fun DeveloperScreen() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Desenvolvido por:")
        Text(
            text = "Pastas",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}