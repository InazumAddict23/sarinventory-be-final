package com.csthesis.sarinventory_be_final.repositories;

import com.csthesis.sarinventory_be_final.entities.Debtor;
import com.csthesis.sarinventory_be_final.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtorRepository extends JpaRepository<Debtor, Long> {
    List<Debtor> findAllByUserId(Long user_id);
}
