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
package com.termmed.control.roletesting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.termmed.inheritance.model.Relationship;
import com.termmed.inheritance.model.RelationshipGroup;
import com.termmed.inheritance.model.RelationshipGroupList;
import com.termmed.utils.FileHelper;
import com.termmed.utils.FileSorter;
import com.termmed.utils.I_Constants;
import com.termmed.utils.TClosure;

// TODO: Auto-generated Javadoc
/**
 * The Class RelationshipTests.
 */
public class RelationshipTests {

	/** The Constant ROLE_REDUNDANCE. */
	private static final String ROLE_REDUNDANCE = "ROLE_REDUNDANCE";
	
	/** The Constant CROSSOVER. */
	private static final String CROSSOVER = "CROSSOVER";
	
	/** The Constant ISA_REDUNDANCE. */
	private static final String ISA_REDUNDANCE = "ISA_REDUNDANCE";
	
	/** The Constant UNGROUPED2UNGROUPED. */
	public static final String UNGROUPED2UNGROUPED = "UNGROUPED_2_UNGROUPED";
	
	/** The Constant UNGROUPED2GROUPED. */
	public static final String UNGROUPED2GROUPED = "UNGROUPED_2_GROUPED";
	
	/** The Constant GROUPED2GROUPED. */
	public static final String GROUPED2GROUPED = "GROUPED_2_GROUPED";
	
	/** The c relationships. */
	private  ArrayList<Relationship> cRelationships;
	
	/** The sorted folder tmp. */
	private File sortedFolderTmp;
	
	/** The sort folder tmp. */
	private File sortFolderTmp;
	
	/** The output folder. */
	private String outputFolder;
	
	/** The t clos. */
	private TClosure tClos;
	
	/** The t co. */
	private TestCrossover tCo;
	
	/** The bw. */
	private BufferedWriter bw;
	
	/** The description file. */
	private String descriptionFile;
	
	/** The output. */
	private File output;
	
	/** The h control. */
	private HashSet<String> hControl;
	
	/** The concept file. */
	private String conceptFile;
	
	/** The stated file. */
	private String statedFile;
	
	/** The output file name. */
	private String outputFileName;
	
	/** The rels cardinality control. */
	private String relsCardinalityControl;
	
	/** The concept terms. */
	private HashMap<Long, String> conceptTerms;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		//		RelationshipTests rt=new RelationshipTests("/Users/ar/Downloads/Crossover");
		//		try {
		//			rt.searchCrossover("/Users/ar/Downloads/RF2_Core_Release_20150731_Beta_version_4/Snapshot/sct2_Relationship_Snapshot_INT_20150731.txt"
		//					, "/Users/ar/Downloads/RF2_Core_Release_20150731_Beta_version_4/Snapshot/sct2_Description_Snapshot-en_INT_20150731.txt",
		//					"/Users/ar/Downloads/RF2_Core_Release_20150731_Beta_version_4/Snapshot/sct2_StatedRelationship_Snapshot_INT_20150731.txt",
		//					"/Users/ar/Downloads/RF2_Core_Release_20150731_Beta_version_4/Snapshot/sct2_Concept_Snapshot_INT_20150731.txt");

