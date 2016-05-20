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
package com.termmed.control.concept;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.termmed.fileprovider.CurrentFile;
import com.termmed.fileprovider.PreviousFile;
import com.termmed.utils.FileHelper;
import com.termmed.utils.I_Constants;
import com.termmed.utils.TClosure;

// TODO: Auto-generated Javadoc
/**
 * The Class TestConcepts.
 */
public class TestConcepts {

	/** The output folder. */
	private File outputFolder;

	/**
	 * Instantiates a new test concepts.
	 *
	 * @param outputFolder the output folder
	 */
	public TestConcepts(File outputFolder) {
		this.outputFolder=outputFolder;
	}

	/**
	 * Gets the intermediate primitive.
	 *
	 * @param concFile the conc file
	 * @param completeConcFile the complete conc file
	 * @param currFile the curr file
	 * @param tClos_file the t clos_file
	 * @return the intermediate primitive
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws NumberFormatException the number format exception
	 */
	public void getIntermediatePrimitive( String concFile,
			String completeConcFile, String currFile, String tClos_file) throws FileNotFoundException, IOException,
			UnsupportedEncodingException, NumberFormatException {

		if (completeConcFile==null){
			completeConcFile=concFile;
		}
		BufferedReader br = FileHelper.getReader(completeConcFile);

		br.readLine();
		String[] spl;
		String line;

		HashSet<Long>SDconcepts=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[4].equals("900000000000073002" )){
				SDconcepts.add(Long.parseLong(spl[0]));
			}
		}
		br.close();

		br = FileHelper.getReader(concFile);

		br.readLine();

		HashSet<Long>Pconcepts=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[4].equals("900000000000074008" )){
				Pconcepts.add(Long.parseLong(spl[0]));
			}
		}
		br.close();

		br = FileHelper.getReader(tClos_file);
		br.readLine();
		Long desc;
		Long asc;
		HashSet<Long> checkAsDesc = new HashSet<Long>();
		HashSet<Long> checkAsPar = new HashSet<Long>();
		HashSet<Long> intermPrim=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			desc=Long.parseLong(spl[0]);
			asc=Long.parseLong(spl[1]);
			if (Pconcepts.contains(desc) && SDconcepts.contains(asc)){
				if (checkAsDesc.contains(desc)){
					intermPrim.add(desc);
				}else{
					checkAsPar.add(desc);
				}
			}else if(Pconcepts.contains(asc) && SDconcepts.contains(desc)){
				if (checkAsPar.contains(asc)){
					intermPrim.add(asc);
				}else{
					checkAsDesc.add(asc);
				}
			}
		}
		br.close();
		Pconcepts=null;
		SDconcepts=null;
		checkAsDesc=null;
		checkAsPar=null;

		BufferedWriter bw = FileHelper.getWriter(new File(outputFolder,currFile));
		bw.append("id");
		bw.append("\r\n");

		for (Long key:intermPrim){

			bw.append(key.toString());
			bw.append("\r\n");
		}

		bw.close();

		tClos_file=null;

	}

	/**
	 * Gets the proximal primitive.
	 *
	 * @param concFile the conc file
	 * @param completeConcFile the complete conc file
	 * @param currFile the curr file
	 * @param tClos the t clos
	 * @return the proximal primitive
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws NumberFormatException the number format exception
	 */
	public void getProximalPrimitive( String concFile,
			String completeConcFile, File currFile, TClosure tClos) throws FileNotFoundException, IOException,
			UnsupportedEncodingException, NumberFormatException {

		if (completeConcFile==null){
			completeConcFile=concFile;
		}
		BufferedReader br = FileHelper.getReader(concFile);

		br.readLine();
		String[] spl;
		String line;

		HashSet<Long>SDconcepts=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[4].equals("900000000000073002" )){
				SDconcepts.add(Long.parseLong(spl[0]));
			}
		}
		br.close();

		br = FileHelper.getReader(completeConcFile);

		br.readLine();

		HashSet<Long>Pconcepts=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[4].equals("900000000000074008" )){
				Pconcepts.add(Long.parseLong(spl[0]));
			}
		}
		br.close();


		tClos.getProximalPrimitives(SDconcepts,Pconcepts,currFile);

		return;


	}

	/**
	 * Gets the concept gained primitive intermediate parent.
	 *
	 * @param concFile the conc file
	 * @param currFile the curr file
	 * @param prevTClos the prev t clos
	 * @param tClos the t clos
	 * @param gainPrimFile the gain prim file
	 * @return the concept gained primitive intermediate parent
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void getConceptGainedPrimitiveIntermediateParent(String prevConcFile,String concFile,
			String currFile, TClosure prevTClos, TClosure tClos,
			String gainPrimFile) throws IOException {

		BufferedReader br=FileHelper.getReader(prevConcFile);
		br.readLine();
		String line;
		String[] spl;
		HashSet<Long>prevSDConc=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[4].equals("900000000000073002")){
				prevSDConc.add(Long.parseLong(spl[0]));
			}
		}
		br.close();

		br=FileHelper.getReader(currFile);
		br.readLine();
		HashSet<Long> curr=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			curr.add(Long.parseLong(spl[0]));
		}
		br.close();

		BufferedWriter bw = FileHelper.getWriter(new File(outputFolder,gainPrimFile));
		bw.append("id");
		bw.append("\r\n");

		br=FileHelper.getReader(concFile);
		br.readLine();
		while ((line=br.readLine())!=null){

			spl=line.split("\t",-1);
			if ( spl[2].equals("1") && spl[4].equals("900000000000073002")){

				Long cid=Long.parseLong(spl[0]);
				if (prevSDConc.contains(cid)){
					for (Long intPrim: curr){
						Boolean isParent=tClos.isParent(intPrim,cid);
						if (isParent!=null && isParent){
							isParent=prevTClos.isParent(intPrim,cid);
							if (isParent!=null && isParent==false){
								bw.append(cid.toString());
								bw.append("\r\n");

							}
						}
					}
				}
			}
		}
		br.close();
		bw.close();
	}

	/**
	 * Gets the sD concepts without stated att.
	 *
	 * @param rels the rels
	 * @param concept the concept
	 * @param outputFile the output file
	 * @return the sD concepts without stated att
	 * @throws NumberFormatException the number format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void getSDConceptsWithoutStatedAtt(String rels,
			String concept, String outputFile) throws NumberFormatException, IOException {


		BufferedReader br=FileHelper.getReader(rels);
		br.readLine();
		String line;
		String[] spl;
		HashSet<Long> curr=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("0") || spl[7].equals(I_Constants.ISA)){
				continue;
			}
			curr.add(Long.parseLong(spl[4]));
		}
		br.close();

		BufferedWriter bw = FileHelper.getWriter(new File(outputFolder,outputFile));
		bw.append("id");
		bw.append("\r\n");

		br=FileHelper.getReader(concept);
		br.readLine();
		while ((line=br.readLine())!=null){

			spl=line.split("\t",-1);
			if (spl[4].equals(I_Constants.SUFFICIENTLY_DEFINED) && !curr.contains(Long.parseLong(spl[0]))){
				bw.append(spl[0]);
				bw.append("\r\n");
			}
		}
		bw.close();
		br.close();
	}

	/**
	 * Gets the concepts with entire in fsn.
	 *
	 * @param descriptionFile the description file
	 * @param tClos_file the t clos_file
	 * @return the concepts with entire in fsn
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HashSet<Long> getConceptsWithEntireInFSN(String descriptionFile, String tClos_file) throws IOException {
		HashSet<Long> cpts=new HashSet<Long>();
		HashSet<Long> bscpts=new HashSet<Long>();
		HashSet<Long> excpts=new HashSet<Long>();

		BufferedReader br=FileHelper.getReader(tClos_file);
		br.readLine();
		String line;
		String[] spl;
		Long desc;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			desc=Long.parseLong(spl[0]);

			if (spl[1].equals("91723000") ){
				bscpts.add(desc);
			}else if (spl[1].equals("4421005") ){
				excpts.add(desc);
			}
		}
		br.close();
		br=FileHelper.getReader(descriptionFile);
		br.readLine();
		Long cid;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			cid=Long.parseLong(spl[4]);
			if (bscpts.contains(cid) && !excpts.contains(cid) && spl[2].equals("1") && spl[6].equals("900000000000003001") && spl[7].toLowerCase().contains("entire")){
				cpts.add(Long.parseLong(spl[4]));
			}
		}
		br.close();
		br=null;
		bscpts=null;
		excpts=null;
		return cpts;
	}

	/**
	 * Gets the cpts without entire.
	 *
	 * @param entireCpt the entire cpt
	 * @param tClos_file the t clos_file
	 * @return the cpts without entire
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HashSet<Long> getCptsWithoutEntire(HashSet<Long> entireCpt,
			String tClos_file) throws IOException {
		HashSet<Long> cpts=new HashSet<Long>();

		BufferedReader br=FileHelper.getReader(tClos_file);
		br.readLine();
		String line;
		String[] spl;
		Long desc;
		Long asc;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			desc=Long.parseLong(spl[0]);
			asc=Long.parseLong(spl[1]);

			if (entireCpt.contains(asc) & !entireCpt.contains(desc)){
				cpts.add(desc);
			}
		}
		br.close();
		br=null;
		return cpts;
	}

	/**
	 * Gets the new concept previous and inactive current.
	 *
	 * @param fullCpts the full cpts
	 * @param resultTmpFolder the result tmp folder
	 * @param releaseDate the release date
	 * @param previousReleaseDate the previous release date
	 * @return the new concept previous and inactive current
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HashSet<Long> getNewConceptPreviousAndInactiveCurrent(
			String fullCpts, 
			File resultTmpFolder, 
			String releaseDate,
			String previousReleaseDate) throws IOException {

		HashSet<Long> concepts=new HashSet<Long>();

		File sortedFile = FileHelper.getSortedFile(new File(fullCpts), this.outputFolder, resultTmpFolder, new int[]{0,1});
		BufferedReader br=FileHelper.getReader(sortedFile);
		br.readLine();
		String line;
		String[] spl;
		String prevCid="";
		boolean isNew=false;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (!spl[0].equals(prevCid)){
				if (spl[1].equals(previousReleaseDate) && spl[2].equals("1")){
					isNew=true;
				}else{
					isNew=false;
				}
				prevCid=spl[0];
			}else{
				if (isNew){
					if (spl[1].equals(releaseDate) && spl[2].equals("0")){
						concepts.add(Long.parseLong(spl[0]));
					}
				}
			}
		}
		br.close();

		return concepts;
	}

	/**
	 * Filter concepts by reason.
	 *
	 * @param cpts the cpts
	 * @param attvalue the attvalue
	 * @param reason the reason
	 * @return the hash set
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HashSet<Long> filterConceptsByReason(HashSet<Long> cpts,
			String attvalue, String reason) throws IOException {
		HashSet<Long> concepts=new HashSet<Long>();

		BufferedReader br=FileHelper.getReader(attvalue);
		br.readLine();
		String line;
		String[] spl;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[4].equals(I_Constants.INACTIVATION_CONCEPT_REFSETID)
					&& spl[2].equals("1")
					&& cpts.contains(Long.parseLong(spl[5]))
					&& spl[6].equals(reason)){
				concepts.add(Long.parseLong(spl[5]));
			}
		}
		br.close();
		return concepts;
	}

	/**
	 * Gets the canonical changes on sd concepts file.
	 *
	 * @return the canonical changes on sd concepts file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getCanonicalChangesOnSDConceptsFile() throws IOException, Exception{
		String ret=CurrentFile.get().getCanonicalChangesOnSDConceptsFile();
		if (ret!=null){
			return ret;
		}
		ret=outputFolder.getAbsolutePath() + "/" + "canonicalChangesOnSDConceptsFile.txt";
		String statedRels;
		String concFile;
		String completeConcFile=null;
		String currFile=null;
		String prevFile=null;
		currFile=CurrentFile.get().getProximalPrimitiveFile();
		if (currFile==null){
			File completedFilesFolder=CurrentFile.get().getCompletedFilesFolder();

			if (CurrentFile.get().getReleaseDependenciesFullFolders()!=null){

				statedRels=CurrentFile.get().getCompleteStatedRelationshipSnapshot();
				completeConcFile=CurrentFile.get().getCompleteConceptSnapshot();
			}else{
				statedRels=CurrentFile.get().getSnapshotStatedRelationshipFile();
			}
			concFile=CurrentFile.get().getSnapshotConceptFile();
			File outputFile=new File(completedFilesFolder,"current_prim_prox.txt");

			TClosure tClos=new TClosure(statedRels,4,5,7,2);

			getProximalPrimitive(concFile, completeConcFile, outputFile, tClos);
			currFile=outputFile.getAbsolutePath();
			CurrentFile.get().setProximalPrimitiveFile(currFile);


		}
		prevFile=PreviousFile.get().getProximalPrimitiveFile();
		if (prevFile==null){
			File completedFilesFolder=PreviousFile.get().getCompletedFilesFolder();
			completeConcFile=null;

			if (PreviousFile.get().getReleaseDependenciesFullFolders()!=null){
				completeConcFile=PreviousFile.get().getCompleteConceptSnapshot();	
				statedRels=PreviousFile.get().getCompleteStatedRelationshipSnapshot();
			}else{
				statedRels=PreviousFile.get().getSnapshotStatedRelationshipFile();
			}
			concFile=PreviousFile.get().getSnapshotConceptFile();
			File outputFile=new File(completedFilesFolder,"previous_prim_prox.txt");

			TClosure tClos=new TClosure(statedRels,4,5,7,2);

			getProximalPrimitive(concFile, completeConcFile, outputFile, tClos);

			prevFile=outputFile.getAbsolutePath();
			PreviousFile.get().setProximalPrimitiveFile(prevFile);
		}

		HashMap<String,Integer>preInf=new HashMap<String,Integer>();
		BufferedReader br = FileHelper.getReader(prevFile);
		br.readLine();
		String line;
		String[] spl;

		HashMap<Long,Integer> sdConcepts=new HashMap<Long,Integer>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			preInf.put(spl[0] + "-i:" + spl[1],0);
			sdConcepts.put(Long.parseLong(spl[0]),0);
		}
		br.close();

		br = FileHelper.getReader(PreviousFile.get().getSnapshotRelationshipFile());
		br.readLine();

		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);

			if (sdConcepts.containsKey(Long.parseLong(spl[4])) 
					&& spl[2].equals("1") && spl[7].compareTo(I_Constants.ISA)!=0){

				String key=spl[4]+ "-" + spl[7] + ":" + spl[5];
				if (preInf.containsKey(key)){
					Integer count=preInf.get(key);
					count++;
					preInf.put(key,count);
				}else{
					preInf.put(key,0);
				}
			}
		}
		br.close();

		HashSet<Long> modified=new HashSet<Long>();
		br = FileHelper.getReader(currFile);

		br.readLine();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			Long cid=Long.parseLong(spl[0]);
			if (sdConcepts.containsKey(cid)){
				String key=spl[0]+ "-i:" + spl[1] ;

				if (!preInf.containsKey(key)){
					sdConcepts.remove(cid);
					modified.add(cid);
				}else{
					preInf.remove(key);
					sdConcepts.put(cid, 1);
				}
			}
		}
		br.close();

		br = FileHelper.getReader(CurrentFile.get().getSnapshotRelationshipFile());

		br.readLine();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			Long cid=Long.parseLong(spl[4]);
			if (sdConcepts.containsKey(cid) && sdConcepts.get(cid).equals(1)
					&& spl[2].equals("1") && spl[7].compareTo(I_Constants.ISA)!=0){
				String key=spl[4]+ "-" + spl[7] + ":" + spl[5];
				if (!preInf.containsKey(key)){
					sdConcepts.remove(cid);
					modified.add(cid);
				}else{
					Integer count=preInf.get(key);
					if (count.compareTo(0)>0){
						count--;
						preInf.put(key, count);
					}else{
						preInf.remove(key);
					}
				}
			}
		}
		br.close();

		for (String key:preInf.keySet()){
			String[] splKey=key.split("-");
			Long cid=Long.parseLong(splKey[0]);
			if (!modified.contains(cid)){
				Integer viewed=sdConcepts.get(cid);
				if (viewed.equals(1)){
					modified.add(cid);
				}
			}
		}

		BufferedWriter bw = FileHelper.getWriter(ret);
		bw.append("id");
		bw.append("\r\n");
		for (Long cid:modified){
			bw.append(cid.toString());
			bw.append("\r\n");

		}
		bw.close();
		CurrentFile.get().setCanonicalChangesOnSDConceptsFile(ret);
		return ret;
	}

	/**
	 * Gets the stated changes on sd concepts file.
	 *
	 * @param releaseDate the release date
	 * @return the stated changes on sd concepts file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getStatedChangesOnSDConceptsFile(String releaseDate) throws IOException, Exception{
		String ret=outputFolder.getAbsolutePath() + "/" + "statedChangesOnSDConceptsFile.txt";
		String statedRels;
		String concFile;

		concFile=PreviousFile.get().getSnapshotConceptFile();

		BufferedReader br = FileHelper.getReader(concFile);

		br.readLine();
		String[] spl;
		String line;

		HashSet<Long>SDconcepts=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") && spl[4].equals("900000000000073002" )){
				SDconcepts.add(Long.parseLong(spl[0]));
			}
		}
		br.close();

		concFile=CurrentFile.get().getSnapshotConceptFile();

		br = FileHelper.getReader(concFile);

		br.readLine();

		HashSet<Long>currSDconcepts=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			Long cid=Long.parseLong(spl[0]);
			if (spl[2].equals("1") && spl[4].equals("900000000000073002" )
					&& SDconcepts.contains(cid)){
				currSDconcepts.add(cid);
			}
		}
		br.close();


		statedRels=CurrentFile.get().getSnapshotStatedRelationshipFile();

		br = FileHelper.getReader(statedRels);
		br.readLine();

		BufferedWriter bw = FileHelper.getWriter(ret);
		bw.append("id");
		bw.append("\r\n");
		HashSet<Long> control = new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			Long cid=Long.parseLong(spl[4]);
			if (spl[1].compareTo(releaseDate)==0 && currSDconcepts.contains(cid) && !control.contains(cid)){
				control.add(cid);
				bw.append(cid.toString());
				bw.append("\r\n");
			}
		}
		br.close();
		bw.close();
		return ret;
	}

	/**
	 * Gets the sD concepts with same canonical as parent file.
	 *
	 * @return the sD concepts with same canonical as parent file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSDConceptsWithSameCanonicalAsParentFile() throws IOException, Exception{
		String ret=outputFolder.getAbsolutePath() + "/" + "SDConceptsWithSameCanonicalAsParentFile.txt";
		String statedRels;
		String concFile;
		String completeConcFile=null;
		String currFile=null;
		currFile=CurrentFile.get().getProximalPrimitiveFile();
		if (currFile==null){
			File completedFilesFolder=CurrentFile.get().getCompletedFilesFolder();

			if (CurrentFile.get().getReleaseDependenciesFullFolders()!=null){

				statedRels=CurrentFile.get().getCompleteStatedRelationshipSnapshot();
				completeConcFile=CurrentFile.get().getCompleteConceptSnapshot();
			}else{
				statedRels=CurrentFile.get().getSnapshotStatedRelationshipFile();
			}
			concFile=CurrentFile.get().getSnapshotConceptFile();
			File outputFile=new File(completedFilesFolder,"current_prim_prox.txt");

			TClosure tClos=new TClosure(statedRels,4,5,7,2);

			getProximalPrimitive(concFile, completeConcFile, outputFile, tClos);
			currFile=outputFile.getAbsolutePath();
			CurrentFile.get().setProximalPrimitiveFile(currFile);

		}

		HashMap<Long,List<Long>>canonical=new HashMap<Long,List<Long>>();
		BufferedReader br = FileHelper.getReader(currFile);
		br.readLine();
		String line;
		String[] spl;

		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			Long cid=Long.parseLong(spl[0]);
			List<Long> rels;
			if (canonical.containsKey(cid)){
				rels=canonical.get(cid);
			}else{
				rels=new ArrayList<Long>();
			}
			rels.add(Long.parseLong(spl[1]));
			canonical.put(cid,  rels);
		}
		br.close();

		br = FileHelper.getReader(CurrentFile.get().getSnapshotStatedRelationshipFile());
		br.readLine();

		HashMap<Long, List<Long>>candidate=new HashMap<Long, List<Long>>();
		HashSet<Long> parentToTest=new HashSet<Long>();
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			Long cid=Long.parseLong(spl[4]);
			if (canonical.containsKey(cid)
					&& spl[2].equals("1")  
					&& spl[7].compareTo(I_Constants.ISA)==0){
				Long tgt=Long.parseLong(spl[5]);

				if (canonical.containsKey(tgt)){
					if (identicalPrimitives(canonical.get(cid),canonical.get(tgt))){
						List<Long>tgts=candidate.get(cid);
						if (tgts==null){
							tgts=new ArrayList<Long>();
						}
						tgts.add(tgt);
						candidate.put(cid,tgts);
						parentToTest.add(tgt);
					}
				}else if (canonical.get(cid).size()==1){
					List<Long>tgts=candidate.get(cid);
					if (tgts==null){
						tgts=new ArrayList<Long>();
					}
					tgts.add(tgt);
					candidate.put(cid,tgts);
					parentToTest.add(tgt);
				}

			}
		}
		br.close();

		canonical=null;


		HashMap<Long, HashMap<Integer,TreeSet<String>>> rels = new HashMap<Long,HashMap<Integer,TreeSet<String>>>();
		br = FileHelper.getReader(CurrentFile.get().getSnapshotRelationshipFile());
		br.readLine();

		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			Long cid=Long.parseLong(spl[4]);
			if ((candidate.containsKey(cid) || parentToTest.contains(cid))
					&& spl[2].equals("1")  
					&& spl[7].compareTo(I_Constants.ISA)!=0){

				HashMap<Integer,TreeSet<String>> cptRels;
				if (rels.containsKey(cid)){
					cptRels=rels.get(cid);
					Integer groupNr=Integer.parseInt(spl[6]);
					TreeSet<String> group=cptRels.get(groupNr);
					if (group==null){
						group=new TreeSet<String>();
					}
					group.add(spl[7] + ":" + spl[5]);
					cptRels.put(groupNr, group);
				}else{
					cptRels=new HashMap<Integer,TreeSet<String>>();
					TreeSet<String> group=new TreeSet<String>();
					group.add(spl[7] + ":" + spl[5]);
					cptRels.put(Integer.parseInt(spl[6]), group);
				}
				rels.put(cid, cptRels);
			}
		}
		br.close();


		BufferedWriter bw = FileHelper.getWriter(ret);
		bw.append("id");
		bw.append("\t");
		bw.append("parentId");
		bw.append("\r\n");

		for (Long cid:candidate.keySet()){
			List<Long> parents=candidate.get(cid);

			HashMap<Integer, TreeSet<String>> cidRels = rels.get(cid);
			for(Long parent:parents){
				HashMap<Integer, TreeSet<String>> parentRels = rels.get(parent);

				if (!diffRels(cidRels,parentRels)){

					bw.append(cid.toString());
					bw.append("\t");
					bw.append(parent.toString());
					bw.append("\r\n");

				}
			}

		}
		bw.close();

		return ret;
	}

	/**
	 * Diff rels.
	 *
	 * @param cidRels the cid rels
	 * @param parentRels the parent rels
	 * @return true, if successful
	 */
	private boolean diffRels(HashMap<Integer, TreeSet<String>> cidRels,
			HashMap<Integer, TreeSet<String>> parentRels) {

		if (parentRels==null && cidRels==null){
			return false;
		}
		if (parentRels==null){
			return true;
		}
		if (cidRels==null){
			return true;
		}
		for (Integer cidGroup:cidRels.keySet()){
			TreeSet<String> cidMembers = cidRels.get(cidGroup);
			boolean equalGroup=false;
			for (Integer parentGroup:parentRels.keySet()){
				TreeSet<String> parentMembers = parentRels.get(parentGroup);

				if (cidMembers.size()!=parentMembers.size()){
					continue;
				}
				boolean equalTriple=true;
				Iterator<String> iter = cidMembers.iterator();

				while(iter.hasNext()){
					String cidRoleTgt=iter.next();
					String parentRoleTgt=parentMembers.iterator().next();
					equalTriple=cidRoleTgt.equals(parentRoleTgt);
					if (!equalTriple){
						break;
					}
				}
				if (equalTriple){
					equalGroup=true;
					break;
				}

			}
			if (!equalGroup){
				return true;
			}
		}
		return false;
	}

	/**
	 * Identical primitives.
	 *
	 * @param list the list
	 * @param list2 the list2
	 * @return true, if successful
	 */
	private boolean identicalPrimitives(List<Long> list, List<Long> list2) {
		if (list.size()!=list2.size()){
			return false;
		}
		for (Long prim : list){
			if (!list2.contains(prim)){
				return false;
			}
		}
		for (Long prim : list2){
			if (!list.contains(prim)){
				return false;
			}
		}
		return true;
	}
}
