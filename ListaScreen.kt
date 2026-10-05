package com.example.practica06

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListaScreen() {
    val context = LocalContext.current

    // Estado para la lista dinámica de contactos
    var listaContactos by remember {
        mutableStateOf(
            listOf(
                Contacto(1, "Ana López", "555-1234"),
                Contacto(2, "Carlos Ruiz", "555-5678"),
                Contacto(3, "María García", "555-9012")
            )
        )
    }

    // Estados para las cajas de texto
    var nombreInput by remember { mutableStateOf("") }
    var telefonoInput by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF121212) 
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Práctica 06: Lista de Contactos",
                fontSize = 22.sp,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Nombre
            OutlinedTextField(
                value = nombreInput,
                onValueChange = { nombreInput = it },
                label = { Text("Nombre Completo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFD0BCFF),
                    unfocusedLabelColor = Color(0xFFCCCCCC),
                    focusedBorderColor = Color(0xFFD0BCFF),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFFD0BCFF)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo: Teléfono
            OutlinedTextField(
                value = telefonoInput,
                onValueChange = { telefonoInput = it },
                label = { Text("Teléfono") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFD0BCFF),
                    unfocusedLabelColor = Color(0xFFCCCCCC),
                    focusedBorderColor = Color(0xFFD0BCFF),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFFD0BCFF)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botón Agregar
            Button(
                onClick = {
                    if (nombreInput.isNotBlank() && telefonoInput.isNotBlank()) {
                        val nuevoContacto = Contacto(
                            id = (listaContactos.maxOfOrNull { it.id } ?: 0) + 1,
                            nombre = nombreInput,
                            telefono = telefonoInput
                        )
                        listaContactos = listaContactos + nuevoContacto
                        nombreInput = ""
                        telefonoInput = ""
                    } else {
                        Toast.makeText(context, "Por favor llena ambos campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6750A4)
                )
            ) {
                Text(text = "Agregar Contacto", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = Color.DarkGray)

            Spacer(modifier = Modifier.height(16.dp))

            // Lista Dinámica 
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = listaContactos,
                    key = { contacto -> contacto.id }
                ) { contacto ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF212121)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = contacto.nombre,
                                    fontSize = 18.sp,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White
                                )
                                Text(
                                    text = contacto.telefono,
                                    fontSize = 14.sp,
                                    color = Color(0xFFD0BCFF)
                                )
                            }

                            // Botón Borrar
                            IconButton(
                                onClick = {
                                    listaContactos = listaContactos.filter { it.id != contacto.id }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar contacto",
                                    tint = Color(0xFFFFB4AB) 
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
