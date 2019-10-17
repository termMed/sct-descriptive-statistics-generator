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
package com.termmed.inheritance.model;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class LongWithDates.
 */
public class LongWithDates implements Comparable<Long> {

	/** The long nr. */
	Long longNr;
	
	/** The eff times. */
	List<Integer> effTimes;
	
	/** The ss times. */
	List<Integer> ssTimes;
	
	/**
	 * Instantiates a new long with dates.
	 *
	 * @param lngId the lng id
	 * @param effTime the eff time
	 * @param ssTime the ss time
	 */
	public LongWithDates(long lngId, int effTime, int ssTime) {
		this.longNr=lngId;
		this.effTimes=new ArrayList<Integer>();
		this.ssTimes=new ArrayList<Integer>();
		this.effTimes.add(effTime);
		this.ssTimes.add(ssTime);
	}
	
	/**
	 * Instantiates a new long with dates.
	 *
	 * @param lngId the lng id
	 * @param effTimes the eff times
	 * @param ssTimes the ss times
	 */
	public LongWithDates(long lngId, List<Integer> effTimes, List<Integer>ssTimes) {
		this.longNr=lngId;
		this.setEffTimes(effTimes);
		this.setSsTimes(ssTimes);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Long o) {
		return longNr.compareTo(o);
	}
	
	/**
	 * Between dates.
	 *
	 * @param date1 the date1
	 * @return true, if successful
	 */
	public boolean betweenDates(int date1){
		for (int i =0;i<effTimes.size();i++){
			if (date1>=effTimes.get(i) && date1<ssTimes.get(i)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the long nr.
	 *
	 * @return the long nr
	 */
	public Long getLongNr() {
		return longNr;
	}
	
	/**
	 * Sets the long nr.
	 *
	 * @param longNr the new long nr
	 */
	public void setLongNr(Long longNr) {
		this.longNr = longNr;
	}
	
	/**
	 * Gets the eff times.
	 *
	 * @return the eff times
	 */
	public List<Integer> getEffTimes() {
		return effTimes;
	}
	
	/**
	 * Sets the eff times.
	 *
	 * @param effTimes the new eff times
	 */
	public void setEffTimes(List<Integer> effTimes) {
		this.effTimes = effTimes;
	}
	
	/**
	 * Gets the ss times.
	 *
	 * @return the ss times
	 */
	public List<Integer> getSsTimes() {
		return ssTimes;
	}
	
	/**
	 * Sets the ss times.
	 *
	 * @param ssTimes the new ss times
	 */
	public void setSsTimes(List<Integer> ssTimes) {
		this.ssTimes = ssTimes;
	}

	/**
	 * Shrink dates.
	 */
	public void shrinkDates(){
		Integer efft1;
		Integer efft2;
		Integer sst1;
		Integer sst2;
		List<Integer> deleted=new ArrayList<Integer>();

		for (int i=0;i<effTimes.size();i++){
			if (i==149){
				boolean bstop=true;
			}
			if (! deleted.contains(i)){
				for (int j=0;j<effTimes.size();j++){
					if (j==149){
						boolean bstop=true;
					}
					if (! deleted.contains(j)){

						if (i!=j){
							efft1 = effTimes.get(i);
							efft2=effTimes.get(j);
							sst1=ssTimes.get(i);
							sst2=ssTimes.get(j);

							if (efft1<=efft2){
								if (sst1>= sst2){
									deleted.add(j);
								}else if(sst1>=efft2){
									deleted.add(j);
									ssTimes.set(i,sst2);
								}
							}else {
								if(sst1<=sst2){
									deleted.add(i);
									break;
								}else if(sst2>=efft1){
									deleted.add(i);
									ssTimes.set(j,sst1);
									break;
								}
							}
						}
					}
				}
			}
		}
		if (deleted.size()>0){
			//			ArrayList<Integer> newEffTimes = new ArrayList<Integer>();
			//			ArrayList<Integer> newSsTimes = new ArrayList<Integer>();
			for (int i=effTimes.size()-1;i>=0;i--){
				if (deleted.contains(i)){
					ssTimes.remove(i);
					effTimes.remove(i);
				}
			}
		}

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return longNr.hashCode();
	}

	/**
	 * Adds the dates.
	 *
	 * @param lwd the lwd
	 */
	public void addDates(LongWithDates lwd){
		this.effTimes.addAll(lwd.getEffTimes());
		this.ssTimes.addAll(lwd.getSsTimes());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object lwd){
		return longNr.equals(((LongWithDates)lwd).getLongNr());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return longNr.toString() ;
	}
	
	/**
	 * Gets the matching date range.
	 *
	 * @param childWithDates the child with dates
	 * @return the matching date range
	 */
	public LongWithDates getMatchingDateRange(LongWithDates childWithDates) {

		Integer efft1;
		Integer efft2;
		Integer sst1;
		Integer sst2;

		ArrayList<Integer> matchEffTimes = new  ArrayList<Integer>();
		ArrayList<Integer> matchSsTimes = new  ArrayList<Integer>();

		for (Integer i=0;i<effTimes.size();i++){
			for (Integer j=0;j<childWithDates.getEffTimes().size();j++){

				efft1 = effTimes.get(i);
				efft2=childWithDates.getEffTimes().get(j);
				sst1=ssTimes.get(i);
				sst2=childWithDates.getSsTimes().get(j);

				if (efft1<sst2 && efft2<sst1){
					if (efft1>efft2){
						matchEffTimes.add(efft1);
					}else{
						matchEffTimes.add(efft2);
					}
					if (sst1< sst2){
						matchSsTimes.add(sst1);
					}else{
						matchSsTimes.add(sst2);
					}
				}
			}
		}
		if (matchEffTimes.size()>0){
			LongWithDates ret=new LongWithDates(this.longNr, matchEffTimes, matchSsTimes);
			ret.shrinkDates();
			return ret;
		}
		return null;
	}
}

