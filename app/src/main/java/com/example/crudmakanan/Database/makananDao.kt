package com.example.crudmakanan.Database

import androidx.room.*

@Dao
interface makananDao {
    @Insert
    suspend fun addmakanan(makanan: makanan)

    @Update
    suspend fun updatemakanan(makanan: makanan)

    @Delete
    suspend fun deletemakanan(makanan: makanan)

    @Query("SELECT * FROM makanan")
    suspend fun getAllmakanan(): List<makanan>

    @Query("SELECT * FROM makanan WHERE id=:makanan_id")
    suspend fun getmakanan(makanan_id: Int) : List<makanan>

}