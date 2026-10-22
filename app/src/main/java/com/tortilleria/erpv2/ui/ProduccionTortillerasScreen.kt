package com.tortilleria.erpv2.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
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
import com.tortilleria.erpv2.data.RegistroBola
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProduccionTortillerasScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val scope = rememberCoroutineScope()
    val fechaHoy = remember { SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()) }
    var tortilleras by remember { mutableStateOf<List<Empleado>>(emptyList()) }
    var registros by remember { mutableStateOf<List<RegistroBola>>(emptyList()) }
    var kgInputs by remember { mutableStateOf<Map<String,String>>(emptyMap()) }
    fun cargar() { scope.launch { tortilleras = db.empleadoDao().obtenerTortilleras(); registros = db.registroBolaDao().obtenerPorDia(fechaHoy) } }
    LaunchedEffect(Unit) { cargar() }
    val totalBolas = registros.sumOf { it.bolas }
    val totalKg = registros.sumOf { it.kg }
    val totalGanado = registros.sumOf { it.ganado }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Produccion Tortilleras", fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = onBack){ Icon(Icons.Default.ArrowBack, null)} }, actions = { IconButton(onClick = { cargar() }){ Icon(Icons.Default.Refresh, null)} }) },
        bottomBar = {
            Card(Modifier.fillMaxWidth().padding(12.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(Color(0xFF16A34A))) {
                Row(Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${String.format("%.1f", totalBolas)} bolas - ${String.format("%.1f", totalKg)}kg", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("$${String.format("%.2f", totalGanado)}", color = Color.White, fontWeight = FontWeight.ExtraBold)
                }
            }
        }
    ) { pad ->
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(pad).padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(tortilleras) { tortillera ->
                val regs = registros.filter { it.empleadoId == tortillera.id }
                val inputKg = kgInputs[tortillera.id] ?: ""
                Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(Color.White)) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(tortillera.nombre, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("$${tortillera.pagoPorBola}/bola", fontSize = 10.sp, color = Color.Gray)
                        OutlinedTextField(value = inputKg, onValueChange = { kgInputs = kgInputs + (tortillera.id to it) }, label = { Text("Kg") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                        val kgVal = inputKg.toDoubleOrNull() ?: 0.0
                        if(kgVal>0){ Text("= ${RegistroBola.kgToBolas(kgVal)} bolas", fontSize = 11.sp, color = Color(0xFF2563EB), fontWeight = FontWeight.Bold) }
                        Button(onClick = {
                            val kg = inputKg.toDoubleOrNull() ?: return@Button
                            scope.launch {
                                val bolas = RegistroBola.kgToBolas(kg)
                                val reg = RegistroBola(empleadoId = tortillera.id, empleadoNombreSnapshot = tortillera.nombre, kg = kg, bolas = bolas, pagoPorBolaSnapshot = tortillera.pagoPorBola, ganado = bolas * tortillera.pagoPorBola)
                                db.registroBolaDao().insertar(reg)
                                kgInputs = kgInputs + (tortillera.id to "")
                                cargar()
                            }
                        }, modifier = Modifier.fillMaxWidth()) { Text("Entregar", fontSize = 12.sp) }
                    }
                }
            }
        }
    }
}
