package goit.project.mvc;

import goit.project.auth.AuthService;
import goit.project.role.Role;
import goit.project.role.RoleDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RequestMapping("/role")
@Controller
public class RoleController {
    private final RoleDAO roleDAO;
    private final AuthService authService;


    @GetMapping
    public ModelAndView list() {
        if (!authService.hasAuthority("ADMIN")) {
            return new ModelAndView("homepage");
        }
        ModelAndView result = new ModelAndView("admin/role");
        String error = null;
        List<Role> roles = null;
        try {
            roles = roleDAO.findAll();
        } catch (Exception ex) {
            error = ex.getMessage();
        }
        result.addObject("roles", roles);
        result.addObject("result", error);
        return result;
    }


    @PostMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") UUID id,
                               @RequestParam("name") String name) {
        if (!authService.hasAuthority("ADMIN")) {
            return new ModelAndView("homepage");
        }
        ModelAndView result = new ModelAndView("admin/role");
        String error;
        List<Role> roles = null;
        try {
            Role role = new Role(name);
            role.setId(id);
            roleDAO.save(role);
            roles = roleDAO.findAll();
            error = "true";
        } catch (Exception ex) {
            error = ex.getMessage();
        }
        result.addObject("roles", roles);
        result.addObject("result", error);
        return result;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") UUID id, Model model) {
        if (!authService.hasAuthority("ADMIN")) {
            return new ModelAndView("homepage");
        }
        ModelAndView result = new ModelAndView("admin/role");
        String error;
        List<Role> roles = null;


        try {
            roleDAO.deleteById(id);
            roles = roleDAO.findAll();
            error = "true";
        } catch (Exception ex) {
            error = ex.getMessage();
        }
        result.addObject("roles", roles);
        result.addObject("result", error);
        return result;
    }
}