package dev.fejda.latinoamericacomparte.controller;

import dev.fejda.latinoamericacomparte.model.constant.Purpose;
import dev.fejda.latinoamericacomparte.model.constant.Status;
import dev.fejda.latinoamericacomparte.model.dto.NewsDTO;
import dev.fejda.latinoamericacomparte.model.dto.TestimonyDTO;
import dev.fejda.latinoamericacomparte.model.service.ContactService;
import dev.fejda.latinoamericacomparte.model.service.NewsService;
import dev.fejda.latinoamericacomparte.model.service.TestimonyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ContactService contactService;
    private final TestimonyService testimonyService;
    private final NewsService newsService;

    public AdminController(ContactService contactService, TestimonyService testimonyService, NewsService newsService) {
        this.contactService = contactService;
        this.testimonyService = testimonyService;
        this.newsService = newsService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    // ==================== CONTACTOS ====================
    @GetMapping("/contacts")
    public String listContacts(Model model) {
        model.addAttribute("contacts", contactService.findAllContact());
        model.addAttribute("purposes", Purpose.values());
        return "admin/contacts";
    }

    @GetMapping("/contacts/{id}/delete")
    public String deleteContact(@PathVariable Long id) {
        try {
            contactService.deleteContact(id);
        } catch (EntityNotFoundException e) {
            return "error/not-found";
        }
        return "redirect:/admin/contacts";
    }

    // ==================== TESTIMONIOS PENDIENTES ====================
    @GetMapping("/testimonies/pending")
    public String pendingTestimonies(Model model) {
        model.addAttribute("testimonies", testimonyService.getPendingTestimonies());
        return "admin/pending-testimonies";
    }

    @PostMapping("/testimonies/{id}/approve")
    public String approveTestimony(@PathVariable Long id) {
        try {
            testimonyService.approveTestimony(id);
        } catch (EntityNotFoundException e) {
            return "error/not-found";
        }
        return "redirect:/admin/testimonies/pending";
    }

    @PostMapping("/testimonies/{id}/reject")
    public String rejectTestimony(@PathVariable Long id, @RequestParam String reason) {
        try {
            testimonyService.rejectTestimony(id, reason);
        } catch (EntityNotFoundException e) {
            return "error/not-found";
        }
        return "redirect:/admin/testimonies/pending";
    }

    // ==================== TESTIMONIOS APROBADOS ====================
    @GetMapping("/testimonies/approved")
    public String approvedTestimonies(Model model) {
        model.addAttribute("testimonies", testimonyService.getApprovedTestimonies());
        return "admin/approved-testimonies";
    }

    @GetMapping("/testimonies/create")
    public String createTestimonyForm(Model model) {
        model.addAttribute("testimonyRequest", new TestimonyDTO("", "", "", "", null));
        return "admin/testimony-create";
    }

    @PostMapping("/testimonies")
    public String createTestimony(@ModelAttribute("testimonyRequest") TestimonyDTO dto) {
        testimonyService.createTestimony(dto);
        return "redirect:/admin/testimonies/approved";
    }

    @GetMapping("/testimonies/{id}/edit")
    public String editTestimonyForm(@PathVariable Long id, Model model) {
        Optional<dev.fejda.latinoamericacomparte.model.entity.Testimony> testimony = testimonyService.findTestimonyById(id);
        if (testimony.isEmpty()) {
            return "error/not-found";
        }
        model.addAttribute("testimony", testimony.get());
        return "admin/testimony-edit";
    }

    @PostMapping("/testimonies/{id}")
    public String updateTestimony(@PathVariable Long id, @ModelAttribute("testimony") TestimonyDTO dto) {
        try {
            testimonyService.updateTestimony(id, dto);
        } catch (EntityNotFoundException e) {
            return "error/not-found";
        }
        return "redirect:/admin/testimonies/approved";
    }

    @PostMapping("/testimonies/{id}/delete")
    public String deleteTestimony(@PathVariable Long id) {
        try {
            testimonyService.deleteTestimony(id);
        } catch (EntityNotFoundException e) {
            return "error/not-found";
        }
        return "redirect:/admin/testimonies/approved";
    }

    // ==================== NOTICIAS ====================
    @GetMapping("/news")
    public String listNews(Model model) {
        model.addAttribute("news", newsService.findAllNews());
        return "admin/news";
    }

    @GetMapping("/news/create")
    public String createNewsForm(Model model) {
        model.addAttribute("newsRequest", new NewsDTO("", "", "", "", "", Status.STATUS_DRAFT.name()));
        return "admin/news-create";
    }

    @PostMapping("/news")
    public String createNews(@ModelAttribute("newsRequest") NewsDTO dto) {
        newsService.createNews(dto);
        return "redirect:/admin/news";
    }

    @GetMapping("/news/{id}/edit")
    public String editNewsForm(@PathVariable Long id, Model model) {
        var news = newsService.findNewsById(id);
        if (news.isEmpty()) {
            return "error/not-found";
        }
        model.addAttribute("news", news.get());
        return "admin/news-edit";
    }

    @PostMapping("/news/{id}")
    public String updateNews(@PathVariable Long id, @ModelAttribute("news") NewsDTO dto) {
        try {
            newsService.updateNews(id, dto);
        } catch (EntityNotFoundException e) {
            return "error/not-found";
        }
        return "redirect:/admin/news";
    }

    @PostMapping("/news/{id}/delete")
    public String deleteNews(@PathVariable Long id) {
        try {
            newsService.deleteNews(id);
        } catch (EntityNotFoundException e) {
            return "error/not-found";
        }
        return "redirect:/admin/news";
    }

    @PostMapping("/news/{id}/publish")
    public String publishNews(@PathVariable Long id) {
        try {
            newsService.publishNews(id);
        } catch (EntityNotFoundException e) {
            return "error/not-found";
        }
        return "redirect:/admin/news";
    }
}