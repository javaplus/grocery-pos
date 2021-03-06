package com.barry.grocerypos.entities;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class ItemTest {
	
	@Test
	public void itemHasAPrice() {
		
		Item item = new Item();
		BigDecimal price = new BigDecimal(10.00);
		item.setPrice(price);
		
		assertEquals(price, item.getPrice());
	}
	
	@Test
	public void itemHasAWeight() {
		Item item = new Item();
		item.setWeight(2);
		
		
		assertEquals(2, item.getWeight());
		
	}

	
	@Test
	public void itemTotalPriceIsWeightTimesPrice() {
		Item item = new Item();
		item.setWeight(2);
		item.setPrice(new BigDecimal(5.00));
		
		assertEquals(new BigDecimal(10.00), item.totalPrice());
		
	}
	
	
	@Test
	public void itemTotalPriceIsSameAsPriceIfNoWeightIsSet() {
		Item item = new Item();
		item.setPrice(new BigDecimal(5.00));
		
		assertEquals(new BigDecimal(5.00), item.totalPrice());
		
	}
	
	@Test
	public void itemCanHaveName() {
		
		Item item = new Item();
		item.setName("Bacon");
		
		assertEquals("Bacon", item.getName());
		
		
	}
	
	@Test 
	public void whencreatingItemWithConstructorCanPassNameAndPriceAsDouble() {
		
		Item item = new Item("Bacon", 2.50);
		
		assertEquals("Bacon", item.getName());
		assertThat(new BigDecimal(2.50), Matchers.comparesEqualTo(item.getPrice()));
		
	}
	
	
	@Test 
	public void whencreatingItemWithConstructorWithNameAndPriceWeightIsDefaultedToOne() {
		
		Item item = new Item("Bacon", 2.50);
		
		assertEquals("Bacon", item.getName());
		assertEquals(1, item.getWeight());
		
	}
	
}
