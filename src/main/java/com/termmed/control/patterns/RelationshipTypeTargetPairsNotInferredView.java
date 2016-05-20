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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.termmed.control.model.AControlPattern;
import com.termmed.control.model.ControlResultLine;
import com.termmed.control.model.IControlPattern;
import com.termmed.fileprovider.CurrentFile;
import com.termmed.fileprovider.PreviousFile;
import com.termmed.utils.FileHelper;
import com.termmed.utils.I_Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class RelationshipTypeTargetPairsNotInferredView.
 */
public class RelationshipTypeTargetPairsNotInferredView extends AControlPattern {

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
		File resultTmpFolder=new File(resultFile.getParentFile() + "/tmp");
		if (!resultTmpFolder.exists()){
			resultTmpFolder.mkdirs();
		}else{
			FileHelper.emptyFolder(resultTmpFolder);
		}

		String inferRels=PreviousFile.get().getSnapshotRelationshipFile();
		String statedRels=PreviousFile.get().getSnapshotStatedRelationshipFile();
		BufferedReader br = FileHelper.getReader(inferRels);
		
		br.readLine();
		String line;
		String[] spl;
		HashSet<String> roles=new HashSet<String>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[7].compareTo(I_Constants.ISA)!=0){
				String key=spl[4]+ "-" + spl[7] + ":" + spl[5];
				roles.add(key);
			}
		}
		br.close();

		HashSet<String>preEx=new HashSet<String>();
		br = FileHelper.getReader(statedRels);
		
		br.readLine();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[7].compareTo(I_Constants.ISA)!=0){
				String key=spl[4]+ "-" + spl[7] + ":" + spl[5];
				if (!roles.contains(key)){
					preEx.add(key);
				}
			}
		}
		br.close();
		
		inferRels=CurrentFile.get().getSnapshotRelationshipFile();
		statedRels=CurrentFile.get().getSnapshotStatedRelationshipFile();
		
		br = FileHelper.getReader(inferRels);
		
		br.readLine();
		roles=new HashSet<String>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[7].compareTo(I_Constants.ISA)!=0){
				String key=spl[4]+ "-" + spl[7] + ":" + spl[5];
				roles.add(key);
			}
		}
		br.close();
		gson=new Gson(); 

		sep = System.getProperty("line.separator");

		sample=new ArrayList<ControlResultLine>();
		BufferedWriter bw = FileHelper.getWriter(resultFile);
		bw.append("[");
		boolean first=true;
		ControlResultLine crl=null;
		
		br = FileHelper.getReader(statedRels);
		
		br.readLine();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[7].compareTo(I_Constants.ISA)!=0){
				String key=spl[4]+ "-" + spl[7] + ":" + spl[5];
				if (!roles.contains(key)){
					String pair=key.substring(key.indexOf("-")+1);
					crl=new ControlResultLine();
					crl.setChanged(changedConcepts.contains(spl[4]));
					crl.setNew(newConcepts.contains(spl[4]));
					crl.setConceptId(spl[4]);
					crl.setTerm(conceptTerms.get(Long.parseLong(spl[4])));
					crl.setSemtag(getSemTag(crl.getTerm()));
					crl.setCurrentEffectiveTime(currentEffTime);
					crl.setPreviousEffectiveTime(previousEffTime);
					crl.setForm("inferred");

					crl.setPatternId(patternId);
					crl.setPreexisting(preEx.contains(key));
					crl.setResultId(UUID.randomUUID().toString());
					crl.setCurrent(true);
					crl.setMatchDescription("RelationshipType-target pairs that are stated but not inferred, pair:" + pair);
					if (first){
						first=false;
					}else{
						bw.append(",");
					}
					writeResultLine(bw, crl);
					
				}
			}
		}
		br.close();
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
		// TODO Auto-generated method stub

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
