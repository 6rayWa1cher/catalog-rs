package com.a6raywa1cher.test.catalogrs.dao.repo;

import com.a6raywa1cher.test.catalogrs.dao.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    boolean existsByTitle(String title);

    Optional<ProductCategory> findByTitle(String title);
}
