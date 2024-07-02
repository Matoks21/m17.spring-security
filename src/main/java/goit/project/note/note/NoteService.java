package goit.project.note.note;


import goit.project.note.repo.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> listAllByUserId(UUID userId) {
        return noteRepository.findByUserId(userId);
    }

    public Note add(Note note, UUID userId) {
        return createNoteForUser(note, userId);
    }

    public void deleteById(UUID id) {
        noteRepository.deleteById(id);
    }

    public void update(Note note) {
        noteRepository.save(note);
    }

    public Note getById(UUID id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note with id " + id + " not found."));
    }

    public Note createNoteForUser(Note note, UUID userId) {
        int lastNoteNumber = noteRepository.findMaxNoteNumberByUserId(userId)
                .orElse(0);
        note.setNoteNumber((long) lastNoteNumber + 1);
        note.setUserId(userId);
        note.setId(UUID.randomUUID());
        return noteRepository.save(note);
    }
}
