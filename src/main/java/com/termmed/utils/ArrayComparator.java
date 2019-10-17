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

import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * The Class ArrayComparator.
 */
public class ArrayComparator implements Comparator<String[]> {

	/** The indexes. */
	private int[] indexes;
	
	/** The reverse. */
	private boolean reverse;

	/**
	 * Instantiates a new array comparator.
	 *
	 * @param indexes the indexes
	 * @param reverse the reverse
	 */
	public ArrayComparator(int[] indexes, boolean reverse) {
		this.indexes = indexes;
		this.reverse = reverse;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(String[] o1, String[] o2) {
		for (int i = 0; i < indexes.length; i++) {
			if (o1[indexes[i]].compareTo(o2[indexes[i]]) < 0) {
				return reverse ? 1 : -1;
			} else if (o1[indexes[i]].compareTo(o2[indexes[i]]) > 0) {
				return reverse ? -1 : 1;
			}
		}
		return 0;
	}
}
