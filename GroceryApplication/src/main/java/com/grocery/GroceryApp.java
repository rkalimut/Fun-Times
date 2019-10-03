package com.grocery;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.util.Products;

public class GroceryApp {
	public SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	public int day;
	public Map<String, Integer> prodQuantityMap = new HashMap<>();
	public double amt = 0.0;
	Scanner sc = new Scanner(System.in);

	private void getInputData() {
		int quantity = 0;
		try {
			System.out.println(String.format("Please Enter the Product to Bucket(%s,%s,%s,%s) :",
					Products.SOUP.toString().toLowerCase(), Products.BREAD.toString().toLowerCase(),
					Products.MILK.toString().toLowerCase(), Products.APPLES.toString().toLowerCase()));
			String product = sc.nextLine();
			if (validateProduct(product)) {
				try {
					System.out.println("Enter Quantity for the product " + product + " :");
					quantity = Integer.parseInt(sc.nextLine());
				} catch (Exception e) {
					System.err.println("Please Enter Quantity in Number");
					System.exit(0);
				}
				System.out.println("Do you want to add other products to Bucket(Y/N)  :");
				String moreProduct = sc.nextLine();
				if ("Y".equalsIgnoreCase(moreProduct)) {
					storeQuantity(product, quantity);
					getInputData();
				} else {
					storeQuantity(product, quantity);
					System.out.println("Please enter the Number value for date(Today=1,Tomorrow=2,....)");
					day = Integer.parseInt(sc.nextLine());
				}
			} else {
				System.err.println(String.format("Please Enter Valid Products (%s,%s,%s,%s) :",
						Products.SOUP.toString().toLowerCase(), Products.BREAD.toString().toLowerCase(),
						Products.MILK.toString().toLowerCase(), Products.APPLES.toString().toLowerCase()));
				getInputData();
			}
		} catch (Exception ex) {
			System.err.println("Please Enter the valid input");
			System.exit(0);
		}
	}

	public void storeQuantity(String prod, int quantity) {
		Integer currentQty, totalQty;
		if (prodQuantityMap.containsKey(prod)) {
			currentQty = prodQuantityMap.get(prod);
			totalQty = currentQty + quantity;
			prodQuantityMap.put(prod, totalQty);
		} else {
			prodQuantityMap.put(prod, quantity);
		}
	}

	public void calculateCost() {
		if (prodQuantityMap.containsKey(Products.SOUP.toString().toLowerCase())) {
			int soupQty = prodQuantityMap.get(Products.SOUP.toString().toLowerCase()) != null
					? prodQuantityMap.get(Products.SOUP.toString().toLowerCase())
					: 0;
			this.amt += 0.65 * soupQty;
		}
		if (prodQuantityMap.containsKey(Products.BREAD.toString().toLowerCase())) {
			int breadQty = prodQuantityMap.get(Products.BREAD.toString().toLowerCase()) != null
					? prodQuantityMap.get(Products.BREAD.toString().toLowerCase())
					: 0;
			this.amt += 0.80 * breadQty;
		}
		if (prodQuantityMap.containsKey(Products.MILK.toString().toLowerCase())) {
			int milkQty = prodQuantityMap.get(Products.MILK.toString().toLowerCase()) != null
					? prodQuantityMap.get(Products.MILK.toString().toLowerCase())
					: 0;
			this.amt += 1.30 * milkQty;
		}
		if (prodQuantityMap.containsKey(Products.APPLES.toString().toLowerCase())) {
			int applesQty = prodQuantityMap.get(Products.APPLES.toString().toLowerCase()) != null
					? prodQuantityMap.get(Products.APPLES.toString().toLowerCase())
					: 0;
			this.amt += 0.10 * applesQty;
		}
		discountCalculate();
	}

	private void discountCalculate() {
		Date disStartDate, disEndDate, disStartDate1, disEndDate1;
		Date date = getOfferDate(day);
		if (prodQuantityMap.containsKey(Products.SOUP.toString().toLowerCase())
				&& prodQuantityMap.containsKey(Products.BREAD.toString().toLowerCase())) {
			int soupQty = prodQuantityMap.get(Products.SOUP.toString().toLowerCase()) != null
					? prodQuantityMap.get(Products.SOUP.toString().toLowerCase())
					: 0;
			if (soupQty >= 2) {
				disStartDate = getOfferDate(-1);
				disEndDate = getOfferDate(7);
				if (date.after(disStartDate) && date.before(disEndDate)) {
					this.amt -= 0.40;
				}
			}
		}
		if (prodQuantityMap.containsKey(Products.APPLES.toString().toLowerCase())) {
			int applesQty = prodQuantityMap.get(Products.APPLES.toString().toLowerCase()) != null
					? prodQuantityMap.get(Products.APPLES.toString().toLowerCase())
					: 0;
			disStartDate1 = getOfferDate(3);
			disEndDate1 = getMonthEndDate();
			if (date.after(disStartDate1) && date.before(disEndDate1)) {
				double discountVal = (applesQty * 0.10) * 10 / 100;
				this.amt -= discountVal;
			}
		}
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.println("Expected total cost=" + df.format(amt));
		System.out.println("------------------------------------------------------------------");

	}

	private static boolean validateProduct(String product) {
		boolean status = false;
		Field[] f = Products.class.getDeclaredFields();
		for (Field field : f) {
			if (product.equalsIgnoreCase(field.getName())) {
				status = true;
			}
		}
		return status;
	}

	public static void main(String[] args) {
		GroceryApp myApp = new GroceryApp();
		myApp.getInputData();
		myApp.calculateCost();
		myApp.continueOperation();
	}

	private void continueOperation() {
		System.out.println("Do you want to Continue (Y/N)?");
		String continueYN = sc.nextLine();
		if ("Y".equalsIgnoreCase(continueYN)) {
			day = 0;
			prodQuantityMap.clear();
			amt = 0.0;
			getInputData();
		} else {
			System.exit(0);
		}
	}

	public static Date getOfferDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date getMonthEndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

}
