package com.example.notepad;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private static final String TAG = "NotesAdapter";
    private List<Note> noteList;
    private MainActivity mainAct;

    NotesAdapter(List<Note> noteList, MainActivity ma) {
        this.noteList = noteList;
        mainAct = ma;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW NoteViewHolder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new NoteViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Note " + position);

        Note note = noteList.get(position);
        String text = note.getNoteText();
        int textSize = Math.min(79, text.length());
        String displayText = textSize < 79 ? text.substring(0, textSize) : text.substring(0, textSize) + "...";

        holder.noteTitle.setText(note.getNoteTitle());
        holder.noteText.setText(displayText);
        holder.noteTime.setText(note.getNoteTime());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}