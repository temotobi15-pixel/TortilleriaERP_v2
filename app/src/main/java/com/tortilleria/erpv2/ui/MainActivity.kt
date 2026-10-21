package com.tortilleria.erpv2.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tortilleria.erpv2.data.AppDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TortilleriaApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TortilleriaApp() {
    var pantalla by remember { mutableStateOf("menu") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Tortilleria ERP v2", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (pantalla) {
                "menu" -> MenuPrincipal(onNavigate = { pantalla = it })
                "ventas" -> PantallaSimple("Ventas / Pedidos", onBack = { pantalla = "menu" })
                "produccion" -> PantallaSimple("Producción Diaria", onBack = { pantalla = "menu" })
                "inventario" -> PantallaSimple("Inventario Maíz/Harina", onBack = { pantalla = "menu" })
                "clientes" -> PantallaSimple("Clientes y Cuentas", onBack = { pantalla = "menu" })
                "reportes" -> PantallaSimple("Reportes y Corte", onBack = { pantalla = "menu" })
            }
        }
    }
}

@Composable
fun MenuPrincipal(onNavigate: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Base Congelada OK - Selecciona módulo:", fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp)) }
        item { MenuCard("🛒 Ventas y Pedidos", "Registrar pedidos, tortilla, masa") { onNavigate("ventas") } }
        item { MenuCard("🌽 Producción", "Kilos producidos por día, merma") { onNavigate("produccion") } }
        item { MenuCard("📦 Inventario", "Maíz, harina, gas, papel") { onNavigate("inventario") } }
        item { MenuCard("👥 Clientes", "Tiendas, rutas, adeudos") { onNavigate("clientes") } }
        item { MenuCard("📊 Reportes", "Corte de caja, utilidad") { onNavigate("reportes") } }
    }
}

@Composable
fun MenuCard(titulo: String, subtitulo: String, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(titulo, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(subtitulo, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun PantallaSimple(titulo: String, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack) { Text("← Volver") }
        Spacer(modifier = Modifier.height(20.dp))
        Text(titulo, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Aquí va la lógica de $titulo. Base de datos lista.")
    }
}
