package com.csthesis.sarinventory_be_final.services;

import com.csthesis.sarinventory_be_final.entities.Item;
import com.csthesis.sarinventory_be_final.entities.User;
import com.csthesis.sarinventory_be_final.repositories.CategoryRepository;
import com.csthesis.sarinventory_be_final.repositories.ItemRepository;
import com.csthesis.sarinventory_be_final.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ItemService {
//    @Autowired
//    private final Logger log = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private final ItemRepository itemRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final CategoryRepository categoryRepo;

    @Autowired
    private final EntityManager entityManager;

    private Map<Date, Map<Long, Integer>> dailySales = new HashMap<>();

    public List<Item> findTopSellingItems(Long id, int limit) {
        List<Item> allItemsById = itemRepo.findAllByUserId(id);
        return allItemsById.stream()
                .sorted((item1, item2) -> item2.getSold() - item1.getSold())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public ItemService(ItemRepository itemRepo, CategoryRepository categoryRepo, EntityManager entityManager, UserRepository userRepository) {
        this.itemRepo = itemRepo;
        this.categoryRepo = categoryRepo;
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    public Item saveItem(Item item, Authentication auth) {

        if (item.getName() == null || item.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty / Di dapat na walang laman ang Item Name");
        }

        if (item.getPrice() <= 0) {
            throw new IllegalArgumentException("Item price cannot be lower than zero / Di dapat bababa sa 0 and price");
        }

        if (item.getStock() <= 0) {
            throw new IllegalArgumentException("Item stock cannot be lower than zero / Di dapat bababa sa 0 ang stock");
        }

        item.setUser((User) userRepository.findByUsername(auth.getName()).get());
        System.out.println("Item Added: " + item.getName() + " | " + item.getStock() + " units");
        return itemRepo.save(item);
    }

    public List<Item> findAll() {
//        log.info("Item list booted up");
        return itemRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<Item> findAllById(Long id) {
//        log.info("Item list booted up");
        return itemRepo.findAllByUserId(id);
    }

    public Item findItemById(Long id) {
//        log.info("Item found");
        return itemRepo.findById(id).orElse(null);
    }

    @Transactional
    public Item updateItem(Long id, String newName, Integer newPrice){
        Item itemToUpdate = itemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item does not exist"));

        boolean updated = false;

        if (newName != null && !newName.trim().isEmpty()) {
            itemToUpdate.setName(newName);
            System.out.println("New Item Name: " + itemToUpdate.getName());
            updated = true;
        }

        if (newPrice != null) {
            if (newPrice <= 0) {
                throw new IllegalArgumentException("Item price must be greater than 0");
            }
            itemToUpdate.setPrice(newPrice);
            System.out.println("New Price of " + itemToUpdate.getName() + " " + itemToUpdate.getPrice());
            updated = true;
        }

        if (updated) {
            itemToUpdate.setDateModified(new Date());
        }

        System.out.println("Item Edited: " + itemToUpdate.getName() + " | " + itemToUpdate.getPrice());
        return itemRepo.save(itemToUpdate);
    }


    @Transactional
    public Item addStock (Long id, Integer amount) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be larger than 0");
        }

        item.setStock(item.getStock() + amount);
        item.setDateModified(item.getDateModified());

        System.out.println(amount + " units added to " + item.getName() + "\n" + " NEW STOCK AMOUNT: " + item.getStock());
        return itemRepo.save(item);
    }

    @Transactional
    public Item subtractStock (Long id, Integer amount) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(("Item not found")));

        if (amount <= 0) {
            throw new IllegalArgumentException("Quantity to deduct must be bigger than 0");
        }
        if (item.getStock() < amount) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        item.setStock(item.getStock() - amount);
        item.setDateModified(item.getDateModified());

        System.out.println(amount + " units subtracted to " + item.getName() + "\n" + " NEW STOCK AMOUNT: " + item.getStock());
        return itemRepo.save(item);
//        log.info(amount + " units subtracted to " + item.getName() + "\n" + " NEW STOCK AMOUNT: " + item.getStock());
    }

    @Transactional
    public Item sellItem (Long id, Integer amount) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Quantity must be higher than 0");
        }

        if (item.getStock() < amount) {
            throw new IllegalStateException("Insufficient stock");
        }

        item.setStock(item.getStock() - amount);
        item.setSold(item.getSold() + amount);
        item.setLastSaleDate(new Date());

        System.out.println(amount + " units subtracted to " + item.getName() + "\n" + " NEW STOCK AMOUNT: " + item.getStock());
        System.out.println(amount + " units sold of " + item.getName() + "\n" + " SOLD: " + item.getSold());
        return itemRepo.save(item);
    }

    private Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username);
    }

    public List<Item> findAllFiltered(boolean isDeleted){
        Session session = entityManager.unwrap(Session.class);

        Filter filter = session.enableFilter("deletedItemFilter");
        filter.setParameter("isDeleted", isDeleted);

        List<Item> items =  itemRepo.findAll();
        session.disableFilter("deletedItemFilter");

        return items;
    }

    public List<Item> getTopSellingItemsByUserIdAndDateRange(Long userId, Date startDate) {
        return itemRepo.findTopSellingItemsByUserIdAndDateRange(userId, startDate);
    }

    public List<Item> getItemsSoldByUserIdAndDateRange(Long userId, Date startDate) {
        return itemRepo.findItemsSoldByUserIdAndDateRange(userId, startDate);
    }

    public void deleteItem (Long id){
        Item item = itemRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));

        item.setUser(null);
        itemRepo.deleteById(id);

//        log.info(item.getName() + " deleted from inventory");
        System.out.println(item.getName() + " deleted from inventory");
    }
}
