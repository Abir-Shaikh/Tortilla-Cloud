package com.Tortilla_cloud.repository;

import com.Tortilla_cloud.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order , Long> {

    List<Order> findByZip(String zip);

    List<Order> findByCity(String city);

    List<Order> findByPlacedAtBetween(Date start, Date end);
}
