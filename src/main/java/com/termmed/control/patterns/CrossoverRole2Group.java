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
import com.termmed.control.model.MatchObjectRedundantRel;
import com.termmed.control.model.RedundantRelDetailLine;
import com.termmed.control.roletesting.RelationshipTests;
import com.termmed.fileprovider.CurrentFile;
import com.termmed.fileprovider.PreviousFile;
import com.termmed.utils.FileHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class CrossoverRole2Group.
 */
public class CrossoverRole2Group extends AControlPattern {

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
		RelationshipTests rt=new RelationshipTests(resultTmpFolder.getAbsolutePath());

		String statedRels;
		String inferRels;
		String inferRels4TC;
		String concFile;
		if (CurrentFile.get().getReleaseDependenciesFullFolders()!=null){
			statedRels=CurrentFile.get().getSnapshotStatedRelationshipFile();
			inferRels4TC=CurrentFile.get().getCompleteRelationshipSnapshot();
			inferRels=CurrentFile.get().getSnapshotRelationshipFile();
			concFile=CurrentFile.get().getSnapshotConceptFile();
		}else{
			statedRels=CurrentFile.get().getSnapshotStatedRelationshipFile();
			inferRels=CurrentFile.get().getSnapshotRelationshipFile();
			inferRels4TC=inferRels;
			concFile=CurrentFile.get().getSnapshotConceptFile();
		}
		String currFile="current_r2g_cross.txt";
		rt.searchCrossover(inferRels, conceptTerms, statedRels, inferRels4TC, concFile
				,RelationshipTests.UNGROUPED2GROUPED
				,currFile);

		if (PreviousFile.get().getReleaseDependenciesFullFolders()!=null){
			inferRels4TC=PreviousFile.get().getCompleteRelationshipSnapshot();
			inferRels=PreviousFile.get().getSnapshotRelationshipFile();
		}else{
			inferRels=PreviousFile.get().getSnapshotRelationshipFile();
			inferRels4TC=inferRels;
		}
		String prevFile="previous_r2g_cross.txt";
		
		rt.searchCrossover(inferRels, (String)null, null, inferRels4TC, null
				,RelationshipTests.UNGROUPED2GROUPED
				,prevFile);

		rt=null;
		getResults(resultTmpFolder, prevFile,currFile);
		FileHelper.emptyFolder(resultTmpFolder);
	}

	/**
	 * Gets the results.
	 *
	 * @param resultTmpFolder the result tmp folder
	 * @param prevFile the prev file
	 * @param currFile the curr file
	 * @return the results
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void getResults(File resultTmpFolder, String prevFile,
			String currFile) throws IOException {
		gson=new Gson(); 

		sep = System.getProperty("line.separator");

		BufferedReader br = FileHelper.getReader(new File(resultTmpFolder,prevFile));

		HashSet<String> prev=new HashSet<String>();
		br.readLine();
		String line;
		String[] spl;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl.length>1){
				prev.add(spl[1]);
			}

		}
		br.close();
		sample=new ArrayList<ControlResultLine>();
		BufferedWriter bw = FileHelper.getWriter(resultFile);
		bw.append("[");
		boolean first=true;
		br=FileHelper.getReader(new File(resultTmpFolder,currFile));
		br.readLine();
		boolean firstLine=true;
		boolean emptyLine=true;
		boolean firstLine2=true;
		ControlResultLine crl=null;
		MatchObjectRedundantRel mobj=null;
		RedundantRelDetailLine detLine;
		String relId1="";
		String group2="";
		List<RedundantRelDetailLine>list=null;
		List<RedundantRelDetailLine>list2=null;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl.length>1){
				if (emptyLine){
					if (firstLine){
						crl=new ControlResultLine();
						mobj=new MatchObjectRedundantRel();
						crl.setChanged(changedConcepts.contains(spl[1]));
						crl.setNew(newConcepts.contains(spl[1]));
						crl.setConceptId(spl[1]);
						crl.setTerm(conceptTerms.get(Long.parseLong(spl[1])));
						crl.setSemtag(getSemTag(crl.getTerm()));
						crl.setCurrentEffectiveTime(currentEffTime);
						crl.setPreviousEffectiveTime(previousEffTime);
						crl.setForm("stated");

						crl.setPatternId(patternId);
						crl.setPreexisting(prev.contains(spl[1]));
						crl.setResultId(UUID.randomUUID().toString());
						crl.setCurrent(true);
						list=new ArrayList<RedundantRelDetailLine>();
						detLine = new RedundantRelDetailLine(line);
						if (detLine.getGroup().equals("0")){
							relId1= detLine.getDestinationId() + "|" + detLine.getDestinationTerm();
						}else{
							group2=detLine.getGroup();
						}
						list.add(detLine);
						mobj.setGroup1(list);
						firstLine=false;
					}else{
						detLine = new RedundantRelDetailLine(line);
						list.add(detLine);
						mobj.setGroup1(list);

					}

				}else{
					if (firstLine2){
						list2=new ArrayList<RedundantRelDetailLine>();
						detLine = new RedundantRelDetailLine(line);
						if (detLine.getGroup().equals("0")){
							relId1= detLine.getDestinationId() + "|" + detLine.getDestinationTerm();
						}else{
							group2=detLine.getGroup();
						}
						list2.add(detLine);
						mobj.setGroup2(list2);
						firstLine2=false;
					}else{
						detLine = new RedundantRelDetailLine(line);
						list2.add(detLine);
						mobj.setGroup2(list2);
					}
				}
			}else if (spl[0].indexOf("--")>-1){

				crl.setMatchObject(mobj);
				crl.setMatchDescription("Crossover between " + relId1 + ", and group:#"  + group2);
				if (first){
					first=false;
				}else{
					bw.append(",");
				}
				writeResultLine(bw, crl);
				firstLine=true;
				firstLine2=true;
				emptyLine=true;
			}else if (spl[0].trim().equals("")){
				emptyLine=false;
				firstLine2=true;

			}
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
