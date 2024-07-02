package goit.project.note.note;


import goit.project.user.User;
import goit.project.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/note/list")
    public String listUserNotes(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        List<Note> userNotes = noteService.listAllByUserId(user.getId());
        model.addAttribute("notes", userNotes);
        return "note/list";
    }

    @GetMapping("/note/add")
    public String addNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "note/add";
    }

    @PostMapping("/note/add")
    public String addNote(@ModelAttribute @Valid Note note, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "note/add";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        noteService.add(note, user.getId());
        return "redirect:/note/list";
    }

    @GetMapping("/note/edit")
    public String editNoteForm(@RequestParam UUID id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note/edit";
    }

    @PostMapping("/note/edit")
    public String editNote(@ModelAttribute @Valid Note note, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "note/edit";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        note.setUserId(user.getId());
        noteService.update(note);
        return "redirect:/note/list";
    }

    @PostMapping("/note/delete")
    public String deleteNote(@RequestParam UUID id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }
}
