package com.example.library_building

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = com.example.mylibrary.RoomKit.build<TestDatabase>(applicationContext, "test.db")

        CoroutineScope(Dispatchers.IO).launch {
            db.testDao().insert(
                TestItem(name = "hello", createdAt = Date(), tags = listOf("a", "b", "c"))
            )
            val all = db.testDao().getAll()
            Log.d("RoomKit", "Loaded: $all")
        }
    }
}