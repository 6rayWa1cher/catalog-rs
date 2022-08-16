package com.a6raywa1cher.test.catalogrs.dao.repo;

import com.a6raywa1cher.test.catalogrs.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
