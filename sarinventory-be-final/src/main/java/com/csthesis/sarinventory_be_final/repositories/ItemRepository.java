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
import com.csthesis.sarinventory_be_final.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByUserId(Long user_id);

    @Query("SELECT i FROM Item i WHERE i.user.id = :user_id ORDER BY i.sold DESC")
    List<Item> findTopSellingItemsByUserId(@Param("user_id") Long user_id);

    @Query("SELECT i FROM Item i WHERE i.user.id = :userId AND i.lastSaleDate >= :startDate ORDER BY i.sold DESC")
    List<Item> findTopSellingItemsByUserIdAndDateRange(@Param("userId") Long user_id, @Param("startDate") Date startDate);
}
