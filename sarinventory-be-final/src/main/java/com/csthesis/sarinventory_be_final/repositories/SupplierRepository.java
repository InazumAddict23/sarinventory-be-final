package com.csthesis.sarinventory_be_final.repositories;

import com.csthesis.sarinventory_be_final.entities.Item;
import com.csthesis.sarinventory_be_final.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findAllByUserId(Long user_id);

}
