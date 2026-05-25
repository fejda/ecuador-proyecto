package dev.fejda.latinoamericacomparte.controller;

import dev.fejda.latinoamericacomparte.model.dto.TestimonyDTO;
import dev.fejda.latinoamericacomparte.model.entity.Testimony;
import dev.fejda.latinoamericacomparte.model.service.TestimonyService;  // ← ESTA LÍNEA FALTA
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/testimony")
public class TestimonyController {

    private final TestimonyService service;

    public TestimonyController(TestimonyService service) {
        this.service = service;
    }

    @GetMapping
    public String redirectToList() {
        return "redirect:/testimony/list";
    }


    @GetMapping("/list")
    public String getTestimonyListTemplate(Model model) {
        model.addAttribute("testimonies", service.findAllTestimony());
        return "testimony/list";
    }


    @GetMapping("/create")
    public String getCreateTestimonyTemplate(Model model) {
        model.addAttribute("testimonyRequest", new TestimonyDTO("", "", "", "", null));
        return "testimony/create";
    }


    @PostMapping
    public String createTestimony(@ModelAttribute("testimonyRequest") TestimonyDTO testimonyRequest) {
        service.createTestimony(testimonyRequest);
        return "redirect:/testimony/list";
    }


    @GetMapping("/{id}/edit")
    public String getUpdateTestimonyTemplate(@PathVariable Long id, Model model) {
        Optional<Testimony> findResult = service.findTestimonyById(id);
        if (findResult.isEmpty()) {
            return "error/not-found";
        }
        model.addAttribute("testimony", findResult.get());
        return "testimony/update";
    }


    @PutMapping("/{id}")
    public String updateTestimony(@PathVariable Long id, @ModelAttribute("testimony") TestimonyDTO request) {
        try {
            service.updateTestimony(id, request);
        } catch (EntityNotFoundException e) {
            return "error/not-found";
        }
        return "redirect:/testimony/list";
    }


    @GetMapping("/delete/{id}")
    public String getDeleteTestimonyTemplate(@PathVariable Long id, Model model) {
        Optional<Testimony> findResult = service.findTestimonyById(id);
        if (findResult.isEmpty()) {
            return "error/not-found";
        }
        model.addAttribute("testimony", findResult.get());
        return "testimony/delete";
    }


    @DeleteMapping("/{id}")
    public String deleteTestimony(@PathVariable Long id) {
        try {
            service.deleteTestimony(id);
        } catch (EntityNotFoundException e) {
            return "error/not-found";
        }
        return "redirect:/testimony/list";
    }
}