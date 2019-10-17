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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.termmed.control.model.AControlPattern;
import com.termmed.control.model.ControlResultLine;
import com.termmed.fileprovider.CurrentFile;
import com.termmed.utils.FileHelper;
import com.termmed.utils.I_Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class ListOfChildrenAndDescendants.
 */
public class ListOfChildrenAndDescendants extends AControlPattern {

	/** The result file. */
	private File resultFile;

	/** The concept terms. */
	private HashMap<Long, String> conceptTerms;
	
	/**
	 * The Enum Indexes.
	 */
	private enum Indexes{
/** The stated children. */
STATED_CHILDREN,
/** The stated descendants. */
STATED_DESCENDANTS,
/** The inferred children. */
INFERRED_CHILDREN,
/** The inferred descendants. */
INFERRED_DESCENDANTS};

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#execute()
	 */
	public void execute() throws Exception {

		String file=CurrentFile.get().getSnapshotConceptFile();
		
		BufferedReader br=FileHelper.getReader(file);
		br.readLine();
		String line;
		String[] spl;
		HashMap<Long,int[]>SDconcepts=new HashMap<Long,int[]>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[4].equals("900000000000073002" )){
				SDconcepts.put(Long.parseLong(spl[0]),new int[4]);
			}
		}
		br.close();
		String rels;
		if (CurrentFile.get().getReleaseDependenciesFullFolders()!=null){
			rels=CurrentFile.get().getCompleteStatedRelationshipSnapshot();
		}else{
			rels=CurrentFile.get().getSnapshotStatedRelationshipFile();
		}
		br=FileHelper.getReader(rels);
		br.readLine();

		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") 
					&& spl[7].compareTo(I_Constants.ISA)==0){
				Long tgt=Long.parseLong(spl[5]);
				if (SDconcepts.containsKey(tgt)){
					int[] values=SDconcepts.get(tgt);
					values[Indexes.STATED_CHILDREN.ordinal()]++;
					SDconcepts.put(tgt,values);
				}
			}
		}
		br.close();
		
		if (CurrentFile.get().getReleaseDependenciesFullFolders()!=null){
			rels=CurrentFile.get().getCompleteRelationshipSnapshot();
		}else{
			rels=CurrentFile.get().getSnapshotRelationshipFile();
		}
		br=FileHelper.getReader(rels);
		br.readLine();

		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") 
					&& spl[7].compareTo(I_Constants.ISA)==0){
				Long tgt=Long.parseLong(spl[5]);
				if (SDconcepts.containsKey(tgt)){
					int[] values=SDconcepts.get(tgt);
					values[Indexes.INFERRED_CHILDREN.ordinal()]++;
					SDconcepts.put(tgt,values);
				}
			}
		}
		br.close();
		
		String tclos_file;
		tclos_file=CurrentFile.get().getTransitiveClosureStatedFile();
		br=FileHelper.getReader(tclos_file);
		br.readLine();

		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
				Long tgt=Long.parseLong(spl[1]);
				if (SDconcepts.containsKey(tgt)){
					int[] values=SDconcepts.get(tgt);
					values[Indexes.STATED_DESCENDANTS.ordinal()]++;
					SDconcepts.put(tgt,values);
				}
		}
		br.close();
		tclos_file=CurrentFile.get().getTransitiveClosureInferredFile();
		br=FileHelper.getReader(tclos_file);
		br.readLine();

		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
				Long tgt=Long.parseLong(spl[1]);
				if (SDconcepts.containsKey(tgt)){
					int[] values=SDconcepts.get(tgt);
					values[Indexes.INFERRED_DESCENDANTS.ordinal()]++;
					SDconcepts.put(tgt,values);
				}
		}
		br.close();
		
		String outputFile=I_Constants.STATS_OUTPUT_FOLDER + "/" + resultFile.getName();
		BufferedWriter bw = FileHelper.getWriter(outputFile);
		bw.append("id");
		bw.append("\t");
		bw.append("term");
		bw.append("\t");
		bw.append("statedChildren");
		bw.append("\t");
		bw.append("statedDescendants");
		bw.append("\t");
		bw.append("inferredChildren");
		bw.append("\t");
		bw.append("inferredDescendants");
		bw.append("\r\n");
		for (Long cid:SDconcepts.keySet()){
			int[] values=SDconcepts.get(cid);
			bw.append(cid.toString());
			bw.append("\t");
			bw.append(conceptTerms.get(cid));
			bw.append("\t");
			bw.append(String.valueOf(values[Indexes.STATED_CHILDREN.ordinal()]));
			bw.append("\t");
			bw.append(String.valueOf(values[Indexes.STATED_DESCENDANTS.ordinal()]));
			bw.append("\t");
			bw.append(String.valueOf(values[Indexes.INFERRED_CHILDREN.ordinal()]));
			bw.append("\t");
			bw.append(String.valueOf(values[Indexes.INFERRED_DESCENDANTS.ordinal()]));
			bw.append("\r\n");
			
		}
		bw.close();

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
		return null;
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
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setChangedConceptsList(java.util.HashSet)
	 */
	public void setChangedConceptsList(HashSet<String> changedConcepts) {
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setCurrentEffTime(java.lang.String)
	 */
	public void setCurrentEffTime(String releaseDate) {
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setPreviousEffTime(java.lang.String)
	 */
	public void setPreviousEffTime(String previousReleaseDate) {
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setPatternId(java.lang.String)
	 */
	public void setPatternId(String patternId) {
	}


	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#getResultCount()
	 */
	public int getResultCount() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.termmed.control.model.IControlPattern#setConceptTerms(java.util.HashMap)
	 */
	public void setConceptTerms(HashMap<Long, String> conceptTerms) {
		this.conceptTerms=conceptTerms;
	}

}
