package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.entities.ErrorResponse;
import com.csthesis.sarinventory_be_final.entities.Supplier;
import com.csthesis.sarinventory_be_final.services.SupplierService;
import com.csthesis.sarinventory_be_final.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/api/suppliers")
public class SupplierController {

    @Autowired
    public SupplierService supplierService;

    @Autowired
    public UserService userService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // tested = working
    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier, Authentication auth){
        return supplierService.saveSupplier(supplier, auth);
    }

    // tested = failed
    @GetMapping("/list")
    public ResponseEntity<List<Supplier>> findAll (@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        List<Supplier> suppliers = supplierService.findAllFiltered(isDeleted);
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    //tested = working
    @GetMapping
    public List <Supplier> findAllById(Authentication auth){
        return supplierService.findAllById(userService.loadUserByUsername(auth.getName()).getId());
    }

    //tested = working
    @GetMapping("/{id}")
    public Supplier getSupplierById(@PathVariable Long id) {
        return supplierService.findSupplierById(id);
    }

    //tested = working
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier (@PathVariable Long id,
                                             @RequestParam (required = false) String name,
                                             @RequestParam (required = false) String phoneNo) {
        try {
            Supplier supplierToUpdate = supplierService.updateSupplier(id, name, phoneNo);
            return ResponseEntity.ok(supplierToUpdate);
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
    @DeleteMapping ("/{id}")
    public void deleteSupplier (@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }
}
