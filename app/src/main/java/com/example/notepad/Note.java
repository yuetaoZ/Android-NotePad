package com.example.notepad;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Note {
    private String title;
    private String noteText;
    private LocalDateTime time = LocalDateTime.now();

    @RequiresApi(api = Build.VERSION_CODES.O)
    Note() {
        this.title = "";
        this.noteText = "";
    }

    public String getTitle() {
        return title;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateTime() {
        this.time = LocalDateTime.now();
    }
}