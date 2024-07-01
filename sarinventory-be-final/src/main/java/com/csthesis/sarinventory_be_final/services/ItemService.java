package com.csthesis.sarinventory_be_final.services;

import com.csthesis.sarinventory_be_final.entities.Item;
import com.csthesis.sarinventory_be_final.repositories.CategoryRepository;
import com.csthesis.sarinventory_be_final.repositories.ItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
//    @Autowired
//    private final Logger log = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private final ItemRepository itemRepo;

    @Autowired
    private final CategoryRepository categoryRepo;

    @Autowired
    private final EntityManager entityManager;

    public List<Item> findTopSellingItems(int limit) {
        List<Item> allItems = itemRepo.findAll();
        return allItems.stream()
                .sorted((item1, item2) -> item2.getSold() - item1.getSold())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public ItemService(ItemRepository itemRepo, CategoryRepository categoryRepo, EntityManager entityManager) {

        this.itemRepo = itemRepo;
        this.categoryRepo = categoryRepo;
        this.entityManager = entityManager;
    }

    public Item saveItem(Item item) {
        System.out.println("Item Added: " + item.getName() + " | " + item.getStock() + " units");
        return itemRepo.save(item);
    }

    public List<Item> findAll() {
//        log.info("Item list booted up");
        return itemRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Item findItemById(Long id) {
//        log.info("Item found");
        return itemRepo.findById(id).orElse(null);
    }

    public Item updateItem(Long id, Item item){
        Item itemToUpdate = itemRepo.findById(id).orElseThrow(() -> new RuntimeException("Item does not exist"));

        itemToUpdate.setName(item.getName());
        itemToUpdate.setPrice(item.getPrice());
        itemToUpdate.setStock(item.getStock());
        itemToUpdate.setDateModified(item.getDateModified());;

        return itemRepo.save(itemToUpdate);
    }

    public void addStock (Long id, int amount) {
        Item item = itemRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));

        item.setStock(item.getStock() + amount);
        item.setDateModified(item.getDateModified());
        itemRepo.save(item);

        System.out.println(amount + " units added to " + item.getName() + "\n" + " NEW STOCK AMOUNT: " + item.getStock());
    }

    public void subtractStock (Long id, int amount) {
        Item item = itemRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(("Item not found")));

        int currentStock = item.getStock();
        int sold = item.getSold();
        if (currentStock < amount) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        item.setStock(currentStock - amount);
        item.setSold(sold + amount);
        item.setDateModified(item.getDateModified());

        itemRepo.save(item);
//        log.info(amount + " units subtracted to " + item.getName() + "\n" + " NEW STOCK AMOUNT: " + item.getStock());
    }

    public void updatePrice(Long id, double price) {
        Item item = itemRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));

        item.setPrice(price);
        item.setDateModified(item.getDateModified());

        itemRepo.save(item);

        System.out.println(item.getName() + "'s price changed to " + item.getPrice());
//        log.info(item.getName() + "'s price changed to " + item.getPrice());
    }

    public List<Item> findAllFiltered(boolean isDeleted){
        Session session = entityManager.unwrap(Session.class);

        Filter filter = session.enableFilter("deletedItemFilter");
        filter.setParameter("isDeleted", isDeleted);

        List<Item> items =  itemRepo.findAll();
        session.disableFilter("deletedItemFilter");

        return items;
    }

    public void deleteItem (Long id){
        Item item = itemRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));

        itemRepo.deleteById(id);

//        log.info(item.getName() + " deleted from inventory");
        System.out.println(item.getName() + " deleted from inventory");
    }
}
