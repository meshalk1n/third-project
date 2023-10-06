package org.example.controllers;

import org.example.model.Person;
import org.example.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonServices personServices;

    @Autowired
    public PersonController(PersonServices personServices) {
        this.personServices = personServices;
    }

    @GetMapping()
    public String showAllPeople(Model model) {
        model.addAttribute("people", personServices.findAll());
        return "people/show";
    }

    @GetMapping("/new")
    public String showFormForNewPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String processSaveNewPerson(@ModelAttribute("person") @Valid Person person,
                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        personServices.save(person);
        return "redirect:/people/";
    }

    @GetMapping("/{id}")
    public String processFetchPersonById(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personServices.findOne(id));
        return "people/id";
    }

    @GetMapping("/{id}/edit")
    public String showFormEdit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personServices.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String processEditForm(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personServices.update(id, person);
        return "redirect:/people/";
    }

    @DeleteMapping("/{id}")
    public String processDeletePerson(@PathVariable("id") int id) {
        personServices.delete(id);
        return "redirect:/people/";
    }
}