package com.example.mylibrary

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Generic DAO providing common write operations for any entity type.
 * Extend this in your own DAO to inherit insert/update/delete for free.
 *
 * Example:
 *   @Dao interface UserDao : BaseDao<User>
 */

interface BaseDao<T> {

    /** Insert a single item. Replaces on primary-key conflict. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T)

    /** Insert multiple items in one call. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<T>)

    /** Update an existing item, matched by primary key. */
    @Update
    suspend fun update(item: T)

    /** Delete an item. */
    @Delete
    suspend fun delete(item: T)
}