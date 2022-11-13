package com.example.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteVIewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private final LiveData<List<Note>> allNotes;

    public NoteVIewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    public void insert(Note note){
        noteRepository.insert(note);
    }
}
