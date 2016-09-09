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
package com.termmed.fileprovider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import com.termmed.utils.ConversionSnapshotDelta;
import com.termmed.utils.FileHelper;
import com.termmed.utils.I_Constants;
import com.termmed.utils.TClosure;

// TODO: Auto-generated Javadoc
/**
 * The Class FileProvider.
 */
public class FileProvider {


	/** The Constant TEMP_SORTING_FOLDER. */
	private static final String TEMP_SORTING_FOLDER = "tmpWorking";

	/** The Constant TEMP_SORTED_FINAL_FOLDER. */
	private static final String TEMP_SORTED_FINAL_FOLDER = "tmpSortedFinal";

	/** The Constant SNAPSHOT_FOLDER. */
	private static final String SNAPSHOT_FOLDER = "snapshot";

	/** The Constant COMPLETED_FILES_FOLDER. */
	private static final String COMPLETED_FILES_FOLDER = "completedFiles";

	/** The concept file. */
	private String conceptFile ;

	/** The description file. */
	private String descriptionFile ;

	/** The relationship file. */
	private String relationshipFile ;

	/** The attribute value file. */
	private String attributeValueFile ;

	/** The simple map file. */
	private String simpleMapFile ;

	/** The association file. */
	private String associationFile ;

	/** The language file. */
	private String languageFile ;

	/** The refset simple file. */
	private String refsetSimpleFile ;

	/** The stated relationship file. */
	private String statedRelationshipFile ;

	/** The text definition file. */
	private String textDefinitionFile ;
	/* Snapshot files */
	/** The snapshot concept file. */
	private String snapshotConceptFile ;

	/** The snapshot description file. */
	private String snapshotDescriptionFile ;

	/** The snapshot relationship file. */
	private String snapshotRelationshipFile ;

	/** The snapshot attribute value file. */
	private String snapshotAttributeValueFile ;

	/** The snapshot simple map file. */
	private String snapshotSimpleMapFile ;

	/** The snapshot association file. */
	private String snapshotAssociationFile ;

	/** The snapshot language file. */
	private String snapshotLanguageFile ;

	/** The snapshot refset simple file. */
	private String snapshotRefsetSimpleFile ;

	/** The snapshot stated relationship file. */
	private String snapshotStatedRelationshipFile ;

	/** The snapshot text definition file. */
	private String snapshotTextDefinitionFile ;


	/** The full folder. */
	private File fullFolder;

	/** The snapshot folder. */
	private File snapshotFolder;

	/** The temp sorting folder. */
	private File tempSortingFolder;

	/** The temp sorted finalfolder. */
	private File tempSortedFinalfolder;

	/** The completed files folder. */
	private File completedFilesFolder;

	/** The release date. */
	private String releaseDate;

	/** The complete stated relationship snapshot. */
	private String completeStatedRelationshipSnapshot;

	/** The complete relationship snapshot. */
	private String completeRelationshipSnapshot;

	/** The complete description snapshot. */
	private String completeDescriptionSnapshot;

	/** The release dependencies full folders. */
	private HashSet<String> releaseDependenciesFullFolders;

	/** The base folder. */
	private File baseFolder;

	/** The complete stated relationship full. */
	private String completeStatedRelationshipFull;

	/** The complete relationship full. */
	private String completeRelationshipFull;

	/** The complete description full. */
	private String completeDescriptionFull;

	/** The new concept file. */
	private File newConceptFile;

	/** The changed concept file. */
	private File changedConceptFile;

	/** The intermediate primitive file. */
	private String intermediatePrimitiveFile;

	/** The complete concept snapshot. */
	private String completeConceptSnapshot;

	/** The complete concept full. */
	private String completeConceptFull;

	/** The transitive closure stated file. */
	private String transitiveClosureStatedFile;

	/** The transitive closure inferred file. */
	private String transitiveClosureInferredFile;

	/** The concept terms. */
	private HashMap<Long, String> conceptTerms;

	/** The proximal primitive file. */
	private String proximalPrimitiveFile;

	/** The canonical changes on sd concepts file. */
	private String canonicalChangesOnSDConceptsFile;

	private String snapshotExtensionLanguage;

	private HashSet<String> refsetSimpleFiles;

	private HashSet<String> snapshotRefsetSimpleFiles;


