package net.anzix.rahakott.model.output;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Transaction;

public class AccountGenerator extends PageGenerator<Account> {
	SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");
	
	@Override
	protected Map<String, Object> populateVariables(Account input) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("root", input);
		
		Map<String,List<Transaction>> transactionsByMonth = new TreeMap<String,List<Transaction>>();
		for (Transaction t : input.getTransactions()){
			String key = format.format(t.getDate());
			List<Transaction> monthly = transactionsByMonth.get(key);
			if (monthly == null){
				transactionsByMonth.put(key, new ArrayList<Transaction>());
				monthly = transactionsByMonth.get(key);
			}
			monthly.add(t);										
		}
		values.put("monthly", transactionsByMonth);
		
		return values;
	}




}
