package com.Tortilla_cloud.repository;

import com.Tortilla_cloud.model.Tortilla;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TortillaRepository extends CrudRepository<Tortilla , Long> {

    List<Tortilla> findByCreatedAtAfter(Date date);
    Page<Tortilla> findAll(Pageable pageable);
}
