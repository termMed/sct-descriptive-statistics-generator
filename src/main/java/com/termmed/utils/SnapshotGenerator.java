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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class SnapshotGenerator.
 */
public class SnapshotGenerator {

	/** The sorted file. */
	private File sortedFile;
	
	/** The column filter ixs. */
	private Integer[] columnFilterIxs;
	
	/** The column filter values. */
	private String[] columnFilterValues;
	
	/** The date. */
	private String date;
	
	/** The component column. */
	private int componentColumn;
	
	/** The effective time column. */
	private int effectiveTimeColumn;
	
	/** The output file. */
	private File outputFile;
	
	/** The output fields. */
	private String[] outputFields;
	
	/** The output columns. */
	private int[] outputColumns;

	/**
	 * Instantiates a new snapshot generator.
	 *
	 * @param sortedFile the sorted file
	 * @param date the date
	 * @param componentColumn the component column
	 * @param effectiveTimeColumn the effective time column
	 * @param outputFile the output file
	 * @param columnFilterIxs the column filter ixs
	 * @param columnFilterValues the column filter values
	 * @param outputFields the output fields
	 */
	public SnapshotGenerator(File sortedFile, String date,
			int componentColumn, int effectiveTimeColumn, File outputFile,
			Integer[] columnFilterIxs,String[] columnFilterValues,String[] outputFields) {
		super();
		this.sortedFile = sortedFile;
		this.date = date;
		this.componentColumn = componentColumn;
		this.effectiveTimeColumn = effectiveTimeColumn;
		this.outputFile = outputFile;
		this.columnFilterIxs=columnFilterIxs;
		this.columnFilterValues=columnFilterValues;
		this.outputFields=outputFields;
	}

	/**
	 * Execute.
	 */
	public void execute(){
		
		try {
			long start1 = System.currentTimeMillis();

			FileInputStream fis = new FileInputStream(sortedFile);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			BufferedReader br = new BufferedReader(isr);

			double lines = 0;
			String nextLine;
			String header = br.readLine();
			getMapColumns(header);
			if (outputFile.exists()){
				outputFile.delete();
			}
			FileOutputStream fos = new FileOutputStream( outputFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			
			writeHeader(bw,header);
			
//			bw.append(header);
//			bw.append("\r\n");
			
			String prevCompo="";
			String prevLine="";
			String[] splittedLine;
			String[] prevSplittedLine;
			boolean bContinue=true;

			while ((prevLine= br.readLine()) != null) {
				prevSplittedLine =prevLine.split("\t",-1);
				
				if (columnFilterIxs!=null){
					bContinue = true;
					for (int i=0;i<columnFilterIxs.length;i++){
						if (prevSplittedLine[columnFilterIxs[i]].compareTo(columnFilterValues[i])!=0){
							bContinue=false;
							break;
						}
					}
				}
				if (bContinue){
					if (prevSplittedLine[effectiveTimeColumn].compareTo(date)<=0){
						prevCompo=prevSplittedLine[componentColumn];
						break;
					}
				}
			}
			if ( !prevCompo.equals("") ){
				while ((nextLine= br.readLine()) != null) {
					splittedLine = nextLine.split("\t",-1);
					
					if (columnFilterIxs!=null){
						bContinue = true;
						for (int i=0;i<columnFilterIxs.length;i++){
							if (splittedLine[columnFilterIxs[i]].compareTo(columnFilterValues[i])!=0){
								bContinue=false;
								break;
							}
						}
					}
					if (bContinue){
						if(splittedLine[componentColumn].equals(prevCompo)){
							if (splittedLine[effectiveTimeColumn].compareTo(date)<=0){
								prevLine=nextLine;
								
							}
						}else{
							if (splittedLine[effectiveTimeColumn].compareTo(date)<=0){
//								bw.append(prevLine);
//								bw.append("\r\n");
								writeLine(bw, prevLine);
								prevLine=nextLine;
								prevCompo=splittedLine[componentColumn];
								lines++;
							}
						}
					}
				}
//				bw.append(prevLine);
//				bw.append("\r\n");
				writeLine(bw, prevLine);
				lines++;
				
			}
			
			bw.close();
			br.close();
			long end1 = System.currentTimeMillis();
			long elapsed1 = (end1 - start1);
			System.out.println("Lines in output file  : " + lines);
			System.out.println("Completed in " + elapsed1 + " ms");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the map columns.
	 *
	 * @param header the header
	 * @return the map columns
	 */
	private void getMapColumns(String header) {
		if (outputFields!=null){
			int cont=0;
			outputColumns=new int[outputFields.length];
			String[]spl=header.split("\t",-1);

			for (int j=0;j<outputFields.length;j++){
				outputColumns[j]=-1;
			}
			for (int j=0;j<outputFields.length;j++){
				for (int i=0;i<spl.length;i++){
				if (spl[i].toLowerCase().equals(outputFields[j].toLowerCase())){
					outputColumns[cont]=i;
					cont++;
					break;
				}
				}
			}
		}
	}

	/**
	 * Write header.
	 *
	 * @param bw the bw
	 * @param header the header
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeHeader(BufferedWriter bw, String header) throws IOException  {
		if (outputFields==null){
			bw.append(header);
		}else{
			for (int i=0;i<outputFields.length;i++){
				bw.append(outputFields[i]);
				if (i<outputFields.length-1){
					bw.append("\t");
				}
			}
		}
		bw.append("\r\n");
	}
	
	/**
	 * Write line.
	 *
	 * @param bw the bw
	 * @param line the line
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeLine(BufferedWriter bw, String line) throws IOException  {
		if (outputFields==null){
			bw.append(line);
		}else{
			String[]spl=line.split("\t",-1);
			for (int i=0;i<outputColumns.length;i++){
				if (outputColumns[i]>-1){
					bw.append(spl[outputColumns[i]]);
				}else{
					bw.append("");
				}
				if (i<outputColumns.length-1){
					bw.append("\t");
				}
			}
		}
		bw.append("\r\n");
	}
}
