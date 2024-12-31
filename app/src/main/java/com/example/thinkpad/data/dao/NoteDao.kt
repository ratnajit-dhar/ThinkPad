//this class consists of the methods that rest of the app can use to interact with the database

package com.example.thinkpad.data.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.thinkpad.data.entity.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("Select * from notes ORDER BY date Desc") //this query is compile time safe,which is a benefit of room
    fun getAllNotes(): Flow<List<Note>>

//    @Insert
//    suspend fun insertNote(note: Note)
    @Insert(onConflict = OnConflictStrategy.REPLACE) // REPLACE will overwrite conflicts
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)


}