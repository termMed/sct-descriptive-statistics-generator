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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

// TODO: Auto-generated Javadoc
/**
 * The Class SortAnalyzer.
 */
public class SortAnalyzer {

	/** The sort columns. */
	private int[] sortColumns;
	
	/** The file. */
	private File file;

	/**
	 * Instantiates a new sort analyzer.
	 *
	 * @param file the file
	 * @param sortColumns the sort columns
	 */
	public SortAnalyzer(File file, int[] sortColumns) {
		super();
		this.file = file;
		this.sortColumns = sortColumns;
	}

	/**
	 * Checks if is sorted file.
	 *
	 * @return true, if is sorted file
	 */
	public boolean isSortedFile(){
		boolean sorted=true;
		try {
			long start1 = System.currentTimeMillis();

			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			BufferedReader br = new BufferedReader(isr);

			double lines = 0;
			String nextLine;
			br.readLine();

			String[] splittedLine;
			int comp;

			String prevValues[]=new String[sortColumns.length];

			if ((nextLine= br.readLine()) != null) {
				splittedLine =nextLine.split("\t",-1);

				for (int i=0;i<sortColumns.length;i++){
					prevValues[i]=splittedLine[sortColumns[i]];
				}

				lines++;
			}
			if ( nextLine!=null) {
				while ((nextLine= br.readLine()) != null && sorted ) {
					splittedLine = nextLine.split("\t",-1);

					for (int i=0;i<sortColumns.length;i++){
						comp=prevValues[i].compareTo(splittedLine[sortColumns[i]]);
						if (comp<0){
							break;
						}
						if (comp>0){
							sorted=false;
							break;
						}
					}
					for (int i=0;i<sortColumns.length;i++){
						prevValues[i]=splittedLine[sortColumns[i]];
					}

					lines++;
				}

			}

			br.close();
			long end1 = System.currentTimeMillis();
			long elapsed1 = (end1 - start1);
			System.out.println("Sorted: " +  sorted + " - Lines processed  : " + lines);
			System.out.println("Completed in " + elapsed1 + " ms");
			return sorted;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
