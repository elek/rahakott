package net.anzix.rahakott.model;

import java.util.Date;

import junit.framework.Assert;
import net.anzix.rahakott.model.config.BookConfiguration;

import org.junit.Test;

public class BookTest {
	@Test
	public void move() {
		Book b = new Book();
		b.getOrCreateAccount("ACC1", "HUF");
		b.getOrCreateAccount("ACC2", "HUF");
		b.getOrCreateAccount("ACC3", "HUF");
		Transaction t = new Transaction(b.getAccount("ACC1"), b.getAccount("ACC2"), new Date(), "ads", 12, "HUF");
		b.addTransaction(t);
		
		Assert.assertEquals("ACC1", t.getFrom().getName());
		Assert.assertEquals("ACC2", t.getTo().getName());
		
		Assert.assertEquals(1, b.getAccount("ACC1").getTransactions().size());
		Assert.assertEquals(1, b.getAccount("ACC2").getTransactions().size());
		Assert.assertEquals(0, b.getAccount("ACC3").getTransactions().size());
		
		b.moveTransaction(t, "ACC2", "ACC3");
		
		Assert.assertEquals(1, b.getAccount("ACC1").getTransactions().size());
		Assert.assertEquals(0, b.getAccount("ACC2").getTransactions().size());
		Assert.assertEquals(1, b.getAccount("ACC3").getTransactions().size());
		
		Transaction tr = b.getAccount("ACC3").getTransactions().get(0);
		Assert.assertEquals("ACC1", tr.getFrom().getName());
		Assert.assertEquals("ACC3", tr.getTo().getName());
		

	}
}
