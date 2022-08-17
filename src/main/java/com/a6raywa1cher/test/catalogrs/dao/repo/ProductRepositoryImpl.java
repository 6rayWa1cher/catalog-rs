package com.a6raywa1cher.test.catalogrs.dao.repo;

import com.a6raywa1cher.test.catalogrs.dao.Product;
import com.a6raywa1cher.test.catalogrs.dao.Product_;
import com.a6raywa1cher.test.catalogrs.dto.ProductQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ProductRepositoryImpl {
    private final ProductRepository repository;

    @Autowired
    public ProductRepositoryImpl(@Lazy ProductRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unused")
    public Page<Product> getFilteredPage(ProductQueryDto queryDto, Pageable pageable) {
        return repository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String title = queryDto.getTitle();
            if (title != null) {
                String pattern = "%" + title.toLowerCase(Locale.ROOT) + "%";
                predicates.add(builder.like(builder.lower(root.get(Product_.title)), pattern));
            }
            Long category = queryDto.getCategory();
            if (category != null) {
                predicates.add(builder.equal(root.get(Product_.category), category));
            }
            BigDecimal fromPriceAmount = queryDto.getFromPriceAmount();
            if (fromPriceAmount != null) {
                predicates.add(builder.ge(root.get(Product_.priceAmount), fromPriceAmount));
            }
            BigDecimal toPriceAmount = queryDto.getToPriceAmount();
            if (toPriceAmount != null) {
                predicates.add(builder.le(root.get(Product_.priceAmount), toPriceAmount));
            }
            return builder.and(predicates.toArray(new Predicate[]{}));
        }, pageable);
    }
}
