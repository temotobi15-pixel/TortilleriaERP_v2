package com.tortilleria.erpv2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tortilleria.erpv2.data.AppDatabase
import com.tortilleria.erpv2.data.Empleado
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuariosScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val scope = rememberCoroutineScope()

    var lista by remember { mutableStateOf<List<Empleado>>(emptyList()) }
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("TORTILLERA") }
    var pagoPorBola by remember { mutableStateOf("8") }
    var showDialog by remember { mutableStateOf(false) }
    var empleadoQR by remember { mutableStateOf<Empleado?>(null) }

    fun cargar() { scope.launch { lista = db.empleadoDao().obtenerTodos() } }
    LaunchedEffect(Unit) { cargar() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Usuarios • Alta", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack){ Icon(Icons.Default.ArrowBack, null)} }
            )
        }
    ) { pad ->
        LazyColumn(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(Color(0xFFF8F9FF))) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Nuevo usuario", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth(), leadingIcon = { Icon(Icons.Default.Person, null)})
                        OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth(), leadingIcon = { Icon(Icons.Default.Phone, null)})
                        
                        // Selector Rol
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("TORTILLERA","MOSTRADOR","REPARTIDOR","ADMIN").forEach { r ->
                                FilterChip(selected = rol==r, onClick = { rol = r }, label = { Text(r.take(4), fontSize = 11.sp) })
                            }
                        }

                        // Pago variable solo para tortilleras
                        if (rol == "TORTILLERA") {
                            OutlinedTextField(
                                value = pagoPorBola, onValueChange = { pagoPorBola = it },
                                label = { Text("Pago por bola ($)") },
                                prefix = { Text("$") },
                                suffix = { Text("x 5kg") },
                                leadingIcon = { Icon(Icons.Default.Payments, null)},
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text("Ej: 8kg = 1.6 bolas = $${(pagoPorBola.toDoubleOrNull()?:8.0)*1.6}", fontSize = 12.sp, color = Color.Gray)
                        }

                        Button(
                            onClick = {
                                if(nombre.isBlank()) return@Button
                                scope.launch {
                                    val nuevo = Empleado(
                                        id = UUID.randomUUID().toString(),
                                        nombre = nombre.trim(),
                                        telefono = telefono.trim(),
                                        rolId = rol,
                                        rolNombreSnapshot = rol,
                                        pagoPorBola = pagoPorBola.toDoubleOrNull() ?: 8.0,
                                        qrToken = "LOGIN:${UUID.randomUUID().toString().take(8).uppercase()}"
                                    )
                                    db.empleadoDao().insertar(nuevo)
                                    nombre=""; telefono=""; pagoPorBola="8"
                                    cargar()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
                        ) { Icon(Icons.Default.Save, null); Spacer(Modifier.width(8.dp)); Text("Guardar usuario + Generar QR") }
                    }
                }
            }
            items(lista) { emp ->
                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(42.dp).background(if(emp.rolId=="TORTILLERA") Color(0xFFFEF3C7) else Color(0xFFDBEAFE), RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center){
                            Text(emp.nombre.take(1).uppercase(), fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(emp.nombre, fontWeight = FontWeight.Bold)
                            Text("${emp.rolId} • $${emp.pagoPorBola}/bola • ${emp.telefono}", fontSize = 12.sp, color = Color.Gray)
                        }
                        IconButton(onClick = { empleadoQR = emp; showDialog = true }) { Icon(Icons.Default.QrCode, null) }
                        IconButton(onClick = { scope.launch { db.empleadoDao().desactivar(emp.id); cargar() } }) { Icon(Icons.Default.Delete, null, tint = Color.Red) }
                    }
                }
            }
        }
    }

    // Dialog QR
    if(showDialog && empleadoQR!=null){
        AlertDialog(
            onDismissRequest = { showDialog=false },
            title = { Text("QR Login - ${empleadoQR!!.nombre}") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(Modifier.size(180.dp).background(Color.Black, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center){
                        // Aqui va tu libreria QR, por ahora mostramos token
                        Text(empleadoQR!!.qrToken, color = Color.White, fontSize = 10.sp)
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(empleadoQR!!.qrToken, fontWeight = FontWeight.Bold)
                    Text("Pago: $${empleadoQR!!.pagoPorBola} por bola (5kg)", fontSize = 12.sp)
                }
            },
            confirmButton = { TextButton(onClick = { showDialog=false }){ Text("Cerrar") } }
        )
    }
}