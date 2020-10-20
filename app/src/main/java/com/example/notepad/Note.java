package com.example.notepad;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class Note {
    private String title;
    private String noteText;
    private LocalDateTime time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    Note() {
        this.title = "";
        this.noteText = "";
        this.time = now();
    }

    public String getTitle() {
        return title;
    }

    public String getNoteText() {
        return noteText;
    }
}