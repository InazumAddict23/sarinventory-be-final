package com.csthesis.sarinventory_be_final.services;

import com.csthesis.sarinventory_be_final.entities.Supplier;
import com.csthesis.sarinventory_be_final.repositories.SupplierRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

//    @Autowired
//    private final Logger log = LoggerFactory.getLogger(SupplierService.class);

    @Autowired
    private final SupplierRepository supplierRepo;

    @Autowired
    private final EntityManager entityManager;

    public SupplierService (SupplierRepository supplierRepo, EntityManager entityManager) {
        this.supplierRepo = supplierRepo;
        this.entityManager = entityManager;
    }

    public Supplier saveSupplier(Supplier supplier) {
        System.out.println("Supplier Added: " + supplier.getName() + " | " + supplier.getPhoneNumber());
//        log.info("Supplier Added: " + supplier.getName() + " | " + supplier.getPhoneNumber());

        return supplierRepo.save(supplier);
    }

    public List<Supplier> findAll() {
        return supplierRepo.findAll();
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

    public Supplier updateSupplier(Long id, Supplier supplier) {
        Supplier supplierToUpdate = supplierRepo.findById(id).orElseThrow(() -> new RuntimeException("Supplier does not exist"));

        supplierToUpdate.setName(supplier.getName());
        supplierToUpdate.setPhoneNumber(supplier.getPhoneNumber());
        supplierToUpdate.setDateModified(supplier.getDateModified());

        System.out.println("Supplier Edited: " + supplier.getName() + " | " + supplier.getPhoneNumber());
//        log.info("Supplier Edited: " + supplier.getName() + " | " + supplier.getPhoneNumber());

        return supplierRepo.save(supplierToUpdate);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        supplierRepo.deleteById(id);

        System.out.println("Supplier Deleted: " + supplier.getName() + " | " + supplier.getPhoneNumber());
//        log.info("Supplier Deleted: " + supplier.getName() + " | " + supplier.getPhoneNumber());
    }
}
