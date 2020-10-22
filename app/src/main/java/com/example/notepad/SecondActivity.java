package com.example.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SecondActivity extends AppCompatActivity {

    private Note note = new Note();
    int pos;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        EditText editNoteTitle = findViewById(R.id.editNoteTitle);
        EditText editNoteText = findViewById(R.id.editNoteText);

        Intent intent = getIntent();
        if (intent.hasExtra("Person")) {
            note = (Note) intent.getSerializableExtra("Note");
            if (note != null) {
                editNoteTitle.setText(note.getNoteTitle());
                editNoteText.setText(note.getNoteText());
            }
        } else {
            editNoteTitle.setText("");
        }

        if (intent.hasExtra("Position")) {
            pos = intent.getIntExtra("Position", -1);
        }

        long time = intent.getLongExtra("Time", 0);

        Toast.makeText(this, "Time: " + time, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //saveNote();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void doReturn() {

        EditText editNoteTitle = findViewById(R.id.editNoteTitle);
        EditText editNoteText = findViewById(R.id.editNoteText);

        note.setTitle(editNoteTitle.getText().toString());
        note.setNoteText(editNoteText.getText().toString());

        Intent intent = new Intent();
        intent.putExtra("Note", note);
        intent.putExtra("Position", pos);
        setResult(RESULT_OK, intent);
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
        doReturn();
        super.onBackPressed();
    }
}



