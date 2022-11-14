package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private final CardView item;
    private final TextView noteTitle;
    private Context context;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        item = itemView.findViewById(R.id.notecard);
        noteTitle = itemView.findViewById(R.id.noteTitle);
    }

    public void bind(Note note){
        noteTitle.setText(note.getNoteTitle());
        item.setOnClickListener(view -> {
            Intent intent = new Intent(context,CreateNote.class);
            intent.putExtra("id",note.getId());
            intent.putExtra("noteTitle",note.getNoteTitle());
            intent.putExtra("noteContent",note.getNoteContent());
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.startActivityForResult(intent,NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    static NoteViewHolder create(ViewGroup viewGroup){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.noteitem, viewGroup, false);
        return new NoteViewHolder(view);
    }

}
