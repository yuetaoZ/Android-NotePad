package com.example.notepad;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;
    List<Note> noteList;  // Main content is here
    private RecyclerView recyclerView; // Layout's recyclerview
    private NotesAdapter notesAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        this.setTitle("Multi Notes (" + noteList.size() + ")");

        recyclerView = findViewById(R.id.recycler);
        // Data to recyclerview adapter
        notesAdapter = new NotesAdapter(noteList, this);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("note list", null);
        Type type = new TypeToken<ArrayList<Note>>() {}.getType();
        noteList = gson.fromJson(json, type);

        if (noteList == null) {
            noteList = new ArrayList<>();
        }
    }

    @Override
    protected void onPause() {
        saveData();
        super.onPause();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(noteList);
        editor.putString("note list", json);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.addItem) {
            addNewNote();
            return true;
        } else if (item.getItemId() == R.id.getInfo) {
            getAppInformation();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void getAppInformation() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNewNote() {
        Note newNote = new Note();
        newNote.updateTime();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("Note", newNote);
        intent.putExtra("Position", -1);

        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
    }

    // From OnClickListener
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks
        int pos = recyclerView.getChildLayoutPosition(v);
        Note note = noteList.get(pos);
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("Note", note);
        intent.putExtra("Position", pos);

        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Note originalNote;
                    Note updatedNote = (Note) data.getSerializableExtra("Note");

                    int pos = data.getIntExtra("Position", -1);
                    if (pos == -1) {
                        originalNote = new Note();
                    } else {
                        originalNote = noteList.get(pos);
                    }

                    if (updatedNote != null) {
                        updateNote(originalNote, updatedNote, pos);
                    } else {
                        Toast.makeText(this, "Can not save note without a TITLE", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "SECOND ACTIVITY result not OK!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Unexpected code received: " + requestCode, Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateNote(Note originalNote, Note updatedNote, int pos) {
        if (noteHasChanged(originalNote, updatedNote)) {
            Note newNote = new Note();

            newNote.setTitle(updatedNote.getNoteTitle());
            newNote.setNoteText(updatedNote.getNoteText());
            newNote.updateTime();

            if (pos == -1) {
                noteList.add(0, newNote);
                notesAdapter.notifyItemInserted(0);
            } else {
                noteList.remove(pos);
                notesAdapter.notifyItemRemoved(pos);
                noteList.add(0, newNote);
                notesAdapter.notifyItemInserted(0);
            }

            //saveData();
            Toast.makeText(this, "Result changed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Result didn't change", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean noteHasChanged(Note originalNote, Note updatedNote) {
        return !originalNote.getNoteTitle().equals(updatedNote.getNoteTitle()) || !originalNote.getNoteText().equals(updatedNote.getNoteText());
    }

    // From OnLongClickListener
    @Override
    public boolean onLongClick(final View v) {  // long click listener called by ViewHolder long clicks
        final int pos = recyclerView.getChildLayoutPosition(v);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE");
        builder.setMessage("Do you want to delete the note?");

        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                noteList.remove(pos);
                notesAdapter.notifyItemRemoved(pos);
                saveData();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.show();
        return false;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "The back button was pressed - Bye!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}