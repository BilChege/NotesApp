package com.example.notesapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListNotes extends AppCompatActivity {

    private NoteVIewModel noteVIewModel;
    private RelativeLayout rootElement;
    private RecyclerView notesList;
    private FloatingActionButton addNote;
    private NoteListAdapter adapter;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        rootElement = findViewById(R.id.container);
        notesList = findViewById(R.id.notesList);
        noteVIewModel = new ViewModelProvider(this).get(NoteVIewModel.class);
        addNote = findViewById(R.id.btnAddNote);
        adapter = new NoteListAdapter(new NoteListAdapter.NoteDiff());
        adapter.setContext(ListNotes.this);
        notesList.setAdapter(adapter);
        notesList.setLayoutManager(new LinearLayoutManager(this));
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListNotes.this,CreateNote.class);
                startActivityForResult(intent,NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String mode = data.getStringExtra("mode");
        Log.e(TAG, "onActivityResult: Mode -> "+mode );
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Note note = new Note();
            note.setNoteTitle(data.getStringExtra("title"));
            note.setNoteContent(data.getStringExtra("content"));
            if (mode.equals("save")){
                try {
                    noteVIewModel.insert(note);
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else if (mode.equals("update")){
                note.setId(data.getIntExtra("id",-1));
                try {
                    noteVIewModel.update(note);
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else if (mode.equals("NAN")){
                //Do Nothing
            }
        } else {
            Toast.makeText(ListNotes.this,"An error occurred while saving data",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteVIewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (notes.isEmpty()){

                } else {
                    for (Note note:notes){
                        Log.e(TAG, "onChanged: "+note.getNoteTitle());
                    }
                    adapter.submitList(notes);
                }
            }
        });
    }
}