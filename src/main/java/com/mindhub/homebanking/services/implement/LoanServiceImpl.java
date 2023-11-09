package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<Loan> findAllLoans() {

        return loanRepository.findAll() ;

    }
    @Override
    public Loan findLoanById(Long id) {

        return loanRepository.findById(id).orElse(null);

    }

    @Override
    public boolean existsByName(String name) {
        return loanRepository.existsByName(name);
    }

    @Override
    public void saveLoan(Loan loan) {

        loanRepository.save(loan);

    }
}
