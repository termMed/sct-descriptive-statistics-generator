/**
 * Copyright (c) 2016 TermMed SA
 * Organization
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

/**
 * Author: Alejandro Rodriguez
 */
package com.termmed.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


// TODO: Auto-generated Javadoc
/**
 * The Class DeltaGenerator.
 */
public class DeltaGenerator {
	

	/** The sorted file. */
	private File sortedFile;
	
	/** The initial date. */
	private String initialDate;
	
	/** The final date. */
	private String finalDate;
	
	/** The column id. */
	private int[] columnId;
	
	/** The effective time column. */
	private int effectiveTimeColumn;
	
	/** The output file. */
	private File outputFile;

	/**
	 * Instantiates a new delta generator.
	 *
	 * @param sortedFile the sorted file
	 * @param initialDate the initial date
	 * @param finalDate the final date
	 * @param columnId the column id
	 * @param effectiveTimeColumn the effective time column
	 * @param outputFile the output file
	 */
	public DeltaGenerator(File sortedFile, String initialDate,
			String finalDate, int[] columnId, int effectiveTimeColumn,
			File outputFile) {
		super();
		this.sortedFile = sortedFile;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
		this.columnId = columnId;
		this.effectiveTimeColumn = effectiveTimeColumn;
		this.outputFile = outputFile;
	}

	/**
	 * Execute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void execute() throws IOException{

			Thread currentThread = Thread.currentThread();
			long start1 = System.currentTimeMillis();

			FileInputStream fis = new FileInputStream(sortedFile);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			BufferedReader br = new BufferedReader(isr);

			double lines = 0;
			String nextLine;
			String header = br.readLine();

			if (outputFile.exists()){
				outputFile.delete();
			}
			FileOutputStream fos = new FileOutputStream( outputFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);

			bw.append(header);
			bw.append("\r\n");

			String[] prevCompoId=new String[columnId.length];
			String prevLine="";
			boolean newId;
			String[] splittedLine;
			String[] prevSplittedLine;
			nextLine = br.readLine();
			
			if ( nextLine  != null){
				prevSplittedLine = nextLine.split("\t",-1);

				if (prevSplittedLine[effectiveTimeColumn].compareTo(finalDate)<=0
						&& prevSplittedLine[effectiveTimeColumn].compareTo(initialDate)>0 ){
					prevLine=nextLine;

				}
				for (int i=0;i<columnId.length;i++){
					prevCompoId[i]=prevSplittedLine[columnId[i]];
				}
				while ((nextLine= br.readLine()) != null) {
					if(currentThread.isInterrupted()){
						break;
					}
					splittedLine = nextLine.split("\t",-1);
					newId=false;
					for (int i=0;i<columnId.length;i++){
						if(!splittedLine[columnId[i]].equals(prevCompoId[i])){
							newId=true;
							break;
						}
					}
					if (!newId){
						if (splittedLine[effectiveTimeColumn].compareTo(finalDate)<=0
								&& splittedLine[effectiveTimeColumn].compareTo(initialDate)>0 ){
							prevLine=nextLine;

						}
					}else{
						if (prevLine.compareTo("")!=0){

							bw.append(prevLine);
							bw.append("\r\n");
							lines++;
						}

						if (splittedLine[effectiveTimeColumn].compareTo(finalDate)<=0
								&& splittedLine[effectiveTimeColumn].compareTo(initialDate)>0 ){
							prevLine=nextLine;

						}else{
							prevLine="";
						}
						for (int i=0;i<columnId.length;i++){
							prevCompoId[i]=splittedLine[columnId[i]];
						}
					}
				}
				if (prevLine.compareTo("")!=0){

					bw.append(prevLine);
					bw.append("\r\n");
					lines++;			
				}
			}

			bw.close();
			br.close();
			long end1 = System.currentTimeMillis();
			long elapsed1 = (end1 - start1);
			System.out.println("Lines in output file  : " + lines);
			System.out.println("Completed in " + elapsed1 + " ms");
	}
}
