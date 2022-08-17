package com.a6raywa1cher.test.catalogrs.dao.repo;

import com.a6raywa1cher.test.catalogrs.dao.Product;
import com.a6raywa1cher.test.catalogrs.dto.ProductQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepositoryImpl {
    @Autowired
    private ProductRepository repository;

    @SuppressWarnings("unused")
    public Page<Product> getFilteredPage(ProductQueryDto queryDto, Pageable pageable) {
        return repository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String title = queryDto.getTitle();
            if (title != null) {
                String pattern = "%" + title + "%";
                predicates.add(builder.like(builder.lower(root.get("title")), pattern));
            }
            Long category = queryDto.getCategory();
            if (category != null) {
                predicates.add(builder.equal(root.get("criteria").get("id"), category));
            }
            BigDecimal fromPriceAmount = queryDto.getFromPriceAmount();
            if (fromPriceAmount != null) {
                predicates.add(builder.ge(root.get("priceAmount"), fromPriceAmount));
            }
            BigDecimal toPriceAmount = queryDto.getToPriceAmount();
            if (toPriceAmount != null) {
                predicates.add(builder.le(root.get("priceAmount"), toPriceAmount));
            }
            return builder.and(predicates.toArray(new Predicate[]{}));
        }, pageable);
    }
}
