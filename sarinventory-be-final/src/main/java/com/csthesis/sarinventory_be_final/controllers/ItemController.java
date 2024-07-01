package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.entities.Item;
import com.csthesis.sarinventory_be_final.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    public ItemService itemService;


    @PostMapping
    public Item createItem(@RequestBody Item item){
        return itemService.saveItem(item);
    };


    @GetMapping("/list")
    public ResponseEntity<List<Item>> findAll (@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        List<Item> items = itemService.findAllFiltered(isDeleted);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping("/topselling")
    public ResponseEntity<List<Item>> findTopSellingItems(@RequestParam(value = "limit", defaultValue = "5") int limit){
        List<Item> topSellingItems = itemService.findTopSellingItems(limit);
        return new ResponseEntity<>(topSellingItems, HttpStatus.OK);
    }

    @GetMapping
    public @ResponseBody List<Item> findAll() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.findItemById(id);
    }

    @PutMapping("/{id}")
    public Item updateItem (@PathVariable Long id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }

    @PutMapping("/addStock")
    public void addStock (@RequestParam Long id, @RequestParam int quantity) {
        itemService.addStock(id, quantity);
    }

    @PutMapping("/subtractStock")
    public void subtractStock (@RequestParam Long id, @RequestParam int quantity) {
        itemService.subtractStock(id, quantity);
    }

    @PutMapping("/updatePrice")
    public void updatePrice (@RequestParam Long id, @RequestParam double price) {
        itemService.updatePrice(id, price);
    }

    @DeleteMapping ("/{id}")
    public void deleteItem (@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
