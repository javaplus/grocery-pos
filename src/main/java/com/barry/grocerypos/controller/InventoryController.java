package com.barry.grocerypos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	
	@RequestMapping(value = "/items", method = POST)
	public void addItems() {
		
	}
	
}