	/**
	 * Gets the concept file.
	 *
	 * @return the conceptFile
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getConceptFile() throws IOException, Exception {
		if (conceptFile==null){
			conceptFile=FileHelper.getFile(fullFolder, "rf2-concepts", null, null, null, false, false);
		}
		return conceptFile;
	}

	/**
	 * Gets the description file.
	 *
	 * @return the descriptionFile
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getDescriptionFile() throws IOException, Exception {
		if (descriptionFile==null){
			descriptionFile=FileHelper.getFile(fullFolder, "rf2-descriptions", null, null, null, false, false);
		}
		return descriptionFile;
	}

	/**
	 * Gets the relationship file.
	 *
	 * @return the relationshipFile
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getRelationshipFile() throws IOException, Exception {
		if (relationshipFile==null){
			relationshipFile=FileHelper.getFile(fullFolder, "rf2-relationships", null, null, "stated", false, false);
		}
		return relationshipFile;
	}

	/**
	 * Gets the attribute value file.
	 *
	 * @return the attributeValueFile
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getAttributeValueFile() throws IOException, Exception {
		if (attributeValueFile==null){
			attributeValueFile=FileHelper.getFile(fullFolder, "rf2-attributevalue", null, null, null, false, false);
		}
		return attributeValueFile;
	}

	/**
	 * Gets the simple map file.
	 *
	 * @return the simpleMapFile
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSimpleMapFile() throws IOException, Exception {
		if (simpleMapFile==null){
			simpleMapFile=FileHelper.getFile(fullFolder, "rf2-simplemaps", null, null, null, false, false);
		}
		return simpleMapFile;
	}

	/**
	 * Gets the association file.
	 *
	 * @return the associationFile
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getAssociationFile() throws IOException, Exception {
		if (associationFile==null){
			associationFile=FileHelper.getFile(fullFolder, "rf2-association", null, null, null, false, false);
		}
		return associationFile;
	}

	/**
	 * Gets the language file.
	 *
	 * @return the languageFile
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getLanguageFile() throws IOException, Exception {
		if (languageFile==null){
			languageFile=FileHelper.getFile(fullFolder, "rf2-language", null, null, null, false, false);
		}
		return languageFile;
	}

	/**
	 * Gets the refset simple file.
	 *
	 * @return the refset simple file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getRefsetSimpleFile() throws IOException, Exception {
		if (refsetSimpleFile==null){
			refsetSimpleFile=FileHelper.getFile(fullFolder, "rf2-simple", null, null, null, false, false);
		}
		return refsetSimpleFile;
	}

	public HashSet<String> getRefsetSimpleFiles() throws IOException, Exception {
		if (refsetSimpleFiles==null){
			refsetSimpleFiles=FileHelper.getFilesFromFolderWithPattern(fullFolder.getAbsolutePath(), "rf2-simple", null, null, false, false);
			if (refsetSimpleFiles==null){
				refsetSimpleFiles=new HashSet<String>();
			}
		}
		return refsetSimpleFiles;
	}

	/**
	 * Gets the stated relationship file.
	 *
	 * @return the stated relationship file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getStatedRelationshipFile() throws IOException, Exception {
		if (statedRelationshipFile==null){
			statedRelationshipFile=FileHelper.getFile(fullFolder, "rf2-relationships", null, "stated", null, false, false);
		}
		return statedRelationshipFile;
	}

	/**
	 * Gets the text definition file.
	 *
	 * @return the text definition file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getTextDefinitionFile() throws IOException, Exception {
		if (textDefinitionFile==null){
			textDefinitionFile=FileHelper.getFile(fullFolder, "rf2-textDefinition", null, null, null, false, false);
		}
		return textDefinitionFile;
	}



	/**
	 * Gets the snapshot concept file.
	 *
	 * @return the snapshot concept file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSnapshotConceptFile() throws IOException, Exception {
		if (snapshotConceptFile==null){
			snapshotConceptFile=FileHelper.getFile(snapshotFolder, "rf2-concepts", null, null, null, false, false);
			if (snapshotConceptFile==null){
				getConceptFile();
				if (conceptFile!=null){
					snapshotConceptFile=new File(snapshotFolder,new File(conceptFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
					ConversionSnapshotDelta.snapshotFile(new File(conceptFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotConceptFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
				}
			}
		}
		return snapshotConceptFile;
	}

	/**
	 * Gets the snapshot description file.
	 *
	 * @return the snapshot description file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSnapshotDescriptionFile() throws IOException, Exception {
		if (snapshotDescriptionFile==null){
			snapshotDescriptionFile=FileHelper.getFile(snapshotFolder, "rf2-descriptions", null, null, null, false, false);
			if (snapshotDescriptionFile==null){
				getDescriptionFile();
				if (descriptionFile!=null){
					snapshotDescriptionFile=new File(snapshotFolder,new File(descriptionFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
					ConversionSnapshotDelta.snapshotFile(new File(descriptionFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotDescriptionFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
				}
			}
		}
		return snapshotDescriptionFile;
	}

	/**
	 * Gets the snapshot relationship file.
	 *
	 * @return the snapshot relationship file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSnapshotRelationshipFile() throws IOException, Exception {
		if (snapshotRelationshipFile==null){
			snapshotRelationshipFile=FileHelper.getFile(snapshotFolder, "rf2-relationships", null, null, "stated", false, false);
			if (snapshotRelationshipFile==null){
				getRelationshipFile();

				if (relationshipFile!=null){
					snapshotRelationshipFile=new File(snapshotFolder,new File(relationshipFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
					ConversionSnapshotDelta.snapshotFile(new File(relationshipFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotRelationshipFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
				}
			}
		}
		return snapshotRelationshipFile;
	}

	/**
	 * Gets the snapshot attribute value file.
	 *
	 * @return the snapshot attribute value file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSnapshotAttributeValueFile() throws IOException, Exception {
		if (snapshotAttributeValueFile==null){
			snapshotAttributeValueFile=FileHelper.getFile(snapshotFolder, "rf2-attributevalue", null, null, null, false, false);
			if (snapshotAttributeValueFile==null){
				getAttributeValueFile();
				snapshotAttributeValueFile=new File(snapshotFolder,new File(attributeValueFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
				ConversionSnapshotDelta.snapshotFile(new File(attributeValueFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotAttributeValueFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
			}
		}
		return snapshotAttributeValueFile;
	}

	/**
	 * Gets the snapshot simple map file.
	 *
	 * @return the snapshot simple map file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSnapshotSimpleMapFile() throws IOException, Exception {
		if (snapshotSimpleMapFile==null){
			snapshotSimpleMapFile=FileHelper.getFile(snapshotFolder, "rf2-simplemaps", null, null, null, false, false);
			if (snapshotSimpleMapFile==null){
				getSimpleMapFile();
				snapshotSimpleMapFile=new File(snapshotFolder,new File(simpleMapFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
				ConversionSnapshotDelta.snapshotFile(new File(simpleMapFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotSimpleMapFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
			}
		}
		return snapshotSimpleMapFile;
	}

	/**
	 * Gets the snapshot association file.
	 *
	 * @return the snapshot association file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSnapshotAssociationFile() throws IOException, Exception {
		if (snapshotAssociationFile==null){
			snapshotAssociationFile=FileHelper.getFile(snapshotFolder, "rf2-association", null, null, null, false, false);
			if (snapshotAssociationFile==null){
				getAssociationFile();
				snapshotAssociationFile=new File(snapshotFolder,new File(associationFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
				ConversionSnapshotDelta.snapshotFile(new File(associationFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotAssociationFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
			}
		}
		return snapshotAssociationFile;
	}

	/**
	 * Gets the snapshot language file.
	 *
	 * @return the snapshot language file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSnapshotLanguageFile() throws IOException, Exception {
		if (snapshotLanguageFile==null){
			snapshotLanguageFile=FileHelper.getFile(snapshotFolder, "rf2-language", null, null, null, false, false);
			if (snapshotLanguageFile==null){
				getLanguageFile();
				if (languageFile!=null){
					snapshotLanguageFile=new File(snapshotFolder,new File(languageFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
					ConversionSnapshotDelta.snapshotFile(new File(languageFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotLanguageFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
				}
			}
		}
		return snapshotLanguageFile;
	}

	/**
	 * Gets the snapshot refset simple file.
	 *
	 * @return the snapshot refset simple file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSnapshotRefsetSimpleFile() throws IOException, Exception {
		if (snapshotRefsetSimpleFile==null){
			snapshotRefsetSimpleFile=FileHelper.getFile(snapshotFolder, "rf2-simple", null, null, null, false, false);
			if (snapshotRefsetSimpleFile==null){
				getRefsetSimpleFile();
				snapshotRefsetSimpleFile=new File(snapshotFolder,new File(refsetSimpleFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
				ConversionSnapshotDelta.snapshotFile(new File(refsetSimpleFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotRefsetSimpleFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
			}
		}
		return snapshotRefsetSimpleFile;
	}

	public HashSet<String> getSnapshotRefsetSimpleFiles() throws IOException, Exception {
		if (snapshotRefsetSimpleFiles==null){
			snapshotRefsetSimpleFiles=FileHelper.getFilesFromFolderWithPattern(snapshotFolder.getAbsolutePath(),  "rf2-simple", null, null, false, false);
			if (snapshotRefsetSimpleFiles==null ){
				getRefsetSimpleFiles();
				snapshotRefsetSimpleFiles=new HashSet<String>();
				for (String refsetSimpleFile:refsetSimpleFiles){
					String snapshotRefsetSimpleFile=new File(snapshotFolder,new File(refsetSimpleFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
					ConversionSnapshotDelta.snapshotFile(new File(refsetSimpleFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotRefsetSimpleFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
					snapshotRefsetSimpleFiles.add(snapshotRefsetSimpleFile);
				}	
			}
		}
		return snapshotRefsetSimpleFiles;
	}
	/**
	 * Gets the snapshot stated relationship file.
	 *
	 * @return the snapshot stated relationship file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSnapshotStatedRelationshipFile() throws IOException, Exception {
		if (snapshotStatedRelationshipFile==null){
			snapshotStatedRelationshipFile=FileHelper.getFile(snapshotFolder, "rf2-relationships", null, "stated", null, false, false);
			if (snapshotStatedRelationshipFile==null){
				getStatedRelationshipFile();
				if (statedRelationshipFile!=null){
					snapshotStatedRelationshipFile=new File(snapshotFolder,new File(statedRelationshipFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
					ConversionSnapshotDelta.snapshotFile(new File(statedRelationshipFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotStatedRelationshipFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
				}
			}
		}
		return snapshotStatedRelationshipFile;
	}

	/**
	 * Gets the snapshot text definition file.
	 *
	 * @return the snapshot text definition file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getSnapshotTextDefinitionFile() throws IOException, Exception {
		if (snapshotTextDefinitionFile==null){
			snapshotTextDefinitionFile=FileHelper.getFile(snapshotFolder, "rf2-textDefinition", null, null, null, false, false);
			if (snapshotTextDefinitionFile==null){
				getTextDefinitionFile();
				snapshotTextDefinitionFile=new File(snapshotFolder,new File(textDefinitionFile).getName().toLowerCase().replace("full","snapshot")).getAbsolutePath();
				ConversionSnapshotDelta.snapshotFile(new File(textDefinitionFile), tempSortingFolder, tempSortedFinalfolder, new File(snapshotTextDefinitionFile), releaseDate, new int[]{0,1}, 0, 1, null, null, null);
			}
		}
		return snapshotTextDefinitionFile;
	}

	/**
	 * Gets the full folder.
	 *
	 * @return the full folder
	 */
	public File getFullFolder() {
		return fullFolder;
	}

