package com.example.notes.service;

import com.example.notes.dto.request.NoteRequestDTO;
import com.example.notes.dto.response.NoteResponseDTO;

public interface NoteService {
    NoteResponseDTO createNote(NoteRequestDTO noteRequestDTO);
}
