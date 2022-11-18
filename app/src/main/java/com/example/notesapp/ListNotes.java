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
        noteVIewModel = new ViewModelProvider(this).get(NoteVIewModel.class);
        addNote = findViewById(R.id.btnAddNote);
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
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Note note = new Note();
            note.setNoteTitle(data.getStringExtra("title"));
            note.setNoteContent(data.getStringExtra("content"));
            noteVIewModel.insert(note);
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
                    if (rootElement.getChildCount() > 1){
                        RelativeLayout relativeLayout = (RelativeLayout) rootElement.getChildAt(1);
                        View view = relativeLayout.getChildAt(0);
                        if (!(view instanceof TextView)){
                            rootElement.removeViewAt(1);
                            initTextView();
                        }
                    } else {
                        initTextView();
                    }
                } else {
                    if (rootElement.getChildCount() > 1){
                        RelativeLayout relativeLayout = (RelativeLayout) rootElement.getChildAt(1);
                        View view = relativeLayout.getChildAt(0);
                        if (!(view instanceof RecyclerView)){
                            rootElement.removeViewAt(1);
                            initRecyclerView(notes);
                        } else {
                            adapter.submitList(notes);
                        }
                    } else {
                        initRecyclerView(notes);
                    }
                }
            }
        });
    }

    private void initTextView() {
        View noNotes = LayoutInflater.from(ListNotes.this).inflate(R.layout.nonotes,rootElement);
        TextView textView = noNotes.findViewById(R.id.emptyText);
        textView.setText("You do not have any notes yet. Please click on the add button below.");
    }

    private void initRecyclerView(List<Note> notes) {
        View notesList = LayoutInflater.from(ListNotes.this).inflate(R.layout.noteslist,rootElement);
        RecyclerView recyclerView = notesList.findViewById(R.id.notesList);
        adapter = new NoteListAdapter(new NoteListAdapter.NoteDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListNotes.this));
        adapter.submitList(notes);
    }
}