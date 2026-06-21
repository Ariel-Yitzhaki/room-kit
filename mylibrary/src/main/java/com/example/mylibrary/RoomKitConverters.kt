package com.example.mylibrary

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import java.util.Date
import java.util.UUID

class RoomKitConverters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromUuidString(value: String?): UUID? = value?.let { UUID.fromString(it) }

    @TypeConverter
    fun uuidToString(uuid: UUID?): String? = uuid?.toString()

    @TypeConverter
    fun fromStringListJson(value: String?): List<String>? =
        value?.let { json.decodeFromString(ListSerializer(String.serializer()), it) }

    @TypeConverter
    fun stringListToJson(list: List<String>?): String? =
        list?.let { json.encodeToString(ListSerializer(String.serializer()), it) }
}