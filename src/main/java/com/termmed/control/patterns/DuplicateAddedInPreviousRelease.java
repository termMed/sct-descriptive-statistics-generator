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
package com.termmed.control.patterns;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.termmed.control.concept.TestConcepts;
import com.termmed.control.model.AControlPattern;
import com.termmed.control.model.ControlResultLine;
import com.termmed.fileprovider.CurrentFile;
import com.termmed.utils.FileHelper;
import com.termmed.utils.I_Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class DuplicateAddedInPreviousRelease.
 */
public class DuplicateAddedInPreviousRelease extends AControlPattern {

	/** The result file. */
	private File resultFile;
	
	/** The new concepts. */
	private HashSet<String> newConcepts;
	
	/** The changed concepts. */
	private HashSet<String> changedConcepts;
	
	/** The current eff time. */
	private String currentEffTime;
	
	/** The previous eff time. */
	private String previousEffTime;
	
	/** The pattern id. */
	private String patternId;

	/** The gson. */
	private Gson gson;
	
	/** The sep. */
	private String sep;
	
	/** The sample. */
	private List<ControlResultLine> sample;
	
	/** The result count. */
	private int resultCount;
	
	/** The concept terms. */
	private HashMap<Long, String> conceptTerms;

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#execute()
	 */
	public void execute() throws Exception {

		resultCount=0;

		String fullCpts = CurrentFile.get().getConceptFile();

		String attvalue = CurrentFile.get().getSnapshotAttributeValueFile();


		TestConcepts tc;
		//		String desc=PreviousFile.get().getSnapshotDescriptionFile();

		File completedFilesFolder=CurrentFile.get().getCompletedFilesFolder();
		tc=new TestConcepts(completedFilesFolder);
		File resultTmpFolder=new File(resultFile.getParentFile() + "/tmp");
		if (!resultTmpFolder.exists()){
			resultTmpFolder.mkdirs();
		}else{
			FileHelper.emptyFolder(resultTmpFolder);
		}
		HashSet<Long> cpts=tc.getNewConceptPreviousAndInactiveCurrent(fullCpts,resultTmpFolder,currentEffTime,previousEffTime);

		HashSet<Long> filtCpts=tc.filterConceptsByReason(cpts,attvalue,I_Constants.DUPLICATE);
		
		getResults(filtCpts);
	}


	/**
	 * Gets the results.
	 *
	 * @param concepts the concepts
	 * @return the results
	 * @throws Exception the exception
	 */
	private void getResults(HashSet<Long> concepts
			) throws Exception {
		gson=new Gson(); 
		sep = System.getProperty("line.separator");


		sample=new ArrayList<ControlResultLine>();
		BufferedWriter bw = FileHelper.getWriter(resultFile);
		bw.append("[");
		boolean first=true;
		ControlResultLine crl=null;

		for (Long currCid:concepts){
			crl = new ControlResultLine();
			crl.setChanged(changedConcepts.contains(currCid));
			crl.setNew(newConcepts.contains(currCid));
			crl.setConceptId(currCid.toString());
			crl.setTerm(conceptTerms.get(currCid));
			crl.setSemtag(getSemTag(crl.getTerm()));
			crl.setCurrentEffectiveTime(currentEffTime);
			crl.setPreviousEffectiveTime(previousEffTime);
			crl.setForm("stated");

			crl.setPatternId(patternId);
			crl.setPreexisting(false);
			crl.setResultId(UUID.randomUUID().toString());
			crl.setCurrent(true);
			crl.setMatchDescription("New duplicate concepts identified in the subset of concepts that were added in the previous release.");
			if (first){
				first=false;
			}else{
				bw.append(",");
			}
			writeResultLine(bw, crl);


		}
		bw.append("]");
		bw.close();
	}

	/**
	 * Write result line.
	 *
	 * @param bw the bw
	 * @param crl the crl
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeResultLine(BufferedWriter bw, ControlResultLine crl) throws IOException {
		bw.append(gson.toJson(crl).toString());
		bw.append(sep);
		if (sample.size()<10){
			sample.add(crl);
		}
		resultCount++;
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setConfigFile(java.io.File)
	 */
	public void setConfigFile(File configFile) {

	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#getSample()
	 */
	public List<ControlResultLine> getSample() {
		return sample;
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setResultFile(java.io.File)
	 */
	public void setResultFile(File resultFile) {
		this.resultFile=resultFile;

	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setNewConceptsList(java.util.HashSet)
	 */
	public void setNewConceptsList(HashSet<String> newConcepts) {
		this.newConcepts=newConcepts;		
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setChangedConceptsList(java.util.HashSet)
	 */
	public void setChangedConceptsList(HashSet<String> changedConcepts) {
		this.changedConcepts=changedConcepts;
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setCurrentEffTime(java.lang.String)
	 */
	public void setCurrentEffTime(String releaseDate) {
		this.currentEffTime=releaseDate;
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setPreviousEffTime(java.lang.String)
	 */
	public void setPreviousEffTime(String previousReleaseDate) {
		this.previousEffTime=previousReleaseDate;
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setPatternId(java.lang.String)
	 */
	public void setPatternId(String patternId) {
		this.patternId=patternId;
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#getResultCount()
	 */
	public int getResultCount() {
		return resultCount;
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setConceptTerms(java.util.HashMap)
	 */
	public void setConceptTerms(HashMap<Long, String> conceptTerms) {
		this.conceptTerms=conceptTerms;
	}
}
