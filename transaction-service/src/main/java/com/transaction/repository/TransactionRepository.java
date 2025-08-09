package com.transaction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Integer>{
	Page<Transaction> findBySenderIdOrReceiverIdOrderByTimestampDesc(
            Integer senderId, Integer receiverId, Pageable pageable
    );
}
