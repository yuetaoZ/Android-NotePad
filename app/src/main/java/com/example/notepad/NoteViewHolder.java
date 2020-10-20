package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    TextView noteTitle;
    TextView noteText;
    TextView noteTime;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        noteTitle = itemView.findViewById(R.id.noteTitle);
        noteText = itemView.findViewById(R.id.noteText);
        noteTime = itemView.findViewById(R.id.noteTime);
    }
}