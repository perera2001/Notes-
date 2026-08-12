package com.example.notes.service.impl;

import com.example.notes.dto.request.NoteRequestDTO;
import com.example.notes.dto.response.NoteResponseDTO;
import com.example.notes.entity.Note;
import com.example.notes.entity.User;
import com.example.notes.repo.NoteRepository;
import com.example.notes.repo.UserRepository;
import com.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceIMPL implements NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public NoteResponseDTO createNote(NoteRequestDTO noteRequestDTO) {
        User user = getLoggedInUser();
        Note note = new Note();
        note.setTitle(noteRequestDTO.getTitle());
        note.setContent(noteRequestDTO.getContent());
        note.setUser(user);
        Note savedNote = noteRepository.save(note);

        return new NoteResponseDTO(
                savedNote.getId(),
                savedNote.getTitle(),
                savedNote.getContent()
        );
    }

    @Override
    public List<NoteResponseDTO> getMyNotes() {
        User user = getLoggedInUser();

        return noteRepository.findByUser(user)
                .stream()
                .map(note-> new NoteResponseDTO(
                        note.getId(),
                        note.getTitle(),
                        note.getContent()
                ))
                .toList();
    }

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}
