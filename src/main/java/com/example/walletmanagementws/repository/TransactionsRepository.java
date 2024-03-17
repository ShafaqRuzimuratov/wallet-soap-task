package com.example.walletmanagementws.repository;

import com.example.walletmanagementws.entity.Transaction;
import com.example.walletmanagementws.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
}
