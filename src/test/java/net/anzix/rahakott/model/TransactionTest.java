package net.anzix.rahakott.model;

import java.util.Date;

import org.junit.Test;

public class TransactionTest {
	@Test
	public void test(){
		Transaction t = new Transaction(new Account("a", "a"), new Account("b", "b"), new Date(), "ads", 1/3d, "HUF");
		System.out.println(t.getKey());
	}
}
