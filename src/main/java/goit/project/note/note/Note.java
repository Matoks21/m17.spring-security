package goit.project.note.note;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;


@Data
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    @NotEmpty(message = "Title is required")
    @Size(min = 1, message = "Title must be at least 1 character long")
    private String title;

    @Column(nullable = false)
    @NotEmpty(message = "Content is required")
    @Size(min = 1, message = "Content must be at least 1 character long")
    private String content;

    @Column(name = "note_number")
    private Long noteNumber;


}
