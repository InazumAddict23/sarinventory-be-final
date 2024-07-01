package com.csthesis.sarinventory_be_final.repositories;

import com.csthesis.sarinventory_be_final.entities.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt, Long> {
}
