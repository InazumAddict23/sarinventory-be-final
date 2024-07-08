package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.entities.ErrorResponse;
import com.csthesis.sarinventory_be_final.entities.Item;
import com.csthesis.sarinventory_be_final.entities.User;
import com.csthesis.sarinventory_be_final.services.ItemService;
import com.csthesis.sarinventory_be_final.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user/api/items")
public class ItemController {

    @Autowired
    public ItemService itemService;

    @Autowired
    public UserService userService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    //tested = working
    @PostMapping
    public Item createItem(@RequestBody Item item, Authentication auth){
        return itemService.saveItem(item, auth);
    };


    //tested = failed
    @GetMapping("/list")
    public ResponseEntity<List<Item>> findAll(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        List<Item> items = itemService.findAllFiltered(isDeleted);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //tested = working
    @GetMapping("/topselling")
    public ResponseEntity<List<Item>> findTopSellingItems(@RequestParam(value = "limit", defaultValue = "5") Integer limit){
        List<Item> topSellingItems = itemService.findTopSellingItems(limit);
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

    //testing = working
    @DeleteMapping ("/{id}")
    public void deleteItem (@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
