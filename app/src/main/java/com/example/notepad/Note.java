package com.example.notepad;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Note implements Serializable {
    private String title;
    private String noteText;
    private String time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    Note() {
        this.title = "";
        this.noteText = "";
        this.time = "";
    }

    public String getNoteTitle() {
        return title;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getNoteTime() { return time;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public void updateTime() {
        this.time = LocalDateTime.now().toString();
    }
}