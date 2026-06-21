package com.example.library_building

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mylibrary.RoomKitConverters

@Database(entities = [TestItem::class], version = 1)
@TypeConverters(RoomKitConverters::class)
abstract class TestDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao
}