	/**
	 * Sets the full folder.
	 *
	 * @param fullFolder the new full folder
	 */
	public void setFullFolder(File fullFolder) {
		this.fullFolder = fullFolder;
	}

	/**
	 * Gets the snapshot folder.
	 *
	 * @return the snapshot folder
	 */
	public File getSnapshotFolder() {
		return snapshotFolder;
	}

	/**
	 * Sets the snapshot folder.
	 *
	 * @param snapshotFolder the new snapshot folder
	 */
	public void setSnapshotFolder(File snapshotFolder) {
		this.snapshotFolder = snapshotFolder;
	}

	/**
	 * Gets the temp sorting folder.
	 *
	 * @return the temp sorting folder
	 */
	public File getTempSortingFolder() {
		return tempSortingFolder;
	}

	/**
	 * Sets the temp sorting folder.
	 *
	 * @param tempSortingFolder the new temp sorting folder
	 */
	public void setTempSortingFolder(File tempSortingFolder) {
		this.tempSortingFolder = tempSortingFolder;
	}

	/**
	 * Gets the temp sorted finalfolder.
	 *
	 * @return the temp sorted finalfolder
	 */
	public File getTempSortedFinalfolder() {
		return tempSortedFinalfolder;
	}

	/**
	 * Sets the temp sorted finalfolder.
	 *
	 * @param tempSortedFinalfolder the new temp sorted finalfolder
	 */
	public void setTempSortedFinalfolder(File tempSortedFinalfolder) {
		this.tempSortedFinalfolder = tempSortedFinalfolder;
	}

