package com.barry.grocerypos.entities;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {

	private Order order;
	
	@Before
	public void initializeOrder() {
		order = new Order();
	}
	
	
	@Test
	
	public void addingItemIncreasesSizeByOne() throws Exception{
		
		Item item = new Item();
		
		order.addItem(item);
		
		assertEquals(1, order.getSize());
		
	}
	
	@Test
	public void addingTwoItemsIncreasesSizeByTwo() {
		
		order.addItem(new Item());
		order.addItem(new Item());
		
		assertEquals(2, order.getSize());
	
		
	}
	
	@Test
	public void orderTotalIsZeroWithNoItems() {
		
		
		assertEquals(new BigDecimal(0.00), order.total());
		
	}
	
	@Test
	public void orderTotalIsSameAsPriceWhenOnlyOneItemAdded() {
		
		Item item = new Item();
		item.setPrice(new BigDecimal(10.00));
		
		order.addItem(item);
		
		assertEquals(new BigDecimal(10.00), order.total());
		
	}
	
	
	@Test
	public void orderTotalIsSumOfPricesWhenTwoItemsInOrder() {
		
		Item item1 = new Item();
		item1.setPrice(new BigDecimal(5.00));
		
		Item item2 = new Item();
		item2.setPrice(new BigDecimal(2.00));
		
		order.addItem(item1);
		order.addItem(item2);

		
		assertEquals(new BigDecimal(7.00), order.total());
	}
	
	
	

}
