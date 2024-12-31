//This class consists of all the functions that will create the instances of the dependencies that
//we will need to inject

package com.example.thinkpad.di

import android.content.Context
import androidx.room.Room
import com.example.thinkpad.data.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "NoteDatabase"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase) = db.noteDao()
}

//room is a easy way to implement sql db. Room provides compile time verification of the sql queries

//Singleton component means the instances will be destroyed as soon as the
//app gets destroyed