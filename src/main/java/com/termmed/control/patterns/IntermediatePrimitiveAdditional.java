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
import com.termmed.control.concept.TestConcepts;
import com.termmed.control.model.AControlPattern;
import com.termmed.control.model.ControlResultLine;
import com.termmed.control.model.IControlPattern;
import com.termmed.fileprovider.CurrentFile;
import com.termmed.utils.FileHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class IntermediatePrimitiveAdditional.
 */
public class IntermediatePrimitiveAdditional extends AControlPattern {

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

		String concFile;
		String completeConcFile=null;
		String currFile=null;
		TestConcepts tc;
		File completedFilesFolder=CurrentFile.get().getCompletedFilesFolder();
		tc=new TestConcepts(completedFilesFolder);
		if (CurrentFile.get().getIntermediatePrimitiveFile()==null){
			if (CurrentFile.get().getReleaseDependenciesFullFolders()!=null){
				completeConcFile=CurrentFile.get().getCompleteConceptSnapshot();
			}
			concFile=CurrentFile.get().getSnapshotConceptFile();
			currFile="current_interm_prim.txt";

			String tClos_file=CurrentFile.get().getTransitiveClosureStatedFile();
			tc.getIntermediatePrimitive( concFile,completeConcFile, currFile, tClos_file);

			currFile=new File(completedFilesFolder,currFile).getAbsolutePath();
			CurrentFile.get().setIntermediatePrimitiveFile(currFile);

			tc=null;
		}else{
			currFile=CurrentFile.get().getIntermediatePrimitiveFile();
		}
		tc=new TestConcepts(completedFilesFolder);
		if (CurrentFile.get().getReleaseDependenciesFullFolders()!=null){
			completeConcFile=CurrentFile.get().getCompleteConceptSnapshot();
		}
		concFile=CurrentFile.get().getSnapshotConceptFile();
		String inferFile="inferred_interm_prim.txt";

		String tClos_file=CurrentFile.get().getTransitiveClosureInferredFile();
		
		tc.getIntermediatePrimitive(concFile, completeConcFile, inferFile, tClos_file);
		inferFile=new File(completedFilesFolder,inferFile).getAbsolutePath();
		tc=null;
		
		getResults(resultTmpFolder, inferFile,currFile);
		FileHelper.emptyFolder(resultTmpFolder);
	}



	/**
	 * Gets the results.
	 *
	 * @param resultTmpFolder the result tmp folder
	 * @param inferFile the infer file
	 * @param currFile the curr file
	 * @return the results
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void getResults(File resultTmpFolder, String inferFile,
			String currFile) throws IOException {
		gson=new Gson(); 

		sep = System.getProperty("line.separator");

		BufferedReader br = FileHelper.getReader(new File(currFile));

		HashSet<String> curr=new HashSet<String>();
		br.readLine();
		String line;
		String[] spl;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			curr.add(spl[0]);
		}
		br.close();
		sample=new ArrayList<ControlResultLine>();
		BufferedWriter bw = FileHelper.getWriter(resultFile);
		bw.append("[");
		boolean first=true;
		br=FileHelper.getReader(new File(inferFile));
		br.readLine();
		ControlResultLine crl=null;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (curr.contains(spl[0])){
				continue;
			}
			crl=new ControlResultLine();
			crl.setChanged(changedConcepts.contains(spl[0]));
			crl.setNew(newConcepts.contains(spl[0]));
			crl.setConceptId(spl[0]);
			crl.setTerm(conceptTerms.get(Long.parseLong(spl[0])));
			crl.setSemtag(getSemTag(crl.getTerm()));
			crl.setCurrentEffectiveTime(currentEffTime);
			crl.setPreviousEffectiveTime(previousEffTime);
			crl.setForm("inferred");

			crl.setPatternId(patternId);
			crl.setPreexisting(curr.contains(spl[0]));
			crl.setResultId(UUID.randomUUID().toString());
			crl.setCurrent(true);
			crl.setMatchDescription("Additional intermediate primitive in inferred view.");
			if (first){
				first=false;
			}else{
				bw.append(",");
			}
			writeResultLine(bw, crl);
		}
		bw.append("]");
		bw.close();
		br.close();

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
