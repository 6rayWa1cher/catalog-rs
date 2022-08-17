package com.a6raywa1cher.test.catalogrs.dao.repo;

import com.a6raywa1cher.test.catalogrs.dao.Product;
import com.a6raywa1cher.test.catalogrs.dto.ProductQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> getFilteredPage(ProductQueryDto queryDto, Pageable pageable);
}
