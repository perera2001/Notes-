package com.example.notes.dto.request;

public class NoteRequestDTO {
    private String title;
    private String content;

    public NoteRequestDTO() {
    }

    public NoteRequestDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
