package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.entities.Supplier;
import com.csthesis.sarinventory_be_final.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    public SupplierService supplierService;

    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier){
        return supplierService.saveSupplier(supplier);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Supplier>> findAll (@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        List<Supplier> suppliers = supplierService.findAllFiltered(isDeleted);
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }
    @GetMapping
    public List <Supplier> findAll(){
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    public Supplier getSupplierById(@PathVariable Long id) {
        return supplierService.findSupplierById(id);
    }

    @PutMapping
    public Supplier updateSupplier (@PathVariable Long id, @RequestBody Supplier supplier) {
        return supplierService.updateSupplier(id, supplier);
    }

    @DeleteMapping ("/{id}")
    public void deleteSupplier (@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }
}
