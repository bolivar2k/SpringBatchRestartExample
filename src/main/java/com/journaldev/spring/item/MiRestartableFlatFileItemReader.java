package com.journaldev.spring.item;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;

public class MiRestartableFlatFileItemReader<T> extends FlatFileItemReader<T> implements ItemStream {

	private int currentCount;
	private String key = "file.in.registry.count";
	
	@Override
	protected T doRead() throws Exception {
		currentCount++;
		return super.doRead();
	}
	
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		try {
			currentCount = executionContext.getInt(key, 0); // OGB: Retrieves the read counter. If not found -> 0
			super.doOpen();
			super.setCurrentItemCount(currentCount);
			//super.setLinesToSkip(currentCount); // Other try to set the line to start, but this doesn't work.
			super.jumpToItem(currentCount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.putInt(key, currentCount);
		super.update(executionContext);
	}

	@Override
	public void close() throws ItemStreamException {
		try {
			super.doClose();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