	/**
	 * Gets the release date.
	 *
	 * @return the release date
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Sets the release date.
	 *
	 * @param releaseDate the new release date
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * Instantiates a new file provider.
	 *
	 * @param sourceFullFolder the source full folder
	 * @param baseFolder the base folder
	 * @param releaseDependenciesFullFolders the release dependencies full folders
	 * @param releaseDate the release date
	 */
	public FileProvider(File sourceFullFolder,File baseFolder,HashSet<String>releaseDependenciesFullFolders, String releaseDate){
		setFullFolder(sourceFullFolder);
		setBaseFolder(baseFolder);
		setReleaseDate(releaseDate);
		setReleaseDependenciesFullFolders(releaseDependenciesFullFolders);
		createFolders();

	}

	/**
	 * Sets the base folder.
	 *
	 * @param baseFolder the new base folder
	 */
	private void setBaseFolder(File baseFolder) {
		this.baseFolder=baseFolder;
	}

	/**
	 * Creates the folders.
	 */
	private void createFolders() {

		if (!baseFolder.exists()){
			baseFolder.mkdirs();
		}else{
			FileHelper.emptyFolder(baseFolder);
		}

		tempSortingFolder=new File(baseFolder , TEMP_SORTING_FOLDER);
		if (!tempSortingFolder.exists()){
			tempSortingFolder.mkdirs();
		}else{
			FileHelper.emptyFolder(tempSortingFolder);
		}
		tempSortedFinalfolder=new File(baseFolder , TEMP_SORTED_FINAL_FOLDER);
		if (!tempSortedFinalfolder.exists()){
			tempSortedFinalfolder.mkdirs();
		}else{
			FileHelper.emptyFolder(tempSortedFinalfolder);
		}	

		snapshotFolder=new File(baseFolder , SNAPSHOT_FOLDER);
		if (!snapshotFolder.exists()){
			snapshotFolder.mkdirs();
		}else{
			FileHelper.emptyFolder(snapshotFolder);
		}
		completedFilesFolder=new File(baseFolder , COMPLETED_FILES_FOLDER);
		if (!completedFilesFolder.exists()){
			completedFilesFolder.mkdirs();
		}else{
			FileHelper.emptyFolder(completedFilesFolder);
		}

	}

