package com.Tortilla_cloud.backend.repository;

import com.Tortilla_cloud.backend.model.Order;
import com.Tortilla_cloud.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order , Long> , PagingAndSortingRepository<Order , Long> {

    Page<Order> findByUser(User user, Pageable pageable);

    List<Order> findByZip(String zip);

    List<Order> findByCity(String city);

}
