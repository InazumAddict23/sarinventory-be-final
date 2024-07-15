package com.csthesis.sarinventory_be_final.repositories;

import com.csthesis.sarinventory_be_final.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByUserId(Long userId);

    @Query("SELECT i FROM Item i WHERE i.user.id = :user_id ORDER BY i.sold DESC")
    List<Item> findTopSellingItemsByUserId(@Param("user_id") Long user_id);

    @Query("SELECT i FROM Item i WHERE i.user.id = :user_id AND i.lastSaleDate >= :startDate ORDER BY i.sold DESC")
    List<Item> findTopSellingItemsByUserIdAndDateRange(@Param("user_id") Long user_id, @Param("startDate") Date startDate);

    @Query("SELECT i FROM Item i WHERE i.user.id = :user_id AND i.lastSaleDate >= :startDate")
    List<Item> findItemsSoldByUserIdAndDateRange(@Param("user_id") Long user_id, @Param("startDate") Date startDate);

    @Query("SELECT i FROM Item i WHERE i.user.id = :user_id")
    List<Item> findAllByUserIdIncludingDeleted(@Param("user_id") Long userId);

    @Query("SELECT i FROM Item i WHERE i.id = :id AND i.user.id = :user_id")
    Optional<Item> findByIdAndUserIdIncludingDeleted(@Param("id") Long id, @Param("user_id") Long user_id);
}