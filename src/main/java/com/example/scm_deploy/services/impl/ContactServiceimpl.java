package com.example.scm_deploy.services.impl;

import com.example.scm_deploy.entities.Contact;
import com.example.scm_deploy.helpers.ResourceNotFoundException;
import com.example.scm_deploy.services.ContactService;
import com.example.scm_deploy.repositories.contactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceimpl implements ContactService {
  @Autowired
    private contactRepo contactRepo;
    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return  contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        return null;
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("contact not found"+id));
    }

    @Override
    public void delete(String id) {
      var contact =  contactRepo.findById(id)
              .orElseThrow(()-> new ResourceNotFoundException("contact not found"+id));
contactRepo.delete(contact);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        return List.of();
    }
    public List<Contact> getByUserId(String userId){
        return contactRepo.findByUserId(userId);
    }
}
