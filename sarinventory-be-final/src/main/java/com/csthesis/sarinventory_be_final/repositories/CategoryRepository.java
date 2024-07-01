package com.csthesis.sarinventory_be_final.repositories;

import com.csthesis.sarinventory_be_final.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
