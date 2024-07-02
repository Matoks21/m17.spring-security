package goit.project.mvc;


import goit.project.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@RequestMapping("/")
@RestController
public class HomepageController {
    private final AuthService authService;

    @GetMapping
    public ModelAndView get() {
        if (!authService.hasAuthority("ADMIN")) {
            return new ModelAndView("homepage");
        }
        return new ModelAndView("admin/homepage");
    }
}