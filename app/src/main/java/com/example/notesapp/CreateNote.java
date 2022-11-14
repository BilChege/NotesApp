package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNote extends AppCompatActivity {

    private EditText title, content;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        title = findViewById(R.id.noteTitle);
        content = findViewById(R.id.noteContent);
        save = findViewById(R.id.btnSaveNote);
        save.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(title.getText())){
                title.setError("Enter the title for your note");
            } else if (TextUtils.isEmpty(content.getText().toString())){
                content.setError("Enter something to note");
            } else {
                String strTitle = title.getText().toString();
                String strContent = content.getText().toString();
                replyIntent.putExtra("title",strTitle);
                replyIntent.putExtra("content",strContent);
                setResult(RESULT_OK,replyIntent);
            }
            finish();
        });
    }
}