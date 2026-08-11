package com.example.notes.controller;


import com.example.notes.dto.request.NoteRequestDTO;
import com.example.notes.dto.response.NoteResponseDTO;
import com.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping
    public NoteResponseDTO createNote(@RequestBody NoteRequestDTO noteRequestDTO) {
        return noteService.createNote(noteRequestDTO);
    }

}
