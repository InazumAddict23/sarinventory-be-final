package com.csthesis.sarinventory_be_final.services;

import com.csthesis.sarinventory_be_final.entities.Debt;
import com.csthesis.sarinventory_be_final.entities.Debtor;
import com.csthesis.sarinventory_be_final.entities.User;
import com.csthesis.sarinventory_be_final.repositories.DebtorRepository;
import com.csthesis.sarinventory_be_final.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DebtorService {

    @Autowired
    private DebtorRepository debtorRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public Debtor addDebtor(Debtor debtor, Authentication auth) {
        debtor.setUser((User) userRepo.findByUsername(auth.getName()).get());
        // Calculate total debt before saving

        debtor.calculateTotalDebt();
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
        Optional<Debtor> optionalDebtor = debtorRepo.findById(debtorId);
        if (optionalDebtor.isPresent()) {
            Debtor debtor = optionalDebtor.get();
            debtor.addDebt(debt);
            return debtorRepo.save(debtor);
        }
        return null;
    }

    @Transactional
    public Debtor removeDebt(Long debtorId, Long debtId) {
        Optional<Debtor> optionalDebtor = debtorRepo.findById(debtorId);
        if (optionalDebtor.isPresent()) {
            Debtor debtor = optionalDebtor.get();
            Optional<Debt> optionalDebt = debtor.getDebts().stream().filter(d -> d.getId().equals(debtId)).findFirst();
            if (optionalDebt.isPresent()) {
                Debt debt = optionalDebt.get();
                debtor.removeDebt(debt);
                debtorRepo.save(debtor);
            }
            return debtor;
        }
        return null;
    }
}
