package com.tortilleria.erpv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HistorialScreen(onBack: () -> Unit){
    Column(Modifier.fillMaxSize().padding(12.dp)){
        Button(onClick=onBack){Text("← Menú")}
        Text("Historial de ventas - por implementar", modifier=Modifier.padding(top=12.dp))
    }
}
