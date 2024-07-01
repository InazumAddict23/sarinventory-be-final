package com.csthesis.sarinventory_be_final.services;

import com.csthesis.sarinventory_be_final.entities.Debt;
import com.csthesis.sarinventory_be_final.entities.Debtor;
import com.csthesis.sarinventory_be_final.repositories.DebtorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DebtorService {

    @Autowired
    private DebtorRepository debtorRepository;

    @Transactional
    public Debtor addDebtor(Debtor debtor) {
        // Calculate total debt before saving
        debtor.calculateTotalDebt();
        return debtorRepository.save(debtor);
    }

    public Debtor getDebtor(Long id) {
        return debtorRepository.findById(id).orElse(null);
    }

    @Transactional
    public Debtor addDebt(Long debtorId, Debt debt) {
        Optional<Debtor> optionalDebtor = debtorRepository.findById(debtorId);
        if (optionalDebtor.isPresent()) {
            Debtor debtor = optionalDebtor.get();
            debtor.addDebt(debt);
            return debtorRepository.save(debtor);
        }
        return null;
    }

    @Transactional
    public Debtor removeDebt(Long debtorId, Long debtId) {
        Optional<Debtor> optionalDebtor = debtorRepository.findById(debtorId);
        if (optionalDebtor.isPresent()) {
            Debtor debtor = optionalDebtor.get();
            Optional<Debt> optionalDebt = debtor.getDebts().stream().filter(d -> d.getId().equals(debtId)).findFirst();
            if (optionalDebt.isPresent()) {
                Debt debt = optionalDebt.get();
                debtor.removeDebt(debt);
                debtorRepository.save(debtor);
            }
            return debtor;
        }
        return null;
    }
}
