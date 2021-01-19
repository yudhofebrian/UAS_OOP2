package com.example.crudkaryawan.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.crudkaryawan.karyawanActivity

@Database(entities = arrayOf(karyawan::class, User::class), version = 1)

abstract class AppRoomDB : RoomDatabase() {

    abstract fun karyawanDao(): karyawanDao
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppRoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppRoomDB::class.java,
            "APPDB"
        ).build()

    }
}