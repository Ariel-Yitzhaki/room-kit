package com.example.library_building

import androidx.room.Dao
import androidx.room.Query
import com.example.mylibrary.BaseDao

@Dao
interface TestDao : BaseDao<TestItem> {

    // insert / insertAll / update / delete come from BaseDao automatically

    @Query("SELECT * FROM test_items")
    suspend fun getAll(): List<TestItem>
}