	/**
	 * Gets the complete description snapshot.
	 *
	 * @return the complete description snapshot
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getCompleteDescriptionSnapshot() throws IOException, Exception {
		if (completeDescriptionSnapshot==null){
			getCompleteDescriptionFull();
			if (completeDescriptionFull!=null){
				completeDescriptionSnapshot=completedFilesFolder.getAbsolutePath() + "/" + "sct2_completeDescriptionSnapshot.txt";
				ConversionSnapshotDelta.snapshotFile(new File(completeDescriptionFull), tempSortingFolder, tempSortedFinalfolder, new File(completeDescriptionSnapshot), releaseDate, new int[]{0,1}, 0, 1,null,null,null);
			}
		}
		return completeDescriptionSnapshot;
	}

	/**
	 * Gets the complete description full.
	 *
	 * @return the complete description full
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getCompleteDescriptionFull() throws IOException, Exception {
		if (completeDescriptionFull==null){
			if (releaseDependenciesFullFolders!=null){
				HashSet<String> tmpFiles=new HashSet<String>();
				for (String fullFolder:releaseDependenciesFullFolders){
					String tmpFile=FileHelper.getFile(new File(fullFolder), "rf2-descriptions", null, null, null, false, false);
					tmpFiles.add(tmpFile);
				}
				String descFull=getDescriptionFile();
				if (descFull!=null){
					tmpFiles.add(descFull);
				}
				completeDescriptionFull=completedFilesFolder.getAbsolutePath() + "/" + "sct2_completeDescriptionFull.txt";
				FileHelper.completeFileFromArray(tmpFiles,  completeDescriptionFull);
			}
		}
		return completeDescriptionFull;
	}

	/**
	 * Sets the complete description snapshot.
	 *
	 * @param completeDescriptionSnapshot the new complete description snapshot
	 */
	public void setCompleteDescriptionSnapshot(
			String completeDescriptionSnapshot) {
		this.completeDescriptionSnapshot = completeDescriptionSnapshot;
	}

	/**
	 * Gets the release dependencies full folders.
	 *
	 * @return the release dependencies full folders
	 */
	public HashSet<String> getReleaseDependenciesFullFolders() {
		return releaseDependenciesFullFolders;
	}

	/**
	 * Sets the release dependencies full folders.
	 *
	 * @param releaseDependenciesFullFolders the new release dependencies full folders
	 */
	public void setReleaseDependenciesFullFolders(
			HashSet<String> releaseDependenciesFullFolders) {
		this.releaseDependenciesFullFolders = releaseDependenciesFullFolders;
	}

	/**
	 * Gets the completed files folder.
	 *
	 * @return the completed files folder
	 */
	public File getCompletedFilesFolder() {
		return completedFilesFolder;
	}

	/**
	 * Sets the completed files folder.
	 *
	 * @param completedFilesFolder the new completed files folder
	 */
	public void setCompletedFilesFolder(File completedFilesFolder) {
		this.completedFilesFolder = completedFilesFolder;
	}

