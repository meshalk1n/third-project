package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findOne(int id){
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updateBook){
        updateBook.setId(id);
        bookRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public Optional<Person> getBookOwner (int id){
        return bookRepository.findById(id).map(Book::getOwner);
    }

    @Transactional
    public void release ( int id){
        Book releasedBook = bookRepository.findById(id).get();
        releasedBook.setOwner(null);
        bookRepository.save(releasedBook);
    }

    @Transactional
    public void assign ( int id, Person selectedPerson){
        Book book = bookRepository.findById(id).get();
        book.setOwner(selectedPerson);
        bookRepository.save(book);
    }
}