		//			rt.searchRedundance("/Users/ar/Downloads/xsct2_StatedRelationship_SRSISnapshot_INT_20150831.txt"
		//					,"/Users/ar/Downloads/xsct2_Relationship_SRSISnapshot_INT_20150831.txt"
		//					, "/Users/ar/Downloads/Snapshot_20160131/Snapshot/sct2_Description_Snapshot-en_INT_20160131.txt"
		//					,true);

		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		rt=null;
	}
	
	/**
	 * Instantiates a new relationship tests.
	 *
	 * @param outputFolder the output folder
	 */
	public RelationshipTests(String outputFolder){
		this.outputFolder=outputFolder;
		createFolders();
	}
	
	/**
	 * Creates the folders.
	 */
	private void createFolders() {

		sortFolderTmp=new File(outputFolder, "sortFolderTmp");
		if (!sortFolderTmp.exists()){
			sortFolderTmp.mkdirs();
		}else{
			FileHelper.emptyFolder(sortFolderTmp);
		}
		sortedFolderTmp=new File(outputFolder, "sortedFolderTmp");
		if (!sortedFolderTmp.exists()){
			sortedFolderTmp.mkdirs();
		}else{
			FileHelper.emptyFolder(sortedFolderTmp);
		}
	}
	
	/**
	 * Gets the writer.
	 *
	 * @param outFile the out file
	 * @return the writer
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 */
	private BufferedWriter getWriter(String outFile) throws UnsupportedEncodingException, FileNotFoundException {

		FileOutputStream tfos = new FileOutputStream( outFile);
		OutputStreamWriter tfosw = new OutputStreamWriter(tfos,"UTF-8");
		return new BufferedWriter(tfosw);

	}

	/**
	 * Search redundance.
	 *
	 * @param relationshipFile the relationship file
	 * @param relationship4TC the relationship4 tc
	 * @param conceptFile the concept file
	 * @param descriptionFile the description file
	 * @param onIsas the on isas
	 * @param relsCardinalityControl the rels cardinality control
	 * @param outputFileName the output file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void searchRedundance( String relationshipFile,String relationship4TC, String conceptFile,String descriptionFile,boolean onIsas,String relsCardinalityControl,String outputFileName)throws IOException {
		this.conceptFile=conceptFile;
		this.descriptionFile=descriptionFile;
		this.conceptTerms=null;
		this.statedFile=relationshipFile;
		if (relationship4TC!=null && !relationship4TC.equals("")){
			tClos = new TClosure(relationship4TC,4,5,7,2);
		}else{
			tClos = new TClosure(relationshipFile,4,5,7,2);
		}
		this.relsCardinalityControl=relsCardinalityControl;
		if (relsCardinalityControl==null && onIsas){
			this.relsCardinalityControl=UNGROUPED2UNGROUPED;
		}
		tCo=new TestCrossover(tClos);
		String controlType;
		this.outputFileName=outputFileName;
		output=new File(outputFolder,"tmp_" + outputFileName);
		if (onIsas){
			controlType=ISA_REDUNDANCE;
		}else{
			controlType=ROLE_REDUNDANCE;
		}
		bw=getWriter(output.getAbsolutePath());

		bw.append("relationshipId	sourceId	destinationId	typeId	group");
		bw.append("\r\n");
		cRelationships=new ArrayList<Relationship>();

		processRelationship(relationshipFile, controlType);

		cRelationships=null;
		createFolders();

	}

	/**
	 * Search redundance.
	 *
	 * @param relationshipFile the relationship file
	 * @param relationship4TC the relationship4 tc
	 * @param conceptFile the concept file
	 * @param conceptTerms the concept terms
	 * @param onIsas the on isas
	 * @param relsCardinalityControl the rels cardinality control
	 * @param outputFileName the output file name
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void searchRedundance(String relationshipFile,String relationship4TC, String conceptFile,HashMap<Long, String> conceptTerms,boolean onIsas,String relsCardinalityControl,String outputFileName) throws FileNotFoundException, IOException{

		this.conceptFile=conceptFile;
		this.descriptionFile=null;
		this.conceptTerms=conceptTerms;
		this.statedFile=relationshipFile;
		if (relationship4TC!=null && !relationship4TC.equals("")){
			tClos = new TClosure(relationship4TC,4,5,7,2);
		}else{
			tClos = new TClosure(relationshipFile,4,5,7,2);
		}
		this.relsCardinalityControl=relsCardinalityControl;
		if (relsCardinalityControl==null && onIsas){
			this.relsCardinalityControl=UNGROUPED2UNGROUPED;
		}
		tCo=new TestCrossover(tClos);
		String controlType;
		this.outputFileName=outputFileName;
		output=new File(outputFolder,"tmp_" + outputFileName);
		if (onIsas){
			controlType=ISA_REDUNDANCE;
		}else{
			controlType=ROLE_REDUNDANCE;
		}
		bw=getWriter(output.getAbsolutePath());

		bw.append("relationshipId	sourceId	destinationId	typeId	group");
		bw.append("\r\n");
		cRelationships=new ArrayList<Relationship>();

		processRelationship(relationshipFile, controlType);

		cRelationships=null;
		createFolders();		
	}
	
	/**
	 * Process relationship.
	 *
	 * @param relationshipFile the relationship file
	 * @param controlType the control type
	 * @throws FileNotFoundException the file not found exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws NumberFormatException the number format exception
	 */
	private void processRelationship(String relationshipFile, String controlType)
			throws FileNotFoundException, UnsupportedEncodingException,
			IOException, NumberFormatException {
		String line;
		String[] spl;
		File relFile=new File(relationshipFile);
		File sortedFile = new File(sortedFolderTmp, "Sorted" + relFile.getName());
		FileSorter fsc = new FileSorter(relFile, sortedFile, sortFolderTmp, new int[]{4});
		fsc.execute();
		fsc=null;

		FileInputStream rfis = new FileInputStream(sortedFile);
		InputStreamReader risr = new InputStreamReader(rfis,"UTF-8");
		BufferedReader rbr = new BufferedReader(risr);
		rbr.readLine();
		String prevSourceId="";
		boolean bFirst=true;
		while((line=rbr.readLine())!=null){
			spl=line.split("\t",-1);
			if (bFirst){
				prevSourceId=spl[4];
				bFirst=false;
			}
			if (!spl[4].equals(prevSourceId)){
				RelationshipGroupList groups = getGroups(cRelationships);
				//				if (spl[4].equals("2560006")){
				//					boolean stop=true;
				//				}
				if (groups.size()>1){
					testGroups(groups,controlType);
				}
				cRelationships=new ArrayList<Relationship>();
				prevSourceId=spl[4];
			}
			if (spl[2].equals("1") 
					&& (spl[8].equals(I_Constants.INFERRED)
							|| spl[8].equals(I_Constants.STATED ))){
				boolean valid=false;
				if (controlType.equals(ISA_REDUNDANCE)){
					if( spl[7].equals(I_Constants.ISA) ){
						valid=true;
					}
				}else if (!spl[7].equals(I_Constants.ISA)){
					valid=true;
				}
				if (valid){
					Long c1 = Long.parseLong(spl[4]);
					Long c2 = Long.parseLong(spl[5]);
					Integer rg = Integer.parseInt(spl[6]);
					Long ty = Long.parseLong(spl[7]);

					Relationship rel = new Relationship(c1, c2, ty, rg, spl[0]);
					cRelationships.add(rel);
				}
			}
		}
		rbr.close();
		rbr=null;

		RelationshipGroupList groups = getGroups(cRelationships);
		testGroups(groups, controlType);

		bw.close();
		if ((descriptionFile!=null && !descriptionFile.equals("")) || (conceptTerms!=null && conceptTerms.size()>0)){
			AddTermToReport();
		}else{
			output.renameTo(new File(outputFolder, outputFileName));
		}
	}
	
	/**
	 * Search crossover.
	 *
	 * @param relationshipFile the relationship file
	 * @param descriptionFile the description file
	 * @param statedFile the stated file
	 * @param relationship4TC the relationship4 tc
	 * @param conceptFile the concept file
	 * @param relsCardinalityControl the rels cardinality control
	 * @param outputFileName the output file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void searchCrossover( String relationshipFile,String descriptionFile, String statedFile,String relationship4TC, String conceptFile,String relsCardinalityControl, String outputFileName)throws IOException {
		this.descriptionFile=descriptionFile;
		this.conceptTerms=null;
		this.conceptFile=conceptFile;
		this.statedFile=statedFile;
		this.relsCardinalityControl=relsCardinalityControl;
		if (relationship4TC!=null && !relationship4TC.equals("")){
			tClos = new TClosure(relationship4TC,4,5,7,2);
		}else{
			tClos = new TClosure(relationshipFile,4,5,7,2);
		}

		tCo=new TestCrossover(tClos);
		this.outputFileName= outputFileName;
		output=new File(outputFolder,"tmp_" + outputFileName);

		bw=getWriter(output.getAbsolutePath());

		bw.append("relationshipId	sourceId	destiantionId	typeId	group");
		bw.append("\r\n");
		cRelationships=new ArrayList<Relationship>();

		processRelationship(relationshipFile,CROSSOVER);

		cRelationships=null;
		tClos=null;


	}

	/**
	 * Search crossover.
	 *
	 * @param inferRels the infer rels
	 * @param conceptTerms the concept terms
	 * @param statedRels the stated rels
	 * @param relationship4TC the relationship4 tc
	 * @param conceptFile the concept file
	 * @param relsCardinalityControl the rels cardinality control
	 * @param outputFileName the output file name
	 * @throws NumberFormatException the number format exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void searchCrossover(String inferRels,
			HashMap<Long, String> conceptTerms, String statedRels,
			String relationship4TC, String conceptFile, String relsCardinalityControl,
			String outputFileName) throws NumberFormatException, FileNotFoundException, UnsupportedEncodingException, IOException {
		this.descriptionFile=null;
		this.conceptTerms=conceptTerms;
		this.conceptFile=conceptFile;
		this.statedFile=statedRels;
		this.relsCardinalityControl=relsCardinalityControl;
		if (relationship4TC!=null && !relationship4TC.equals("")){
			tClos = new TClosure(relationship4TC,4,5,7,2);
		}else{
			tClos = new TClosure(inferRels,4,5,7,2);
		}

		tCo=new TestCrossover(tClos);
		this.outputFileName= outputFileName;
		output=new File(outputFolder,"tmp_" + outputFileName);

		bw=getWriter(output.getAbsolutePath());

		bw.append("relationshipId	sourceId	destiantionId	typeId	group");
		bw.append("\r\n");
		cRelationships=new ArrayList<Relationship>();

		processRelationship(inferRels,CROSSOVER);

		cRelationships=null;
		tClos=null;

	}
	
	/**
	 * Gets the reader.
	 *
	 * @param inFile the in file
	 * @return the reader
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 */
	private BufferedReader getReader(String inFile) throws UnsupportedEncodingException, FileNotFoundException {

		FileInputStream rfis = new FileInputStream(inFile);
		InputStreamReader risr = new InputStreamReader(rfis,"UTF-8");
		BufferedReader rbr = new BufferedReader(risr);
		return rbr;

	}
	
	/**
	 * Adds the term to report.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void AddTermToReport() throws IOException {
		BufferedReader br=null;
		String[] spl;
		String line;
		if (descriptionFile!=null){
			br =getReader(descriptionFile);

			br.readLine();
			while((line=br.readLine())!=null){
				spl=line.split("\t",-1);
				if (spl[2].equals("1") 
						&& spl[6].equals(I_Constants.FSN)){

					conceptTerms.put(Long.parseLong(spl[4]),spl[7]);
				}

			}
			br.close();
		}
		br =getReader(conceptFile);
		HashSet<Long> cptDef=new HashSet<Long>();

		br.readLine();
		while((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[4].equals("900000000000073002") ){

				cptDef.add(Long.parseLong(spl[0]));
			}

		}
		br.close();


		br =getReader(statedFile);
		HashSet<Long> statedAttr=new HashSet<Long>();
		HashSet<Long> statedParP=new HashSet<Long>();

		br.readLine();
		while((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1")  ){
				if (! spl[7].equals("116680003")){
					if (!statedAttr.contains(Long.parseLong(spl[4]))){
						statedAttr.add(Long.parseLong(spl[4]));
					}
				}else if (!cptDef.contains(Long.parseLong(spl[5]) )){
					if (!statedParP.contains(Long.parseLong(spl[4]))){
						statedParP.add(Long.parseLong(spl[4]));
					}
				}
			}

		}
		br.close();
		br=getReader(output.getAbsolutePath());
		br.readLine();
		File outputWithTerm=new File(outputFolder, outputFileName);

		bw=getWriter(outputWithTerm.getAbsolutePath());

		bw.append("relationshipId	sourceId	sourceTerm	destinationId	destinationTerm	typeId	typeTerm	group	definitionStatus	statedAttributes	allStatedParentsSD	inferredDescendants");
		bw.append("\r\n");
		while((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if(spl.length==5){
				Long cpt=Long.parseLong(spl[1]);
				String defStat=cptDef.contains(cpt)? "SD":"Primitive";
				String statAtt=statedAttr.contains(cpt)? "Y":"N";
				String statParSD=statedParP.contains(cpt)? "N":"Y";
				String infDesc=tClos.getChildren(cpt)!=null? "Y":"N";
				bw.append(spl[0]);
				bw.append("\t");
				bw.append(spl[1]);
				bw.append("\t");
				bw.append(conceptTerms.get(Long.parseLong(spl[1])));
				bw.append("\t");
				bw.append(spl[2]);
				bw.append("\t");
				bw.append(conceptTerms.get(Long.parseLong(spl[2])));
				bw.append("\t");
				bw.append(spl[3]);
				bw.append("\t");
				bw.append(conceptTerms.get(Long.parseLong(spl[3])));
				bw.append("\t");
				bw.append(spl[4]);
				bw.append("\t");
				bw.append(defStat);
				bw.append("\t");
				bw.append(statAtt);
				bw.append("\t");
				bw.append(statParSD);
				bw.append("\t");
				bw.append(infDesc);
				bw.append("\r\n");
			}else{
				bw.append(line);
				bw.append("\r\n");
			}
		}
		br.close();
		bw.close();
		statedAttr=null;
		statedParP=null;
		cptDef=null;

	}
	
	/**
	 * Test groups.
	 *
	 * @param groups the groups
	 * @param controlType the control type
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void testGroups(RelationshipGroupList groups, String controlType) throws IOException {
		hControl=new HashSet<String>();
		String key1;
		String key2;
		for (int i=0;i<groups.size();i++){
			for (int j=0;j<groups.size();j++){
				if (i==j){
					continue;
				}
				RelationshipGroup relgroup1 = groups.get(i);
				RelationshipGroup relgroup2 = groups.get(j);
				if (relsCardinalityControl!=null){
					if (relsCardinalityControl.equals(UNGROUPED2UNGROUPED)){
						if (relgroup1.size()>1 ){
							break;
						}else if( relgroup2.size()>1){
							continue;
						}
					}
					else if (relsCardinalityControl.equals(UNGROUPED2GROUPED)){
						
						if (relgroup1.size()>1 && relgroup2.size()>1){
							
							continue;
						}else if (relgroup1.size()==1 && relgroup2.size()==1){
							
							continue;
						}
						
					}else if (relsCardinalityControl.equals(GROUPED2GROUPED)){
						if (relgroup1.size()==1 ){
							break;
						}else if (relgroup2.size()==1 ){
							continue;
						}
					}
				}
				if (relgroup1.size()>1){
					key1=String.valueOf(i);
				}else{
					key1=relgroup1.get(0).getRelId();
				}
				if (relgroup2.size()>1){
					key2=String.valueOf(j);
				}else{
					key2=relgroup2.get(0).getRelId();
				}
				if ((controlType.equals(ISA_REDUNDANCE) || controlType.equals(ROLE_REDUNDANCE)) && !added(key1,key2) && tCo.isRedundant(relgroup1, relgroup2)){
					hControl.add(key1 + "-" + key2);
					addToReport(relgroup1, relgroup2);
				}else if (controlType.equals(CROSSOVER) && !added(key1, key2) && tCo.isCrossover(relgroup1, relgroup2)){
					hControl.add(key1 + "-" + key2);
					addToReport(relgroup1, relgroup2);
				}
			}

		}
	}
	
	/**
	 * Added.
	 *
	 * @param i the i
	 * @param j the j
	 * @return true, if successful
	 */
	private boolean added(String i, String j) {
		String key=i + "-" + j;
		String revKey=j + "-" + i;
		if (hControl.contains(revKey) || hControl.contains(key)){
			return true;
		}
		return false;
	}
	
	/**
	 * Adds the to report.
	 *
	 * @param relgroup1 the relgroup1
	 * @param relgroup2 the relgroup2
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void addToReport(RelationshipGroup relgroup1, RelationshipGroup relgroup2) throws IOException {
		bw.append(relgroup1.toString4File());
		bw.append("\r\n");
		bw.append(relgroup2.toString4File());
		bw.append("-----------------------------------------------------------------------------------------------------------");
		bw.append("\r\n");

	}
	
	/**
	 * Gets the groups.
	 *
	 * @param snorelA the snorel a
	 * @return the groups
	 */
	private RelationshipGroupList getGroups(List<Relationship> snorelA){
		RelationshipGroupList groupList_A = new RelationshipGroupList();
		if (snorelA.size()>1){
			Collections.sort(snorelA);
			Iterator<Relationship> itA = snorelA.iterator();
			RelationshipGroup groupA = null;
			Relationship rel_A = null;
			boolean done_A = false;
			if (itA.hasNext()) {
				rel_A = itA.next();
			} else {
				done_A = true;
			}
			while ( rel_A.group == 0 && !done_A  ) {
				groupA = new RelationshipGroup();
				groupA.add(rel_A);
				groupList_A.add(groupA);
				if (itA.hasNext()) {
					rel_A = itA.next();
				} else {
					done_A = true;
				}
			}

			int prevGroup = Integer.MIN_VALUE;
			while (!done_A) {
				if (rel_A.group != prevGroup) {
					groupA = new RelationshipGroup();
					groupList_A.add(groupA);
				}

				groupA.add(rel_A);

				prevGroup = rel_A.group;
				if (itA.hasNext()) {
					rel_A = itA.next();
				} else {
					done_A = true;
				}
			}
		}
		return groupList_A;
	}
}
