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
import java.util.HashMap;
import java.util.HashSet;


// TODO: Auto-generated Javadoc
/**
 * The Class TClosure.
 */
public class TClosure {

	/** The parent hier. */
	HashMap<Long,HashSet<Long>>parentHier;

	/** The children hier. */
	HashMap<Long,HashSet<Long>>childrenHier;

	/** The isarelationshiptypeid. */
	private long ISARELATIONSHIPTYPEID=116680003l;

	/** The root concept. */
	private String ROOT_CONCEPT = "138875005";

	/** The rf2 rels. */
	String rf2Rels;

	/** The h control. */
	private HashSet<Long> hControl;

	/** The source index. */
	private int sourceIndex;

	/** The type index. */
	private int typeIndex;

	/** The destination index. */
	private int destinationIndex;

	/** The active index. */
	private Integer activeIndex;

	/**
	 * Instantiates a new t closure.
	 *
	 * @param rf2Rels the rf2 rels
	 * @param sourceIndex the source index
	 * @param destinationIndex the destination index
	 * @param typeIndex the type index
	 * @param activeIndex the active index
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public TClosure(String rf2Rels,int sourceIndex, int destinationIndex, int typeIndex,Integer activeIndex) throws FileNotFoundException, IOException{
		parentHier=new HashMap<Long,HashSet<Long>>();
		this.sourceIndex=sourceIndex;
		this.destinationIndex=destinationIndex;
		this.typeIndex=typeIndex;
		this.activeIndex=activeIndex;
		childrenHier=new HashMap<Long,HashSet<Long>>();
		this.rf2Rels=rf2Rels;
		loadIsas();
	}

	/**
	 * Load isas.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException the file not found exception
	 */
	private void loadIsas() throws IOException, FileNotFoundException {
		System.out.println("Starting Isas Relationships from: " + rf2Rels);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rf2Rels), "UTF8"));
		try {
			String line = br.readLine();
			line = br.readLine(); // Skip header
			int count = 0;
			while (line != null) {
				if (line.isEmpty()) {
					continue;
				}
				String[] columns = line.split("\\t");
				if (Long.parseLong(columns[typeIndex])==ISARELATIONSHIPTYPEID
						&& !columns[sourceIndex].equals(ROOT_CONCEPT)){
					if (activeIndex!=null){
						if (columns[activeIndex].compareTo("1")==0){
							addRel(Long.parseLong(columns[destinationIndex]),Long.parseLong(columns[sourceIndex]));
						}
					}else{
						addRel(Long.parseLong(columns[destinationIndex]),Long.parseLong(columns[sourceIndex]));
					}
					count++;
					if (count % 100000 == 0) {
						System.out.print(".");
					}
				}
				line = br.readLine();
			}
			System.out.println(".");
			System.out.println("Parent isas Relationships loaded = " + parentHier.size());
			System.out.println("Children isas Relationships loaded = " + childrenHier.size());
		} finally {
			br.close();
		}		
	}

	/**
	 * Adds the rel.
	 *
	 * @param parent the parent
	 * @param child the child
	 */
	public void addRel(Long parent, Long child){
		if (parent==child){
			System.out.println("same child and parent: " + child);
			return;
		}
		HashSet<Long> parentList=parentHier.get(child);
		if (parentList==null){
			parentList=new HashSet<Long>();
		}
		parentList.add(parent);
		parentHier.put(child, parentList);

		HashSet<Long> childrenList=childrenHier.get(parent);
		if (childrenList==null){
			childrenList=new HashSet<Long>();
		}
		childrenList.add(child);
		childrenHier.put(parent, childrenList);
	}

	/**
	 * Checks if is ancestor of.
	 *
	 * @param ancestor the ancestor
	 * @param descendant the descendant
	 * @return true, if is ancestor of
	 */
	public boolean isAncestorOf(Long ancestor,Long descendant){


		HashSet<Long>parent=parentHier.get(descendant);
		if (parent==null){
			return false;
		}
		if (parent.contains(ancestor)){
			return true;
		}
		for(Long par:parent){
			if (isAncestorOf(ancestor,par)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the parent.
	 *
	 * @param conceptId the concept id
	 * @return the parent
	 */
	public HashSet<Long> getParent(Long conceptId) {
		return parentHier.get(conceptId);
	}

	/**
	 * Gets the children.
	 *
	 * @param conceptId the concept id
	 * @return the children
	 */
	public HashSet<Long> getChildren(Long conceptId) {
		return childrenHier.get(conceptId);
	}


	/**
	 * To file.
	 *
	 * @param outputFile the output file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void toFile(String outputFile) throws IOException{
		BufferedWriter bw = getWriter(outputFile);
		addTClosureFileHeader(bw);
		writeHierarchy(bw);
		bw.close();
	}

	/**
	 * To file first level hierarchy.
	 *
	 * @param outputFile the output file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void toFileFirstLevelHierarchy(String outputFile) throws IOException{
		BufferedWriter bw = getWriter(outputFile);
		addTClosureFileHeader(bw);
		writeFirstLevelHierarchy(bw);
		bw.close();
	}

	/**
	 * Adds the t closure file header.
	 *
	 * @param bw the bw
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void addTClosureFileHeader(BufferedWriter bw) throws IOException {
		bw.append("descendant");
		bw.append("\t");
		bw.append("ancestor");
		bw.append("\r\n");		
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
	 * Write hierarchy.
	 *
	 * @param bw the bw
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeHierarchy(BufferedWriter bw) throws IOException{

		for (Long child: parentHier.keySet()){
			hControl=new HashSet<Long>();
			writeParents(bw,child,child);
			hControl=null;
		}

	}

	/**
	 * Write first level hierarchy.
	 *
	 * @param bw the bw
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeFirstLevelHierarchy(BufferedWriter bw) throws IOException{
		HashSet<Long> children = childrenHier.get(Long.parseLong(ROOT_CONCEPT));
		if (children!=null){
			for (Long child: children){
				hControl=new HashSet<Long>();
				writeDescendants(bw,child,child);
				hControl=null;
			}
		}

	}

	/**
	 * Write parents.
	 *
	 * @param bw the bw
	 * @param child the child
	 * @param descendant the descendant
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeParents(BufferedWriter bw, Long child,Long descendant) throws IOException {

		HashSet<Long> parents=parentHier.get(child);
		if (parents==null){
			return;
		}

		for(Long par:parents){
			if (!hControl.contains(par)){
				hControl.add(par);
				bw.append(descendant.toString());
				bw.append("\t");
				bw.append(par.toString());
				bw.append("\r\n");
				writeParents(bw, par,descendant);
			}
		}		
	}

	/**
	 * Write descendants.
	 *
	 * @param bw the bw
	 * @param child the child
	 * @param ancestor the ancestor
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeDescendants(BufferedWriter bw, Long child,Long ancestor) throws IOException {

		HashSet<Long> children=childrenHier.get(child);
		if (children==null){
			return;
		}

		for(Long chi:children){
			if (!hControl.contains(chi)){
				hControl.add(chi);
				bw.append(chi.toString());
				bw.append("\t");
				bw.append(ancestor.toString());
				bw.append("\r\n");
				writeDescendants(bw, chi,ancestor);
			}
		}		
	}

	/**
	 * Checks if is intermediate.
	 *
	 * @param concept the concept
	 * @param sDconcepts the s dconcepts
	 * @return true, if is intermediate
	 */
	public boolean isIntermediate(long concept, HashSet<Long> sDconcepts) {
		for (Long desc:sDconcepts){
			if (isAncestorOf(concept,desc)){
				for (Long anc:sDconcepts){
					if (isAncestorOf(anc, concept)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks if is parent.
	 *
	 * @param parent the parent
	 * @param cid the cid
	 * @return the boolean
	 */
	public Boolean isParent(Long parent, Long cid) {
		HashSet<Long> parents = parentHier.get(cid);
		if (parents!=null){
			return parents.contains(parent);
		}
		return null;
	}

	/**
	 * Gets the proximal primitives.
	 *
	 * @param sdConcepts the sd concepts
	 * @param pConcepts the concepts
	 * @param outputFile the output file
	 * @return the proximal primitives
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void getProximalPrimitives(HashSet<Long> sdConcepts,
			HashSet<Long> pConcepts, File outputFile) throws IOException {
		BufferedWriter bw = FileHelper.getWriter(outputFile);
		bw.append("id");
		bw.append("\t");
		bw.append("parent");
		bw.append("\r\n");

		for (Long concept:sdConcepts){

			HashSet<Long>primProx=getProximalPrimitives(concept, pConcepts);

			for (Long prim:primProx){
				bw.append(concept.toString());
				bw.append("\t");
				bw.append(prim.toString());
				bw.append("\r\n");
			}

		}
		bw.close();
		bw=null;
	}

	/**
	 * Gets the proximal primitives.
	 *
	 * @param concept the concept
	 * @param pConcepts the concepts
	 * @return the proximal primitives
	 */
	private HashSet<Long> getProximalPrimitives(Long concept,
			HashSet<Long> pConcepts) {
		HashSet<Long>ret=new HashSet<Long>();
		HashSet<Long> parents = parentHier.get(concept);
		if (parents!=null){
			for (Long parent:parents){
				if (pConcepts.contains(parent)){
					addNewProximalParent(ret,parent);
				}else{
					ret.addAll(getProximalPrimitives(parent,pConcepts));
				}
			}
		}
		return ret;
	}

	/**
	 * Adds the new proximal parent.
	 *
	 * @param parents the parents
	 * @param newParent the new parent
	 */
	private void addNewProximalParent(HashSet<Long> parents, Long newParent) {
		for (Long parent:parents){
			if (isAncestorOf(newParent, parent)){
				return ;
			}else if (isAncestorOf(parent,newParent)){
				parents.remove(parent);
				parents.add(newParent);
				return ;
			}
		}
		parents.add(newParent);
		return ;
	}

}
