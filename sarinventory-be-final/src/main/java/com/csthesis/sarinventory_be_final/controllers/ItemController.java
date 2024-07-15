package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.entities.ErrorResponse;
import com.csthesis.sarinventory_be_final.entities.Item;
import com.csthesis.sarinventory_be_final.entities.User;
import com.csthesis.sarinventory_be_final.repositories.UserRepository;
import com.csthesis.sarinventory_be_final.services.ItemService;
import com.csthesis.sarinventory_be_final.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/user/api/items")
public class ItemController {

    @Autowired
    public ItemService itemService;

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepository userRepo;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    //tested = working
    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody Item item, Authentication auth){
        try {
            item = itemService.saveItem(item, auth);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, e.getMessage()));
        }
    };


    //tested = failed
    @GetMapping("/list")
    public ResponseEntity<List<Item>> findAll(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        List<Item> items = itemService.findAllFiltered(isDeleted);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //tested = working
    @GetMapping("/topselling")
    public ResponseEntity<List<Item>> findTopSellingItems(Authentication auth, @RequestParam(value = "limit", defaultValue = "5") Integer limit){
        Long id = userService.loadUserByUsername(auth.getName()).getId();
        List<Item> topSellingItems = itemService.findTopSellingItems(id, limit);
        return new ResponseEntity<>(topSellingItems, HttpStatus.OK);
    }

    //tested = working
    @GetMapping
    public @ResponseBody List<Item> findAllById(Authentication auth) {
        return itemService.findAllById(userService.loadUserByUsername(auth.getName()).getId());
    }

    //tested = working
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.findItemById(id);
    }

    //tested = working
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem (@PathVariable Long id,
                                         @RequestParam (required = false) String name,
                                         @RequestParam (required = false) Integer price) {
        try {
            Item itemToUpdate = itemService.updateItem(id, name, price);
            return ResponseEntity.ok(itemToUpdate);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(500, "Error updating item: " + e.getMessage()));
        }
    }

    //tested = working
    @PutMapping("/addStock/{id}")
    public ResponseEntity<?> addStock (@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            Item itemToUpdate = itemService.addStock(id, quantity);
            return ResponseEntity.ok(itemToUpdate);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(400, e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(500, "Error updating stock: " + e.getMessage()));
        }
    }

    //tested = working
    @PutMapping("/subtractStock/{id}")
    public ResponseEntity<?> subtractStock (@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            Item itemToUpdate = itemService.subtractStock(id, quantity);
            return ResponseEntity.ok(itemToUpdate);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage()));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(500, "Error updating stock: " + e.getMessage()));
        }
    }

    //testing = working
    @PutMapping("/sellItem/{id}")
    public ResponseEntity<?> sellItem(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            Item itemToUpdate = itemService.sellItem(id, quantity);
            return ResponseEntity.ok(itemToUpdate);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage()));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(500, "Error updating stock: " + e.getMessage()));
        }
    }

    @GetMapping("/top-selling/{period}")
    public List<Item> getTopSellingItemsByUserIdAndPeriod(
            Authentication auth,
            @PathVariable String period) {
        Date startDate = getStartDateForPeriod(period);
        Long userId = userService.loadUserByUsername(auth.getName()).getId();

        return itemService.getTopSellingItemsByUserIdAndDateRange(userId, startDate);
    }

    @GetMapping("/sold/{period}")
    public List<Item> getItemsSoldByUserIdAndPeriod(
            Authentication auth,
            @PathVariable String period) {
        Date startDate = getStartDateForPeriod(period);
        Long id = userService.loadUserByUsername(auth.getName()).getId();
        return itemService.getItemsSoldByUserIdAndDateRange(id, startDate);
    }

    //testing = working
    @DeleteMapping ("/{id}")
    public void deleteItem (@PathVariable Long id) {
        itemService.deleteItem(id);
    }

    private Date getStartDateForPeriod(String period) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        switch (period.toLowerCase()) {
            case "daily":
                cal.add(Calendar.DAY_OF_MONTH, -1);
                break;
            case "weekly":
                cal.add(Calendar.WEEK_OF_YEAR, -1);
                break;
            case "monthly":
                cal.add(Calendar.MONTH, -1);
                break;
            default:
                throw new IllegalArgumentException("Invalid period. Use 'daily', 'weekly', or 'monthly'.");
        }

        return cal.getTime();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Item>> findAllIncludingDeleted(Authentication auth) {
        Long userId = userService.loadUserByUsername(auth.getName()).getId();
        List<Item> items = itemService.findAllByUserIdIncludingDeleted(userId);
        return ResponseEntity.ok(items);
    }


    @PutMapping("/undelete/{id}")
    public ResponseEntity<?> undeleteItem(@PathVariable Long id, Authentication auth) {
        try {
            Long userId = userService.loadUserByUsername(auth.getName()).getId();
            Item undeletedItem = itemService.undeleteItem(id, userId);
            return ResponseEntity.ok(undeletedItem);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(500, "Error undeleting item: " + e.getMessage()));
        }
    }
}
