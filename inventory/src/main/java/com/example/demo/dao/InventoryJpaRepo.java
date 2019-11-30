package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Item;

public interface InventoryJpaRepo extends JpaRepository<Item, Long>{

}
