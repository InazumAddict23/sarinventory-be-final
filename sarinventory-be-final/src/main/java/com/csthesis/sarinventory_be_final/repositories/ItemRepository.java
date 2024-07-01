//package com.thesis2.sarinventorybackend.repositories;
//
//import com.thesis2.sarinventorybackend.entities.Item;
//import jakarta.persistence.Entity;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.EntityGraph;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ItemRepository extends JpaRepository<Item, Long> {
//
//}


// ItemRepository.java
package com.csthesis.sarinventory_be_final.repositories;

import com.csthesis.sarinventory_be_final.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
