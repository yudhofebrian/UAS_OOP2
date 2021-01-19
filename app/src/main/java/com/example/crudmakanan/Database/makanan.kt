package com.example.crudmakanan.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "makanan")
data class makanan(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "menu") val menu: String,
    @ColumnInfo(name = "jumlah") val jumlah: Int,
    @ColumnInfo(name = "harga") val harga: Int
)