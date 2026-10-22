package com.tortilleria.erpv2.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    fun cargar(){ scope.launch{ lista = db.empleadoDao().obtenerTodos() } }
    LaunchedEffect(Unit){ cargar() }
    Scaffold(topBar = { TopAppBar(title = { Text("Usuarios - Alta", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = onBack){ Icon(Icons.Default.ArrowBack, null)} }) }) { pad ->
        LazyColumn(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(Color(0xFFF8F9FF))) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Nuevo usuario", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Telefono") }, modifier = Modifier.fillMaxWidth())
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("TORTILLERA","MOSTRADOR","REPARTIDOR","ADMIN").forEach { r -> FilterChip(selected = rol==r, onClick = { rol = r }, label = { Text(r.take(4), fontSize = 11.sp) }) }
                        }
                        if (rol == "TORTILLERA") {
                            OutlinedTextField(value = pagoPorBola, onValueChange = { pagoPorBola = it }, label = { Text("Pago por bola") }, modifier = Modifier.fillMaxWidth())
                        }
                        Button(onClick = {
                            if(nombre.isBlank()) return@Button
                            scope.launch {
                                val nuevo = Empleado(id = UUID.randomUUID().toString(), nombre = nombre.trim(), telefono = telefono.trim(), rolId = rol, rolNombreSnapshot = rol, pagoPorBola = pagoPorBola.toDoubleOrNull() ?: 8.0, qrToken = "LOGIN:${UUID.randomUUID().toString().take(8).uppercase()}")
                                db.empleadoDao().insertar(nuevo)
                                nombre=""; telefono=""; pagoPorBola="8"
                                cargar()
                            }
                        }, modifier = Modifier.fillMaxWidth()) { Text("Guardar + QR") }
                    }
                }
            }
            items(lista) { emp ->
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(12.dp)) {
                        Column(Modifier.weight(1f)) {
                            Text(emp.nombre, fontWeight = FontWeight.Bold)
                            Text("${emp.rolId} - $${emp.pagoPorBola}", fontSize = 12.sp)
                        }
                        IconButton(onClick = { scope.launch { db.empleadoDao().desactivar(emp.id); cargar() } }) { Icon(Icons.Default.Delete, null, tint = Color.Red) }
                    }
                }
            }
        }
    }
}
