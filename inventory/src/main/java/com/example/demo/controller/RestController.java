package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.example.demo.dao.InventoryJpaRepo;
import com.example.demo.exceptions.ForbiddenOperationException;
import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.model.Item;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Validated
@Api(value = "Inventory Management System")
@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	InventoryJpaRepo repo;


	// Add item to stock
	@ApiOperation(value = "Add item to stock")
	@PostMapping(path="/item")
	public Item addItem(@Valid @RequestBody Item item) {
		return repo.save(item);
		 
	}

	// List of the inventory items list
	@ApiOperation(value = "View a list of inventory items", response = List.class)
	@GetMapping(path="/items",produces = "application/json" )
	public List<Item> getAllItems() {
		return repo.findAll();
	}

	// Read item details (by item no)
	@ApiOperation(value = "View item details", response = Item.class)
	@GetMapping(path="/item/{itemNo}",produces = "application/json")
	public Item getItem(@PathVariable("itemNo") long itemNo) {
		return repo.findById(itemNo).orElseThrow(() -> new ItemNotFoundException(itemNo));
	}

	// Delete an item from stock
	@ApiOperation(value = "Delete an item from stock")
	@DeleteMapping("/item/{itemNo}")
	public String deleteItem(@PathVariable("itemNo") long itemNo) {
		repo.findById(itemNo).orElseThrow(() -> new ItemNotFoundException(itemNo));
		repo.deleteById(itemNo);
		return "deleted";
	}

  
	  @ApiOperation(value = "Withdrawal quantity of a specific item from stock")
	  @PostMapping("/item/withdrawal/{itemNo}") 
	  public String withdrawalAmountFromItem(@Valid @RequestBody Item item,@PathVariable("itemNo") long itemNo) throws ForbiddenOperationException {
		  Item stockItem = repo.findById(itemNo).orElseThrow(()->new ItemNotFoundException(itemNo)); 
		  if(stockItem.getAmount()<item.getAmount()){ 
			  throw new ForbiddenOperationException("Not enough items in stock"); 
		  } 
		  else { 
			  stockItem = repo.findById(itemNo).get();
			  stockItem.setAmount(stockItem.getAmount()-item.getAmount());
			  repo.save(stockItem);
			  return "operation succeeded"; 
		  }
	  }
	  
	  //Deposit quantity of a specific item to stock
	  @ApiOperation(value = "Deposit quantity of a specific item to stock")
	  @PostMapping("item/deposit/{itemNo}")
	  public String depositAmountToItem(@Valid @RequestBody Item item, @PathVariable("itemNo") long itemNo) throws ForbiddenOperationException
	  { 
 		  if(item.getAmount()<=0){ 
			  throw new ForbiddenOperationException("amount must be greater than zero"); 
		  } 
		  else {
			  Item stockItem = repo.findById(itemNo).orElseThrow(()->new ItemNotFoundException(itemNo));
			  stockItem.setAmount(stockItem.getAmount()+item.getAmount());
			  repo.save(stockItem);
			  return "operation succeeded"; 
		  }
  }
}
