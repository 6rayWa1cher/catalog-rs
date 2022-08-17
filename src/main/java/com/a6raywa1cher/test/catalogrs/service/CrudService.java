package com.a6raywa1cher.test.catalogrs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<D, I> {
    D getById(I id);

    Page<D> getPage(Pageable pageable);

    D create(D dto);

    D update(I id, D dto);

    D patch(I id, D dto);

    void delete(I id);
}