	/**
	 * Gets the complete relationship snapshot.
	 *
	 * @return the complete relationship snapshot
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getCompleteRelationshipSnapshot() throws IOException, Exception {
		if (completeRelationshipSnapshot==null){
			getCompleteRelationshipFull();
			if (completeRelationshipFull!=null){
				completeRelationshipSnapshot=completedFilesFolder.getAbsolutePath() + "/" + "sct2_completeRelationshipSnapshot.txt";
				ConversionSnapshotDelta.snapshotFile(new File(completeRelationshipFull), tempSortingFolder, tempSortedFinalfolder, new File(completeRelationshipSnapshot), releaseDate, new int[]{0,1}, 0, 1,null,null,null);
			}
		}
		return completeRelationshipSnapshot;
	}

	/**
	 * Gets the complete relationship full.
	 *
	 * @return the complete relationship full
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getCompleteRelationshipFull() throws IOException, Exception {
		if (completeRelationshipFull==null){
			if (releaseDependenciesFullFolders!=null){
				HashSet<String> tmpFiles=new HashSet<String>();
				for (String fullFolder:releaseDependenciesFullFolders){
					String tmpFile=FileHelper.getFile(new File(fullFolder), "rf2-relationships", null, null, "stated", false, false);
					tmpFiles.add(tmpFile);
				}
				String relsFull=getRelationshipFile();
				if (relsFull!=null){
					tmpFiles.add(relsFull);
				}
				completeRelationshipFull=completedFilesFolder.getAbsolutePath() + "/" + "sct2_completeRelationshipFull.txt";
				FileHelper.completeFileFromArray(tmpFiles,  completeRelationshipFull);
			}
		}
		return completeRelationshipFull;
	}

	/**
	 * Sets the complete relationship snapshot.
	 *
	 * @param completeRelationshipSnapshot the new complete relationship snapshot
	 */
	public void setCompleteRelationshipSnapshot(
			String completeRelationshipSnapshot) {
		this.completeRelationshipSnapshot = completeRelationshipSnapshot;
	}

	/**
	 * Gets the complete stated relationship snapshot.
	 *
	 * @return the complete stated relationship snapshot
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getCompleteStatedRelationshipSnapshot() throws IOException, Exception {
		if (completeStatedRelationshipSnapshot==null){
			getCompleteStatedRelationshipFull();
			if (completeStatedRelationshipFull!=null){
				completeStatedRelationshipSnapshot=completedFilesFolder.getAbsolutePath() + "/" + "sct2_completeStatedRelationshipSnapshot.txt";
				ConversionSnapshotDelta.snapshotFile(new File(completeStatedRelationshipFull), tempSortingFolder, tempSortedFinalfolder, new File(completeStatedRelationshipSnapshot), releaseDate, new int[]{0,1}, 0, 1,null,null,null);
			}
		}
		return completeStatedRelationshipSnapshot;
	}

	/**
	 * Gets the complete stated relationship full.
	 *
	 * @return the complete stated relationship full
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getCompleteStatedRelationshipFull() throws IOException, Exception {
		if (completeStatedRelationshipFull==null){
			if (releaseDependenciesFullFolders!=null){
				HashSet<String> tmpFiles=new HashSet<String>();
				for (String fullFolder:releaseDependenciesFullFolders){
					System.out.println("folder:" +fullFolder);
					String tmpFile=FileHelper.getFile(new File(fullFolder), "rf2-relationships", null, "stated", null, false, false);
					System.out.println("file:" + tmpFile);
					tmpFiles.add(tmpFile);
				}
				String relsFull=getStatedRelationshipFile();
				if (relsFull!=null){
					tmpFiles.add(relsFull);
				}
				completeStatedRelationshipFull=completedFilesFolder.getAbsolutePath() + "/" + "sct2_completeStatedRelationshipFull.txt";
				FileHelper.completeFileFromArray(tmpFiles,  completeStatedRelationshipFull);
			}
		}
		return completeStatedRelationshipFull;
	}

	/**
	 * Sets the complete stated relationship snapshot.
	 *
	 * @param completeStatedRelationshipSnapshot the new complete stated relationship snapshot
	 */
	public void setCompleteStatedRelationshipSnapshot(
			String completeStatedRelationshipSnapshot) {
		this.completeStatedRelationshipSnapshot = completeStatedRelationshipSnapshot;
	}

	/**
	 * Gets the full file by pattern.
	 *
	 * @param patternTag the pattern tag
	 * @param fileNameMustHave the file name must have
	 * @param fileNameDoesntMustHave the file name doesnt must have
	 * @return the full file by pattern
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getFullFileByPattern(String patternTag,
			String fileNameMustHave, String fileNameDoesntMustHave) throws IOException, Exception {
		if (patternTag.equals("rf2-concepts")){

			return getConceptFile();
		}
		if (patternTag.equals("rf2-descriptions")){

			return getDescriptionFile();
		}
		if (patternTag.equals("rf2-relationships")){
			if (fileNameMustHave!=null && fileNameMustHave.toLowerCase().equals("stated")){
				return getStatedRelationshipFile();
			}
			if (fileNameDoesntMustHave!=null && fileNameDoesntMustHave.toLowerCase().equals("stated")){
				return getRelationshipFile();
			}
		}
		if (patternTag.equals("rf2-attributevalue")){

			return getAttributeValueFile();
		}
		if (patternTag.equals("rf2-simplemaps")){

			return getSimpleMapFile();
		}
		if (patternTag.equals("rf2-association")){

			return getAssociationFile();
		}
		if (patternTag.equals("rf2-language")){

			return getLanguageFile();
		}
		if (patternTag.equals("rf2-simple")){

			return getRefsetSimpleFile();
		}
		if (patternTag.equals("rf2-textDefinition")){

			return getTextDefinitionFile();
		}

		return null;
	}

	/**
	 * Gets the new concept file.
	 *
	 * @return the new concept file
	 */
	public File getNewConceptFile() {
		return newConceptFile;
	}

