package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.entities.Debt;
import com.csthesis.sarinventory_be_final.entities.Debtor;
import com.csthesis.sarinventory_be_final.services.DebtorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debtors")
public class DebtorController {

    @Autowired
    private DebtorService debtorService;

    @PostMapping
    public ResponseEntity<Debtor> addDebtor(@RequestBody Debtor debtor) {
        Debtor newDebtor = debtorService.addDebtor(debtor);
        return new ResponseEntity<>(newDebtor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Debtor> getDebtor(@PathVariable Long id) {
        Debtor debtor = debtorService.getDebtor(id);
        return debtor != null ? ResponseEntity.ok(debtor) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{debtorId}/debts")
    public ResponseEntity<Debtor> addDebt(@PathVariable Long debtorId, @RequestBody Debt debt) {
        Debtor updatedDebtor = debtorService.addDebt(debtorId, debt);
        return updatedDebtor != null ? ResponseEntity.ok(updatedDebtor) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{debtorId}/debts/{debtId}")
    public ResponseEntity<Debtor> removeDebt(@PathVariable Long debtorId, @PathVariable Long debtId) {
        Debtor updatedDebtor = debtorService.removeDebt(debtorId, debtId);
        return updatedDebtor != null ? ResponseEntity.ok(updatedDebtor) : ResponseEntity.notFound().build();
    }
}
