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
import com.termmed.fileprovider.CurrentFile;
import com.termmed.fileprovider.PreviousFile;
import com.termmed.utils.FileHelper;
import com.termmed.utils.TClosure;

// TODO: Auto-generated Javadoc
/**
 * The Class ConceptsGainIntermediatePrimitiveParent.
 */
public class ConceptsGainIntermediatePrimitiveParent extends AControlPattern {

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

		String statedRels;
		String concFile;
		String completeConcFile=null;
		String currFile=null;
		TestConcepts tc;
		String prevConceptFile=PreviousFile.get().getSnapshotConceptFile();
		File completedFilesFolder=CurrentFile.get().getCompletedFilesFolder();
		if (CurrentFile.get().getReleaseDependenciesFullFolders()!=null){
			statedRels=CurrentFile.get().getCompleteStatedRelationshipSnapshot();
			completeConcFile=CurrentFile.get().getCompleteConceptSnapshot();
		}else{
			statedRels=CurrentFile.get().getSnapshotStatedRelationshipFile();
		}
		tc=new TestConcepts(completedFilesFolder);
		String tClos_file=CurrentFile.get().getTransitiveClosureStatedFile();
		TClosure tClos=new TClosure(statedRels,4,5,7,2);
		if (tClos_file==null){
			tClos_file=new File(completedFilesFolder,"t_closure_stated.txt").getAbsolutePath();
			tClos.toFile(tClos_file);
			CurrentFile.get().setTransitiveClosureStatedFile(tClos_file);
		}
		concFile=CurrentFile.get().getSnapshotConceptFile();
		if (CurrentFile.get().getIntermediatePrimitiveFile()==null){
			
			currFile="current_interm_prim.txt";

			tc.getIntermediatePrimitive( concFile,completeConcFile, currFile, tClos_file);

			currFile=new File(completedFilesFolder,currFile).getAbsolutePath();
			CurrentFile.get().setIntermediatePrimitiveFile(currFile);

			tc=null;
		}else{
			currFile=CurrentFile.get().getIntermediatePrimitiveFile();
		}
		tc=new TestConcepts(completedFilesFolder);
		
		if (PreviousFile.get().getReleaseDependenciesFullFolders()!=null){
			statedRels=PreviousFile.get().getCompleteStatedRelationshipSnapshot();
		}else{
			statedRels=PreviousFile.get().getSnapshotStatedRelationshipFile();
		}
		String gainPrimFile="concept_gain_prim.txt";

		TClosure prevTClos=new TClosure(statedRels,4,5,7,2);

		tc.getConceptGainedPrimitiveIntermediateParent(prevConceptFile, concFile,currFile,prevTClos,tClos,gainPrimFile);
		tClos=null;
		prevTClos=null;
		gainPrimFile=new File(completedFilesFolder,gainPrimFile).getAbsolutePath();
		tc=null;
		
