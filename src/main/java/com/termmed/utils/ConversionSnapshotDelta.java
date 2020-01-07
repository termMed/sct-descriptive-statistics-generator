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

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class ConversionSnapshotDelta.
 */
public class ConversionSnapshotDelta {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger(ConversionSnapshotDelta.class);

	/**
	 * Snapshot file.
	 *
	 * @param file the file
	 * @param tempSortingFolder the temp sorting folder
	 * @param tempSortedFinalfolder the temp sorted finalfolder
	 * @param resultFile the result file
	 * @param date the date
	 * @param sortColumns the sort columns
	 * @param snapshotIndex the snapshot index
	 * @param effTimeColumn the eff time column
	 * @param fieldFilter the field filter
	 * @param fieldFilterValue the field filter value
	 * @param outputFields the output fields
	 * @return true, if successful
	 */
	public static boolean snapshotFile(File file, File tempSortingFolder,File tempSortedFinalfolder, File resultFile, String date, int[] sortColumns, int snapshotIndex, int effTimeColumn, Integer[] fieldFilter, String[] fieldFilterValue, String[] outputFields) {
		try {
			if (!tempSortingFolder.exists()) {
				tempSortingFolder.mkdirs();
			}
			boolean sorted = false;
			sorted = isSorted(file, sortColumns);
			if (!sorted) {
				File sortedFile = new File(tempSortedFinalfolder, "Sorted" + file.getName());
				FileSorter fsc = new FileSorter(file, sortedFile, tempSortingFolder, sortColumns);
				fsc.execute();
				fsc = null;
				System.gc();
				SnapshotGenerator sg = new SnapshotGenerator(sortedFile, date, snapshotIndex, effTimeColumn, resultFile, fieldFilter,fieldFilterValue, outputFields);
				sg.execute();
				sg=null;
				System.gc();
				if (sortedFile.exists()){
					sortedFile.delete();
				}
			}else{

				SnapshotGenerator sg = new SnapshotGenerator(file, date, snapshotIndex, effTimeColumn, resultFile, fieldFilter,fieldFilterValue,outputFields);
				sg.execute();
				sg=null;
				System.gc();
			}
		} catch (Exception e) {
			log.error(e);
			return false;
		} finally {
			FileHelper.emptyFolder(tempSortingFolder);
		}
		return true;
	}

	/**
	 * Delta file.
	 *
	 * @param file the file
	 * @param deltaFile the delta file
	 * @param tempSortingFolder the temp sorting folder
	 * @param tempSortedFinalFolder the temp sorted final folder
	 * @param initDate the init date
	 * @param endDate the end date
	 * @return true, if successful
	 */
	public static boolean deltaFile(File file, File deltaFile, File tempSortingFolder,File tempSortedFinalFolder, String initDate, String endDate) {
		try {
			if (!tempSortingFolder.exists()) {
				tempSortingFolder.mkdirs();
			}
			boolean sorted = false;
			int[] sortColumns = new int[] { 0, 1 };
			sorted = isSorted(file, sortColumns);
			if (!sorted) {
				File sortedFile = new File(tempSortedFinalFolder, "Sorted" + file.getName());
				FileSorter fsc = new FileSorter(file, sortedFile, tempSortingFolder, sortColumns);
				fsc.execute();
				fsc = null;
				System.gc();

				DeltaGenerator dg = new DeltaGenerator(sortedFile, initDate, endDate, new int[]{0}, 1, deltaFile);
				dg.execute();
				dg=null;
				System.gc();
				if (sortedFile.exists()){
					sortedFile.delete();
				}
			}else{
			
				DeltaGenerator dg = new DeltaGenerator(file, initDate, endDate, new int[]{0}, 1, deltaFile);
				dg.execute();
				dg=null;
				System.gc();
			}
		} catch (Exception e) {
			log.error(e);
			return false;
		} finally {
			FileHelper.emptyFolder(tempSortingFolder);
		}

		return true;
	}

	/**
	 * Checks if is sorted.
	 *
	 * @param file the file
	 * @param sortColumns the sort columns
	 * @return true, if is sorted
	 */
	private static boolean isSorted(File file, int[] sortColumns) {
		SortAnalyzer sa = new SortAnalyzer(file, sortColumns);
		Boolean ret = sa.isSortedFile();
		sa = null;
		System.gc();
		return ret;
	}

}