	/**
	 * Sets the new concept file.
	 *
	 * @param newConceptFile the new new concept file
	 */
	public void setNewConceptFile(File newConceptFile) {
		this.newConceptFile = newConceptFile;
	}

	/**
	 * Gets the changed concept file.
	 *
	 * @return the changed concept file
	 */
	public File getChangedConceptFile() {
		return changedConceptFile;
	}

	/**
	 * Sets the changed concept file.
	 *
	 * @param changedConceptFile the new changed concept file
	 */
	public void setChangedConceptFile(File changedConceptFile) {
		this.changedConceptFile = changedConceptFile;
	}

	/**
	 * Sets the intermediate primitive file.
	 *
	 * @param intermediatePrimitiveFile the new intermediate primitive file
	 */
	public void setIntermediatePrimitiveFile(String intermediatePrimitiveFile) {
		this.intermediatePrimitiveFile=intermediatePrimitiveFile;
	}

	/**
	 * Gets the intermediate primitive file.
	 *
	 * @return the intermediate primitive file
	 */
	public String getIntermediatePrimitiveFile() {
		return intermediatePrimitiveFile;
	}

	/**
	 * Gets the complete concept snapshot.
	 *
	 * @return the complete concept snapshot
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getCompleteConceptSnapshot() throws IOException, Exception {
		if (completeConceptSnapshot==null){
			getCompleteConceptFull();
			if (completeConceptFull!=null){
				completeConceptSnapshot=completedFilesFolder.getAbsolutePath() + "/" + "sct2_completeConceptSnapshot.txt";
				ConversionSnapshotDelta.snapshotFile(new File(completeConceptFull), tempSortingFolder, tempSortedFinalfolder, new File(completeConceptSnapshot), releaseDate, new int[]{0,1}, 0, 1,null,null,null);
			}
		}
		return completeConceptSnapshot;
	}

	/**
	 * Gets the complete concept full.
	 *
	 * @return the complete concept full
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	private String getCompleteConceptFull() throws IOException, Exception {
		if (completeConceptFull==null){
			if (releaseDependenciesFullFolders!=null){
				HashSet<String> tmpFiles=new HashSet<String>();
				for (String fullFolder:releaseDependenciesFullFolders){
					String tmpFile=FileHelper.getFile(new File(fullFolder), "rf2-concepts", null, null, null, false, false);
					tmpFiles.add(tmpFile);
				}
				String concFull=getConceptFile();
				if (concFull!=null){
					tmpFiles.add(concFull);
				}
				completeConceptFull=completedFilesFolder.getAbsolutePath() + "/" + "sct2_completeConceptFull.txt";
				FileHelper.completeFileFromArray(tmpFiles,  completeConceptFull);
			}
		}
		return completeConceptFull;		
	}

	/**
	 * Sets the transitive closure stated file.
	 *
	 * @param transitiveClosureStatedFile the new transitive closure stated file
	 */
	public void setTransitiveClosureStatedFile(String transitiveClosureStatedFile) {
		this.transitiveClosureStatedFile=transitiveClosureStatedFile;
	}

	/**
	 * Gets the transitive closure stated file.
	 *
	 * @return the transitive closure stated file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getTransitiveClosureStatedFile() throws IOException, Exception {
		if (transitiveClosureStatedFile==null){
			String statedRels;
			if (getReleaseDependenciesFullFolders()!=null){
				statedRels=getCompleteStatedRelationshipSnapshot();
			}else{
				statedRels=getSnapshotStatedRelationshipFile();
			}
			TClosure tClos=new TClosure(statedRels,4,5,7,2);
			transitiveClosureStatedFile = new File(completedFilesFolder,"t_closure_stated.txt").getAbsolutePath();
			tClos.toFile(transitiveClosureStatedFile);
			tClos=null;
		}
		return transitiveClosureStatedFile;
	}

	/**
	 * Sets the transitive closure inferred file.
	 *
	 * @param transitiveClosureInferredFile the new transitive closure inferred file
	 */
	public void setTransitiveClosureInferredFile(String transitiveClosureInferredFile) {
		this.transitiveClosureInferredFile=transitiveClosureInferredFile;
	}

