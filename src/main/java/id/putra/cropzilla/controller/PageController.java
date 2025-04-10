package id.putra.cropzilla.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/menu")
    public String menu(@AuthenticationPrincipal OidcUser user, Model model) {
        if (user != null) {
            System.out.println(user);
            model.addAttribute("username", user.getPreferredUsername());
        } else {
            return "redirect:/"; // Redirect to home if not authenticated
        }
        return "menu";
    }

}
