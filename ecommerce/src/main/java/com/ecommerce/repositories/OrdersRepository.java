package com.ecommerce.repositories;

import com.ecommerce.domains.Orders;
import com.ecommerce.domains.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Transactional(readOnly = true)
    Page<Orders> findByUser(User user, Pageable pageable);
}
