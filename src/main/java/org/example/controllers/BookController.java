package org.example.controllers;

import org.example.models.Book;
import org.example.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookServices bookServices;

    @Autowired
    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @GetMapping()
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookServices.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String showFormForNewBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String processSaveNewBook(@ModelAttribute("book") @Valid Book book,
                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookServices.save(book);
        return "redirect:/books/";
    }

    @GetMapping("/{id}")
    public String processFetchBookById(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookServices.findOne(id));
        return "books/id";
    }

    @GetMapping("/{id}/edit")
    public String showFormEdit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookServices.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String processEditForm(@PathVariable("id") int id, @ModelAttribute("person") @Valid Book book,
                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookServices.update(id, book);
        return "redirect:/books/";
    }

    @DeleteMapping("/{id}")
    public String processDeleteBook(@PathVariable("id") int id) {
        bookServices.delete(id);
        return "redirect:/books/";
    }
}