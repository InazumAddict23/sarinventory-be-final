package com.csthesis.sarinventory_be_final.services;

import com.csthesis.sarinventory_be_final.entities.Debt;
import com.csthesis.sarinventory_be_final.entities.Debtor;
import com.csthesis.sarinventory_be_final.entities.User;
import com.csthesis.sarinventory_be_final.repositories.DebtorRepository;
import com.csthesis.sarinventory_be_final.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DebtorService {

    @Autowired
    private DebtorRepository debtorRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public Debtor addDebtor(String name, Authentication auth) {
        Debtor debtor = new Debtor();
        debtor.setName(name);
        debtor.setUser((User) userRepo.findByUsername(auth.getName()).get());

        return debtorRepo.save(debtor);
    }

    public List <Debtor> findAllById(Long id) {
        return debtorRepo.findAllByUserId(id);
    }

    public Debtor getDebtor(Long id) {
        return debtorRepo.findById(id).orElse(null);
    }

    @Transactional
    public Debtor addDebt(Long debtorId, Debt debt) {
        Debtor debtor = debtorRepo.findById(debtorId)
                .orElseThrow(() -> new RuntimeException("Debtor not found"));
        debt.setDate(new Date());
        debtor.addDebt(debt);
        return debtorRepo.save(debtor);
    }

    @Transactional
    public Debtor removeDebt(Long debtorId, Long debtId, boolean softDelete) {
        Debtor debtor = debtorRepo.findById(debtorId)
                .orElseThrow(() -> new RuntimeException("Debtor not found"));
        Debt debtToRemove = debtor.getDebts().stream()
                .filter(debt -> debt.getId().equals(debtId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Debt not found"));
        debtor.removeDebt(debtToRemove, softDelete);
        return debtorRepo.save(debtor);
    }

    public List<Debt> getActiveDebtsForDebtor(Long debtorId) {
        Debtor debtor = debtorRepo.findById(debtorId)
                .orElseThrow(() -> new RuntimeException("Debtor not found"));
        return debtor.getActiveDebts();
    }

    public void deleteDebtor (Long id) {
        Debtor debtor = debtorRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Debtor not found"));

        if (debtor.getTotal() > 0) {
            throw new IllegalArgumentException("You cannot delete someone with debt");
        }

        debtor.setUser(null);
        debtorRepo.deleteById(id);

        System.out.println(debtor.getName() + " deleted from inventory");
    }
}
