package goit.project.note.repo;


import goit.project.note.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    @Query("SELECT n FROM Note n WHERE n.title LIKE %:keyword%")
    List<Note> findByTitleContaining(@Param("keyword") String keyword);

    List<Note> findByUserId(UUID userId);

    @Query("SELECT COALESCE(MAX(n.noteNumber), 0) FROM Note n WHERE n.userId = :userId")
    Optional<Integer> findMaxNoteNumberByUserId(@Param("userId") UUID userId);
}
