package com.journaldev.spring.item;

import org.springframework.batch.item.ItemProcessor;

import com.journaldev.spring.model.Report;

public class CustomItemProcessor implements ItemProcessor<Report, Report> {

	public Report process(Report item) throws Exception {
		
		System.out.println("Processing..." + item);
		String fname = item.getFirstName();
		String lname = item.getLastName();
		
		item.setFirstName(fname.toUpperCase());
		item.setLastName(lname.toUpperCase());
		
		Thread.sleep(1000); // OGB: Intentional delay in order to stop the test in the middle of a chunk processing.
		
		return item;
	}

}