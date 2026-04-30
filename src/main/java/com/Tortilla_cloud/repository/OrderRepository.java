package com.Tortilla_cloud.repository;

import com.Tortilla_cloud.model.Order;
import com.Tortilla_cloud.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order , Long> , PagingAndSortingRepository<Order , Long> {

    List<Order> findByUser(User user, Pageable pageable);

    List<Order> findByZip(String zip);

    List<Order> findByCity(String city);

}
