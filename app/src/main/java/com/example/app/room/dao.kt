package com.example.app.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: Record)

    @Query("SELECT * FROM records WHERE username = :username")
    fun getRecordsByUserId(username: String): Flow<List<Record>>

    @Query("SELECT * FROM records")
    fun getAllRecords(): Flow<List<Record>>

    @Delete()
    suspend fun deleteRecord(record: Record)

    @Query("DELETE FROM records WHERE id = :recordId")
    suspend fun deleteRecordById(recordId: Long)
}
