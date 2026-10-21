package com.tortilleria.erpv2.data
import android.content.Context
import androidx.room.*
@Database(entities = [Ticket::class, TicketDetalle::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "tortilleria_v2.db").fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
