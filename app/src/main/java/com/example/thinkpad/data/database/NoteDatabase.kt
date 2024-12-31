package com.example.thinkpad.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.thinkpad.data.dao.NoteDao
import com.example.thinkpad.data.entity.Note


@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}