		getResults(resultTmpFolder, gainPrimFile);
		FileHelper.emptyFolder(resultTmpFolder);
	}



	/**
	 * Gets the results.
	 *
	 * @param resultTmpFolder the result tmp folder
	 * @param gainPrimFile the gain prim file
	 * @return the results
	 * @throws Exception the exception
	 */
	private void getResults(File resultTmpFolder, String gainPrimFile
			) throws Exception {
		gson=new Gson(); 
		sep = System.getProperty("line.separator");


		BufferedReader br = FileHelper.getReader(new File(gainPrimFile));
		HashSet<String> curr=new HashSet<String>();
		br.readLine();
		String[] spl;
		String line;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			curr.add(spl[0]);
		}
		br.close();
		String tClos_file=null;
		if (PreviousFile.get().getTransitiveClosureInferredFile()==null){
			String prevInferredRels=null;
			if (PreviousFile.get().getReleaseDependenciesFullFolders()!=null){
				prevInferredRels=PreviousFile.get().getCompleteRelationshipSnapshot();
			}else{
				prevInferredRels=PreviousFile.get().getSnapshotRelationshipFile();
			}
			TClosure tClos=new TClosure(prevInferredRels,4,5,7,2);
			tClos_file=new File(PreviousFile.get().getCompletedFilesFolder(),"t_closure_inferred.txt").getAbsolutePath();
			tClos.toFile(tClos_file);
			tClos=null;
			PreviousFile.get().setTransitiveClosureInferredFile(tClos_file);
		}else{
			tClos_file=PreviousFile.get().getTransitiveClosureInferredFile();
		}

		br = FileHelper.getReader(CurrentFile.get().getSnapshotConceptFile());
		HashSet<Long> retired=new HashSet<Long>();
		br.readLine();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].compareTo("0")==0){
				retired.add(Long.parseLong(spl[0]));
			}
		}
		br.close();
		
		 br = FileHelper.getReader(tClos_file);
		br.readLine();
		HashMap<Long,Integer>prevSubTypes=new HashMap<Long,Integer>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (curr.contains(spl[1])){
				Long cid=Long.parseLong(spl[1]);
				Long descId=Long.parseLong(spl[0]);
				if (retired.contains(descId)){
					continue;
				}
				Integer count=prevSubTypes.get(cid);
				if (count==null){
					count=1;
				}else{
					count++;
				}
				prevSubTypes.put(cid, count);
			}
		}
		br.close();
		retired=null;

		if (CurrentFile.get().getTransitiveClosureInferredFile()==null){
			String currInferredRels=null;
			if (CurrentFile.get().getReleaseDependenciesFullFolders()!=null){
				currInferredRels=CurrentFile.get().getCompleteRelationshipSnapshot();
			}else{
				currInferredRels=CurrentFile.get().getSnapshotRelationshipFile();
			}
			TClosure tClos=new TClosure(currInferredRels,4,5,7,2);
			tClos_file=new File(CurrentFile.get().getCompletedFilesFolder(),"t_closure_inferred.txt").getAbsolutePath();
			tClos.toFile(tClos_file);
			tClos=null;
			CurrentFile.get().setTransitiveClosureInferredFile(tClos_file);
		}else{
			tClos_file=CurrentFile.get().getTransitiveClosureInferredFile();
		}
		br=FileHelper.getReader(tClos_file);
		br.readLine();
		HashMap<Long,Integer>currSubTypes=new HashMap<Long,Integer>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (curr.contains(spl[1])){
				Long cid=Long.parseLong(spl[1]);
				Integer count=currSubTypes.get(cid);
				if (count==null){
					count=1;
				}else{
					count++;
				}
				currSubTypes.put(cid, count);
			}
		}
		br.close();

		sample=new ArrayList<ControlResultLine>();
		BufferedWriter bw = FileHelper.getWriter(resultFile);
		bw.append("[");
		boolean first=true;
		ControlResultLine crl=null;

		for (String currCid:curr){
			Integer prevCount=prevSubTypes.get(Long.parseLong(currCid));
			Integer currCount=currSubTypes.get(Long.parseLong(currCid));
			if ((prevCount!=null && currCount==null)
					|| (prevCount==null && currCount!=null)
					|| (prevCount!=null && currCount!=null && prevCount.intValue()!=currCount.intValue())){
				crl=new ControlResultLine();
				crl.setChanged(changedConcepts.contains(currCid));
				crl.setNew(newConcepts.contains(currCid));
				crl.setConceptId(currCid);
				crl.setTerm(conceptTerms.get(Long.parseLong(currCid)));
				crl.setSemtag(getSemTag(crl.getTerm()));
				crl.setCurrentEffectiveTime(currentEffTime);
				crl.setPreviousEffectiveTime(previousEffTime);
				crl.setForm("stated");

				crl.setPatternId(patternId);
				crl.setPreexisting(false);
				crl.setResultId(UUID.randomUUID().toString());
				crl.setCurrent(true);
				crl.setMatchDescription("Concepts gained intermediate primitive stated parent have lost or gained inferred subtypes.");
				if (first){
					first=false;
				}else{
					bw.append(",");
				}
				writeResultLine(bw, crl);


			}
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
