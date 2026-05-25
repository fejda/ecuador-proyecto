package dev.fejda.latinoamericacomparte.controller;

import dev.fejda.latinoamericacomparte.model.service.NewsService;
import dev.fejda.latinoamericacomparte.model.service.TestimonyService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final TestimonyService testimonyService;
    private final NewsService newsService;

    public HomeController(TestimonyService testimonyService, NewsService newsService) {
        this.testimonyService = testimonyService;
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Obtener SOLO los 4 testimonios más recientes
        var todosLosTestimonios = testimonyService.getApprovedTestimonies();
        var testimoniosRecientes = todosLosTestimonios.stream()
                .limit(4)
                .toList();
        model.addAttribute("testimonies", testimoniosRecientes);
        model.addAttribute("totalTestimonios", todosLosTestimonios.size());

        // Obtener SOLO las 3 noticias más recientes
        var todasLasNoticias = newsService.getPublishedNews();
        var noticiasRecientes = todasLasNoticias.stream()
                .limit(3)
                .toList();
        model.addAttribute("news", noticiasRecientes);
        model.addAttribute("totalNoticias", todasLasNoticias.size());

        // Información de autenticación para el menú
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("userEmail", auth.getName());
        } else {
            model.addAttribute("isAuthenticated", false);
        }

        return "index";
    }
}