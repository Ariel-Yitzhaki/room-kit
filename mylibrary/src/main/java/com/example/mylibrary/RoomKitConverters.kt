package com.example.mylibrary

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import java.util.Date
import java.util.UUID

/**
 * Ready-made Room [TypeConverter]s for common types that Room cannot
 * store natively.
 *
 * Register on your database with:
 *   @TypeConverters(RoomKitConverters::class)
 *
 * Each type has two converters: one for reading (stored value -> object)
 * and one for writing (object -> stored value). Room selects the correct
 * one automatically based on the parameter and return types.
 */
class RoomKitConverters {
    private val json = Json { ignoreUnknownKeys = true }

    // Date <-> Long (epoch milliseconds)

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    // UUID <-> String

    @TypeConverter
    fun fromUuidString(value: String?): UUID? = value?.let { UUID.fromString(it) }

    @TypeConverter
    fun uuidToString(uuid: UUID?): String? = uuid?.toString()

    // List<String> <-> JSON String
    // Stored as JSON so any string content (commas, special chars) is
    // preserved safely.

    @TypeConverter
    fun fromStringListJson(value: String?): List<String>? =
        value?.let { json.decodeFromString(ListSerializer(String.serializer()), it) }

    @TypeConverter
    fun stringListToJson(list: List<String>?): String? =
        list?.let { json.encodeToString(ListSerializer(String.serializer()), it) }
}