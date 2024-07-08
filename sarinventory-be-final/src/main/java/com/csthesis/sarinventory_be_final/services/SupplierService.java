package com.csthesis.sarinventory_be_final.services;

import com.csthesis.sarinventory_be_final.entities.Item;
import com.csthesis.sarinventory_be_final.entities.Supplier;
import com.csthesis.sarinventory_be_final.entities.User;
import com.csthesis.sarinventory_be_final.repositories.SupplierRepository;
import com.csthesis.sarinventory_be_final.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
public class SupplierService {

//    @Autowired
//    private final Logger log = LoggerFactory.getLogger(SupplierService.class);

    @Autowired
    private final SupplierRepository supplierRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private final EntityManager entityManager;

    public SupplierService (SupplierRepository supplierRepo, EntityManager entityManager) {
        this.supplierRepo = supplierRepo;
        this.entityManager = entityManager;
    }

    public Supplier saveSupplier(Supplier supplier, Authentication auth) {

        if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be empty / May laman dapat ang supplier name");
        }

        if (supplier.getPhoneNumber() == null || supplier.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier phone number should not be empty / May laman dapat ang supplier number");
        }

        supplier.setUser((User) userRepo.findByUsername(auth.getName()).get());
        System.out.println("Supplier Added: " + supplier.getName() + " | " + supplier.getPhoneNumber());
//        log.info("Supplier Added: " + supplier.getName() + " | " + supplier.getPhoneNumber());
        return supplierRepo.save(supplier);
    }

    public List<Supplier> findAll() {
        return supplierRepo.findAll();
    }

    public List<Supplier> findAllById(Long id) {
//        log.info("Item list booted up");
        return supplierRepo.findAllByUserId(id);
    }

    public List<Supplier> findAllFiltered(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);

        Filter filter = session.enableFilter("deletedSupplierFilter");
        filter.setParameter("isDeleted", isDeleted);

        List<Supplier> suppliers = supplierRepo.findAll();
        session.disableFilter("deletedSupplierFilter");

        return suppliers;
    }

    public Supplier findSupplierById (Long id) {
        return supplierRepo.findById(id).orElse(null);
    }

    public Supplier updateSupplier(Long id, String newName, String newPhoneNo) {
        Supplier supplierToUpdate = supplierRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier does not exist"));

        boolean updated = false;

        if (newName != null && !newName.trim().isEmpty()) {
            supplierToUpdate.setName(newName);
            System.out.println("New Supplier Name:" + supplierToUpdate.getName());
            updated = true;
        }

        if (newPhoneNo != null && !newPhoneNo.trim().isEmpty()) {
            supplierToUpdate.setPhoneNumber(newPhoneNo);
            System.out.println("New Phone Number: " + supplierToUpdate.getPhoneNumber());
            updated = true;
        }

        if (updated) {
            supplierToUpdate.setDateModified(new Date());
        }

        System.out.println("Supplier Edited: " + supplierToUpdate.getName() + " | " + supplierToUpdate.getPhoneNumber());
//        log.info("Supplier Edited: " + supplier.getName() + " | " + supplier.getPhoneNumber());

        return supplierRepo.save(supplierToUpdate);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        supplier.setUser(null);
        supplierRepo.deleteById(id);

        System.out.println("Supplier Deleted: " + supplier.getName() + " | " + supplier.getPhoneNumber());
//        log.info("Supplier Deleted: " + supplier.getName() + " | " + supplier.getPhoneNumber());
    }
}
