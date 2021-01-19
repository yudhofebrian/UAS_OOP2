package com.example.crudkaryawan.Database

import androidx.room.*

@Dao
interface karyawanDao {
    @Insert
    suspend fun addkaryawan(karyawan: karyawan)

    @Update
    suspend fun updatekaryawan(karyawan: karyawan)

    @Delete
    suspend fun deletekaryawan(karyawan: karyawan)

    @Query("SELECT * FROM karyawan")
    suspend fun getAllkaryawan(): List<karyawan>

    @Query("SELECT * FROM karyawan WHERE id=:karyawan_id")
    suspend fun getmakanan(karyawan_id: Int) : List<karyawan>

}