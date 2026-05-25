package dev.fejda.latinoamericacomparte.controller;

import dev.fejda.latinoamericacomparte.model.constant.Purpose;
import dev.fejda.latinoamericacomparte.model.dto.ContactDTO;
import dev.fejda.latinoamericacomparte.model.dto.PublicTestimonyDTO;
import dev.fejda.latinoamericacomparte.model.service.ContactService;
import dev.fejda.latinoamericacomparte.model.service.NewsService;
import dev.fejda.latinoamericacomparte.model.service.TestimonyService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/public")
public class PublicController {

    private final ContactService contactService;
    private final TestimonyService testimonyService;
    private final NewsService newsService;

    public PublicController(ContactService contactService, TestimonyService testimonyService, NewsService newsService) {
        this.contactService = contactService;
        this.testimonyService = testimonyService;
        this.newsService = newsService;
    }

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        if (!model.containsAttribute("contactRequest")) {
            model.addAttribute("contactRequest", new ContactDTO("", "", "", Purpose.SERVICE, null));
        }
        model.addAttribute("contactpurpose", Purpose.values());
        return "public/contact-form";
    }

    @PostMapping("/contact")
    public String processContact(@Valid @ModelAttribute("contactRequest") ContactDTO contactRequest,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (contactRequest.telephone() != null && !contactRequest.telephone().isEmpty()) {
            String phone = contactRequest.telephone().replaceAll("[^0-9]", "");
            if (!phone.matches("^[0-9]{9,10}$")) {
                result.rejectValue("telephone", "error.telephone", "El teléfono debe tener 9 o 10 dígitos");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("contactpurpose", Purpose.values());
            return "public/contact-form";
        }

        contactService.createContact(contactRequest);

        redirectAttributes.addFlashAttribute("success", "✅ ¡Contacto guardado exitosamente!");
        redirectAttributes.addFlashAttribute("contactEmail", contactRequest.email());
        redirectAttributes.addFlashAttribute("contactName", contactRequest.name());
        redirectAttributes.addFlashAttribute("testimonyRequest", new PublicTestimonyDTO("", "", "", "", contactRequest.email()));

        return "redirect:/public/testimony-form";
    }

    @GetMapping("/testimony-form")
    public String testimonyForm(Model model) {
        return "public/testimony-form";
    }

    @PostMapping("/testimony")
    public String saveTestimony(@ModelAttribute("testimonyRequest") PublicTestimonyDTO testimonyRequest,
                                RedirectAttributes redirectAttributes) {

        if (testimonyRequest.name() == null || testimonyRequest.name().isEmpty() ||
                testimonyRequest.photoURL() == null || testimonyRequest.photoURL().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "⚠️ El nombre y la foto son obligatorios");
            return "redirect:/public/testimony-form";
        }

        testimonyService.createPublicTestimony(testimonyRequest);
        redirectAttributes.addFlashAttribute("success", "✅ ¡Testimonio enviado correctamente! Será revisado por nuestro equipo.");

        return "redirect:/public/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "public/success";
    }

    @GetMapping("/testimonies")
    public String viewTestimonies(Model model) {
        model.addAttribute("testimonies", testimonyService.getApprovedTestimonies());
        return "public/testimonies";
    }

    // NUEVO: Ver TODOS los testimonios
    @GetMapping("/all-testimonies")
    public String viewAllTestimonies(Model model) {
        model.addAttribute("testimonies", testimonyService.getApprovedTestimonies());
        return "public/all-testimonies";
    }

    // NUEVO: Ver TODAS las noticias
    @GetMapping("/all-news")
    public String viewAllNews(Model model) {
        model.addAttribute("news", newsService.getPublishedNews());
        return "public/all-news";
    }

    @GetMapping("/news")
    public String viewNews(Model model) {
        model.addAttribute("news", newsService.getPublishedNews().stream().limit(3).toList());
        return "public/news";
    }

    @GetMapping("/news/{id}")
    public String viewNewsDetail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var newsOpt = newsService.findNewsById(id);
        if (newsOpt.isEmpty() || !"PUBLISHED".equals(newsOpt.get().getStatus())) {
            redirectAttributes.addFlashAttribute("error", "⚠️ Noticia no encontrada");
            return "redirect:/public/news";
        }
        model.addAttribute("news", newsOpt.get());
        return "public/news-detail";
    }
}