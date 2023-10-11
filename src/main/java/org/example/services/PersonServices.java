package org.example.services;

import org.example.models.Person;
import org.example.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonServices {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServices(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson){
        updatePerson.setId(id);
        personRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }
}