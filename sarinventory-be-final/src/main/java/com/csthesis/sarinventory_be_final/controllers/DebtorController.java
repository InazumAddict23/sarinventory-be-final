package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.Dto.DebtDTO;
import com.csthesis.sarinventory_be_final.Dto.DebtorDTO;
import com.csthesis.sarinventory_be_final.entities.Debt;
import com.csthesis.sarinventory_be_final.entities.Debtor;
import com.csthesis.sarinventory_be_final.services.DebtorService;
import com.csthesis.sarinventory_be_final.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/api/debtors")
public class DebtorController {

    @Autowired
    public DebtorService debtorService;

    @Autowired
    public UserService userService;

    public DebtorController(DebtorService debtorService) {
        this.debtorService = debtorService;
    }

    //tested = working
    @PostMapping
    public ResponseEntity<Debtor> addDebtor(@RequestParam String name, Authentication auth) {
        return ResponseEntity.ok(debtorService.addDebtor(name, auth));
    }

    //tested = working
    @GetMapping
    public @ResponseBody List<Debtor> findAllById (Authentication auth) {
        return debtorService.findAllById(userService.loadUserByUsername(auth.getName()).getId());
    }

    //tested = working
    @GetMapping("/{id}")
    public ResponseEntity<DebtorDTO> getDebtor(@PathVariable Long id) {
        Debtor debtor = debtorService.getDebtor(id);
        DebtorDTO debtorDTO = new DebtorDTO(debtor.getId(), debtor.getName(), debtor.getTotal());
        return ResponseEntity.ok(debtorDTO);
    }

    //tested = working
    @PostMapping("/{id}/debts")
    public ResponseEntity<Debtor> addDebt(@PathVariable Long id, @RequestBody Debt debt) {
        return ResponseEntity.ok(debtorService.addDebt(id, debt));
    }

    //tested = working
    @DeleteMapping("/{debtorId}/debts/{debtId}")
    public ResponseEntity<DebtorDTO> removeDebt(
            @PathVariable Long debtorId,
            @PathVariable Long debtId,
            @RequestParam(defaultValue = "true") boolean softDelete) {
        Debtor updatedDebtor = debtorService.removeDebt(debtorId, debtId, softDelete);
        DebtorDTO debtorDTO = new DebtorDTO(updatedDebtor.getId(), updatedDebtor.getName(), updatedDebtor.getTotal());
        return ResponseEntity.ok(debtorDTO);
    }

    //tested = working
    @GetMapping("/{id}/debts")
    public ResponseEntity<List<Debt>> getActiveDebtsForDebtor(@PathVariable Long id) {
        List<Debt> debts = debtorService.getActiveDebtsForDebtor(id);
        return ResponseEntity.ok(debts);
    }

    @DeleteMapping ("/{id}")
    public void deleteDebtor(@RequestParam Long id) {
        debtorService.deleteDebtor(id);
    }
}
