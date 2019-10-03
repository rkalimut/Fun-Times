package test.grocery;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.grocery.GroceryApp;
import org.junit.Test;
import static org.junit.Assert.*;

public class GroceryAppTest {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	DecimalFormat df = new DecimalFormat("0.00");

	@Test
	public void testCase1() {
		GroceryApp myApp = new GroceryApp();
		String prod[] = { "soup", "bread" };
		int quantity[] = { 3, 2 };
		for (int i = 0; i < prod.length; i++) {
			myApp.storeQuantity(prod[i], quantity[i]);
			System.out.println(prod[i] + " Quantity is :" + quantity[i]);
		}
		myApp.day = 1;
		myApp.calculateCost();
		
		assertEquals(df.format(3.15), df.format(myApp.amt));
	}

	@Test
	public void testCase2() {
		GroceryApp myApp = new GroceryApp();
		String prod[] = { "apples", "milk" };
		int quantity[] = { 6, 1 };
		for (int i = 0; i < prod.length; i++) {
			myApp.storeQuantity(prod[i], quantity[i]);
			System.out.println(prod[i] + " Quantity is :" + quantity[i]);
		}
		myApp.day = 1;
		myApp.calculateCost();
		assertEquals(df.format(1.90), df.format(myApp.amt));
	}

	@Test
	public void testCase3() {
		GroceryApp myApp = new GroceryApp();
		String prod[] = { "apples", "milk" };
		int quantity[] = { 6,1 };
		for (int i = 0; i < prod.length; i++) {
			myApp.storeQuantity(prod[i], quantity[i]);
			System.out.println(prod[i] + " Quantity is :" + quantity[i]);
		}
		myApp.day = 5;
		myApp.calculateCost();
		assertEquals(df.format(1.84), df.format(myApp.amt));
	}

	@Test
	public void testCase4() {
		GroceryApp myApp = new GroceryApp();
		String prod[] = { "apples", "soup", "bread" };
		int quantity[] = { 3, 2, 1 };
		for (int i = 0; i < prod.length; i++) {
			myApp.storeQuantity(prod[i], quantity[i]);
			System.out.println(prod[i] + " Quantity is :" + quantity[i]);
		}
		myApp.day = 5;
		myApp.calculateCost();
		assertEquals(df.format(1.97), df.format(myApp.amt));
	}
}
