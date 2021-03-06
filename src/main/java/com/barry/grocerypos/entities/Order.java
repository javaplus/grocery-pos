package com.barry.grocerypos.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class Order {
	
	@Autowired
	@Setter
	private Inventory inventory;
	
	@Getter
	public List<Item> itemList = new ArrayList<>();
	
	private Map<String, Special> specialsMap = new HashMap<>();
	private Map<String, MarkDown> markDownMap = new HashMap<>();
	
	private Map<String, PercentOffSpecial> percentOffSpecialMap = new HashMap<>();
	

	public void addItem(Item newItem) {
		// make a copy of the item
		Item copyOfItem = new Item(newItem.getName(), newItem.getPrice().setScale(2, RoundingMode.HALF_UP).doubleValue());
		copyOfItem.setWeight(newItem.getWeight());
		itemList.add(copyOfItem);
	}
	
	public int getSize() {
		return itemList.size();
	}
	
	public BigDecimal total() {
		
		// reset price of items to individual prices before applying specials
		for (Item item : itemList) {
			Item inventoryItem = inventory.getItemByName(item.getName());
			// in case its not in inventory accept the price override
			BigDecimal price = (inventoryItem!=null)? inventoryItem.getPrice(): item.getPrice();
			item.setPrice(price);
		}

		applyMarkDowns();
		
		applyPercentOffSpecials();
		
		applySpecials();
		
		
		
		BigDecimal total = itemList.stream()
				.map(Item::totalPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return total.setScale(2, RoundingMode.HALF_UP);
	}
	
	public void applyMarkDowns() {
		for (Item item : itemList) {
			MarkDown markDown = getMarkDownByName(item.getName());
			if(markDown!=null) {
				item.setPrice(item.getPrice().subtract(markDown.getPriceReduction()));
			}
		}
		
	}

	public void applySpecials() {
		
		// if have item with special,then update prices with unit prices
		for (String itemName: specialsMap.keySet()) {
			if(itemList.stream().anyMatch(item->item.getName().equals(itemName))) {
				//get special
				Special special = specialsMap.get(itemName);
				BigDecimal unitPrice = special.getUnitPrice();
				if(getCountOfItem(itemName)>=special.getQuantityRequired()) {
					// set price to special price for number of items
					itemList.stream().filter(item->item.getName().equals(itemName))
						.limit(special.getQuantityRequired())
						.forEach(specialItem -> specialItem.setPrice(unitPrice));
				}
			}
		}
	}
	
	public void removeItem(String itemName) {
		
		this.itemList = itemList.stream().filter(p-> p.getName().equalsIgnoreCase(itemName)==false).collect(Collectors.toList());
		
	}

	public void addSpecial(Special special) {
		specialsMap.put(special.getItemName(), special);
		
	}
	
	public Special getSpecialByName(String itemName) {
	
		return specialsMap.get(itemName);
		
	}

	public int getCountOfItem(String itemName) {

		return itemList.stream().filter(item-> item.getName().equals(itemName)).collect(Collectors.counting()).intValue();
	}

	public void addMarkDown(MarkDown markDown) {
		markDownMap.put(markDown.getItemName(), markDown);
		
	}

	public MarkDown getMarkDownByName(String itemName) {
		
		return markDownMap.get(itemName);
	}
	
	public void addPercentOffSpecial(PercentOffSpecial percentOffSpecial) {
		percentOffSpecialMap.put(percentOffSpecial.getItemName(), percentOffSpecial);
	}
	

	public PercentOffSpecial getPercentOffSpecialByName(String itemName) {
		return percentOffSpecialMap.get(itemName);
	}

	public void applyPercentOffSpecials() {
		
		percentOffSpecialMap.entrySet().forEach(special->special.getValue().applySpecial(itemList));
		
	}

	public void clearOrder() {
		itemList.clear();
		
	}

	
}