	/**
	 * Gets the transitive closure inferred file.
	 *
	 * @return the transitive closure inferred file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public String getTransitiveClosureInferredFile() throws IOException, Exception {
		if (transitiveClosureInferredFile==null){
			String inferRels;
			if (getReleaseDependenciesFullFolders()!=null){
				inferRels=getCompleteRelationshipSnapshot();
			}else{
				inferRels=getSnapshotRelationshipFile();
			}
			TClosure tClos=new TClosure(inferRels,4,5,7,2);
			transitiveClosureInferredFile = new File(completedFilesFolder,"t_closure_inferred.txt").getAbsolutePath();
			tClos.toFile(transitiveClosureInferredFile);
			tClos=null;
		}
		return transitiveClosureInferredFile;
	}

	/**
	 * Gets the concept terms.
	 *
	 * @return the concept terms
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public HashMap<Long, String> getConceptTerms() throws IOException, Exception {
		if (conceptTerms==null){
			conceptTerms=new HashMap<Long, String>();

			getSnapshotDescriptionFile();
			if (snapshotDescriptionFile!=null){
				setTerms(snapshotDescriptionFile);
			}
			if (getReleaseDependenciesFullFolders()!=null){
				TreeMap<Integer,String> tmpFiles=new TreeMap<Integer,String>();
				int cont=0;
				for (String fullFolder:getReleaseDependenciesFullFolders()){
					String tmpFile=FileHelper.getFile(new File(fullFolder), "rf2-descriptions", null, null, null, false, false);
					HashSet<String>modules=FileHelper.getModules(tmpFile);

					if (modules.contains(I_Constants.CORE_MODULE)){
						if (modules.size()>1){
							tmpFiles.put(Integer.MAX_VALUE-cont, tmpFile);
						}else {
							tmpFiles.put(Integer.MAX_VALUE, tmpFile);
						}
					}else{
						tmpFiles.put(cont, tmpFile);
					}
					cont++;
				}
				for (Integer index:tmpFiles.keySet()){
					String file=tmpFiles.get(index);
					setTerms(file);
				}
			}

		}
		return conceptTerms;
	}

	/**
	 * Sets the terms.
	 *
	 * @param file the new terms
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws NumberFormatException the number format exception
	 */
	private void setTerms(String file) throws UnsupportedEncodingException,
	FileNotFoundException, IOException, NumberFormatException {
		//		System.out.println("desc file:" + file);
		BufferedReader br=FileHelper.getReader(file);
		br.readLine();
		String line;
		String[] spl;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") 
					&& spl[6].equals(I_Constants.FSN) && !conceptTerms.containsKey(Long.parseLong(spl[4]))){

				conceptTerms.put(Long.parseLong(spl[4]),spl[7]);
			}
		}
		br.close();
	}

	/**
	 * Gets the proximal primitive file.
	 *
	 * @return the proximal primitive file
	 */
	public String getProximalPrimitiveFile() {
		return proximalPrimitiveFile;
	}

	/**
	 * Sets the proximal primitive file.
	 *
	 * @param proximalPrimitiveFile the new proximal primitive file
	 */
	public void setProximalPrimitiveFile(String proximalPrimitiveFile) {
		this.proximalPrimitiveFile = proximalPrimitiveFile;
	}

	/**
	 * Sets the canonical changes on sd concepts file.
	 *
	 * @param canonicalChangesOnSDConceptsFile the new canonical changes on sd concepts file
	 */
	public void setCanonicalChangesOnSDConceptsFile(String canonicalChangesOnSDConceptsFile) {
		this.canonicalChangesOnSDConceptsFile=canonicalChangesOnSDConceptsFile;
	}

	/**
	 * Gets the canonical changes on sd concepts file.
	 *
	 * @return the canonical changes on sd concepts file
	 */
	public String getCanonicalChangesOnSDConceptsFile() {
		return canonicalChangesOnSDConceptsFile;
	}

	public void setSnapshotExtensionLanguage(String snapshotExtensionLanguage) {
		if (snapshotExtensionLanguage!=null && snapshotExtensionLanguage.trim().equals("")){
			this.snapshotExtensionLanguage=null;
		}else{
			this.snapshotExtensionLanguage=snapshotExtensionLanguage;
		}
	}

	public String getSnapshotExtensionLanguage() {
		if (snapshotExtensionLanguage==null){
			File snapshotExtensionLanguageFile=new File(snapshotFolder,"snap_ext_lang_empty.txt");
			try {
				snapshotExtensionLanguageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			snapshotExtensionLanguage=snapshotExtensionLanguageFile.getAbsolutePath();

		}
		return snapshotExtensionLanguage;
	}

}
