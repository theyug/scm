package com.example.scm_deploy.services;

import com.example.scm_deploy.entities.Contact;

import java.util.List;

public interface ContactService {
    Contact save(Contact contact);
    Contact update(Contact contact);
    List<Contact> getAll();
    Contact getById(String id);
    void delete(String id);
    //search contact
    List<Contact> search(String name , String email, String phoneNumber);
}
