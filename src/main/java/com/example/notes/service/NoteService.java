package com.example.notes.service;

import com.example.notes.dto.request.NoteRequestDTO;
import com.example.notes.dto.response.NoteResponseDTO;

import java.util.List;

public interface NoteService {
    NoteResponseDTO createNote(NoteRequestDTO noteRequestDTO);

    List<NoteResponseDTO> getMyNotes();
}
