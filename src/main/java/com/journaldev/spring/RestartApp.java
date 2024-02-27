package com.journaldev.spring;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RestartApp {
		
	public static void main(String[] args) {

		String[] springConfig = { "./spring/batch/jobs/job-batch-demo.xml" };

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

		// OGB: Restart execution.
		DataSource dataSource = (DataSource) context.getBean("dataSource");;
		JobOperator jobOperator = (JobOperator) context.getBean("jobOperator");
	    long executionId = 24; // OGB TODO: Fill in the execution id from the original execution job. Retrieve it from database.
	    Connection connection = null;

	    try {
	    	System.out.println("SUMMARY BEFORE RESTART:" + jobOperator.getSummary(executionId));
	    	
	    	connection = dataSource.getConnection();
	    	Statement statement = connection.createStatement();
	    	statement.addBatch("update batch_job_execution set status='STOPPED' where job_execution_id=" + executionId + ";");
	    	statement.addBatch("update batch_step_execution set status='STOPPED' where job_execution_id=" + executionId + ";");
	    	statement.executeBatch();
	    	
	        jobOperator.restart(executionId);
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
		System.out.println("Done");
		context.close();
	}
}
