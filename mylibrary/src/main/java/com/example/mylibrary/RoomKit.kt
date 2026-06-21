package com.example.mylibrary

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Convenience helpers for building a Room database with less boilerplate.
 */

object RoomKit {

    /**
     * Build a Room database with one call.
     *
     * Example:
     *   val db = RoomKit.build<AppDatabase>(context, "app.db")
     *
     * @param context any Context (application context is used internally)
     * @param name    the database file name
     * @param fallbackToDestructiveMigration if true, recreates the DB on a
     *        version mismatch instead of crashing (handy during development)
     */
    inline fun <reified T : RoomDatabase> build(
        context: Context,
        name: String,
        fallbackToDestructiveMigration: Boolean = false
    ): T {
        val builder = Room.databaseBuilder(
            context.applicationContext,
            T::class.java,
            name
        )
        if (fallbackToDestructiveMigration) {
            builder.fallbackToDestructiveMigration()
        }
        return builder.build()
    }
}