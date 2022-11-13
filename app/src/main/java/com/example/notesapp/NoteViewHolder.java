package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private final CardView item;
    private final TextView noteTitle;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        item = itemView.findViewById(R.id.notecard);
        noteTitle = itemView.findViewById(R.id.noteTitle);
    }

    public void bind(Note note){
        noteTitle.setText(note.getNoteTitle());
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    static NoteViewHolder create(ViewGroup viewGroup){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.noteitem, viewGroup, false);
        return new NoteViewHolder(view);
    }

}
