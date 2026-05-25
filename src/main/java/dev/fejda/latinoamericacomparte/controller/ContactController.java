package dev.fejda.latinoamericacomparte.controller;

import dev.fejda.latinoamericacomparte.model.constant.Purpose;
import dev.fejda.latinoamericacomparte.model.dto.ContactDTO;
import dev.fejda.latinoamericacomparte.model.entity.Contact;
import dev.fejda.latinoamericacomparte.model.service.ContactService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service){
        this.service = service;
    }

    @GetMapping
    public String redirectToList(){
        return "redirect:/contact/list";
    }

    @GetMapping("/list")
    public String getContactListTemplate(Model model){
        model.addAttribute("contacts", service.findAllContact());
        return "contact/list";
    }

    @GetMapping("/create")
    public String getCreateContactTemplate(Model model){
        model.addAttribute("contactRequest", new ContactDTO("", "", "", Purpose.SERVICE, null));
        model.addAttribute("contactpurpose", Purpose.values());
        return "contact/create";
    }

    @PostMapping
    public String createContact(@ModelAttribute("contactRequest") ContactDTO contactRequest){
        service.createContact(contactRequest);
        return "redirect:/contact/list";
    }

    @GetMapping("/{id}/edit")
    public String getUpdateContactTemplate(@PathVariable Long id, Model model){
        Optional<Contact> findResult = service.findContactById(id);
        if (findResult.isEmpty()){
            return "error/not-found";
        }
        model.addAttribute("contact", findResult.get());
        model.addAttribute("contactpurpose", Purpose.values());
        return "contact/update";
    }

    @PutMapping("/{id}")
    public String updateContact(@PathVariable Long id, @ModelAttribute("contact") ContactDTO request){
        try {
            service.updateContact(id, request);
        } catch (EntityNotFoundException e){
            return "error/not-found";
        }
        return "redirect:/contact/list";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteContactTemplate(@PathVariable Long id, Model model){
        Optional<Contact> findResult = service.findContactById(id);
        if (findResult.isEmpty()){
            return "error/not-found";
        }
        model.addAttribute("contact", findResult.get());
        return "contact/delete";
    }

    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable Long id){
        try {
            service.deleteContact(id);
        } catch (EntityNotFoundException e){
            return "error/not-found";
        }
        return "redirect:/contact/list";
    }
}