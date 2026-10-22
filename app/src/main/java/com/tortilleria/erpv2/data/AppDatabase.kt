package com.tortilleria.erpv2.data

import android.content.Context
import androidx.room.*

@Database(
    entities = [
        Ticket::class,
        TicketDetalle::class,
        Empleado::class,
        RegistroBola::class
    ], 
    version = 6, // Subimos de 5 a 6 por las 2 tablas nuevas
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
    abstract fun empleadoDao(): EmpleadoDao
    abstract fun registroBolaDao(): RegistroBolaDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext, 
                    AppDatabase::class.java, 
                    "tortilleria_v2.db"
                )
                .fallbackToDestructiveMigration() // Borra y recrea si cambia version, evita crash en tu tablet de cobro
                .build().also { INSTANCE = it }
            }
        }
    }
}