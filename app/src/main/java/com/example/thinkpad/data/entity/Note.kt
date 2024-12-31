package com.example.thinkpad.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Ensure this is set!
    val title: String?,
    val content: String?,
    val date: Long
): Parcelable
