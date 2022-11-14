package com.example.notesapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    public NoteDAO noteDAO;
    private LiveData<List<Note>> allNotes;

    NoteRepository(Application application){
        NoteDataBase noteDataBase = NoteDataBase.getDatabase(application);
        noteDAO = noteDataBase.noteDAO();
        allNotes = noteDAO.getNotes();
    }

    LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    void insert(Note note){
        NoteDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDAO.insert(note);
            }
        });
    }

    void update(Note note){
        NoteDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDAO.update(note);
            }
        });
    }

}
