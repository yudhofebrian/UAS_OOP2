package com.example.crudkaryawan.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "karyawan")
data class karyawan(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "menu") val menu: String,
    @ColumnInfo(name = "nama") val nama: String,
    @ColumnInfo(name = "nik") val nik: Int
)