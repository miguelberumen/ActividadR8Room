package com.cuvalles.actividadr8room

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaNotas() {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val notaDao = remember { database.notaDao() }
    val scope = rememberCoroutineScope()

    val listaNotas by notaDao.obtenerTodas().collectAsState(initial = emptyList())
    var textoNota by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CUValles - Room (Miguel Berumen)") }
            )
        }
    ) { paddingValores ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValores)
                .padding(16.dp)
        ) {
            Text(
                text = "Licenciatura en Tecnologías de la Información",
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = textoNota,
                onValueChange = { textoNota = it },
                label = { Text("Escribe un registro para guardar") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (textoNota.isNotBlank()) {
                        val nuevaNota = NotaEntity(contenido = textoNota)
                        scope.launch {
                            notaDao.insertar(nuevaNota)
                        }
                        textoNota = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar en Base de Datos")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Registros guardados localmente (${listaNotas.size}):",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listaNotas) { nota ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "ID #${nota.id}",
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    text = nota.contenido,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            TextButton(
                                onClick = {
                                    scope.launch {
                                        notaDao.eliminar(nota)
                                    }
                                }
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}