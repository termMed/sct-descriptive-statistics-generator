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
package com.termmed.statistics.db.importer;

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
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.termmed.fileprovider.CurrentFile;
import com.termmed.fileprovider.DependentFile;
import com.termmed.statistics.db.setup.DbSetup;
import com.termmed.statistics.model.ReportConfig;
import com.termmed.utils.ConversionSnapshotDelta;
import com.termmed.utils.FileHelper;
import com.termmed.utils.FileTest;
import com.termmed.utils.I_Constants;
import com.termmed.utils.ResourceUtils;
import com.termmed.utils.TClosure;


// TODO: Auto-generated Javadoc
/**
 * The Class ImportManager.
 */
public class ImportManager {
	
	/** The root desc. */
	boolean rootDesc;
	
	/** The concepts. */
	boolean concepts;
	
	/** The descriptions. */
	boolean descriptions;
	
	/** The descriptions_pre. */
	boolean descriptions_pre;
	
	/** The relationships. */
	boolean relationships;
	
	/** The stated rels. */
	boolean statedRels;

	/** The owl expression. */
	boolean owlExpress;

	/** The text defins. */
	boolean textDefins;
	
	/** The attribute values. */
	boolean attributeValues;
	
	/** The associations. */
	boolean associations;
	
	/** The same_associations. */
	boolean same_associations;
	
	/** The simple refset. */
	boolean simpleRefset;
	
	/** The simple maps. */
	boolean simpleMaps;
	
	/** The language. */
	boolean language;
	
	/** The extension language. */
	boolean extLanguage;
	
	/** The t closure inferred. */
	boolean tClosureInferred;
	
	/** The t closure stated. */
	boolean tClosureStated;
	
	/** The concepts_pre. */
	boolean concepts_pre;
	
	/** The relationships_pre. */
	boolean relationships_pre;
	
	/** The stated rels_pre. */
	boolean statedRels_pre;
	
	/** The t closure stated_pre. */
	boolean tClosureInferred_pre;

	private String refsetId;
	
	/** The params. */
	public static HashMap<String,String> params;
	
	/**
	 * The Enum TABLE.
	 */
	public enum TABLE{
		
		/** The concepts. */
		CONCEPTS("s_concepts", "rf2-concepts",null,null,null,false,"id,active,definitionStatusId",null,null),
		
		/** The concepts previous. */
		CONCEPTS_PREVIOUS("s_concepts_pre", "rf2-concepts",null,null,null,true,"id,active,definitionStatusId",null,null),
		
		/** The descriptions. */
		DESCRIPTIONS("s_descriptions", "rf2-descriptions",null,null,null,false,"id,effectiveTime,active,conceptId,typeId,term",null,null),
		
		/** The descriptions previous. */
		DESCRIPTIONS_PREVIOUS("s_descriptions_pre", "rf2-descriptions",null,null,null,true,"id,effectiveTime,active,conceptId,typeId,term",null,null),
		
		/** The relationships. */
		RELATIONSHIPS("s_relationships","rf2-relationships",null,null,"stated",false,"sourceId,effectiveTime,active",null,null),
		
		/** The relationships previous. */
		RELATIONSHIPS_PREVIOUS("s_relationships_pre",null,null,null,"stated",true,null,null,null),
		
		/** The statedrels. */
		STATEDRELS("s_statedrels", "rf2-relationships",null,"stated",null,false,"sourceId,effectiveTime,active",null,null),
		
		/** The inferredrootdesc. */
		INFERREDROOTDESC("s_inferredrootdesc", "rf2-inferredrootdesc",null,null,"stated",false,"sourceId,term",new Integer[]{2,5,7},new String[]{"1","138875005","116680003"}),
		
		/** The statedrels previous. */
		STATEDRELS_PREVIOUS("s_statedrels_pre", null,null,"stated",null,true,null,null,null),

		/** The owlExpression. */
		OWLEXPRESS("s_owlexpress", "rf2-owl-expression",null,null,null,false,"referencedComponentId,effectiveTime,active",null,null),

		/** The textdefins. */
		TEXTDEFINS("s_textdefin",null,null,null,null,false,null,null,null),
		
		/** The attributevalues. */
		ATTRIBUTEVALUES("s_attributevalues","rf2-attributevalue",null,null,null,false,"refsetId,referencedComponentId,valueId",new Integer[]{2,4},new String[]{"1","900000000000489007"}),
		
		/** The associations. */
		ASSOCIATIONS("s_associations",null,null,null,null,false,null,null,null),
		
		/** The same as associations. */
		SAME_AS_ASSOCIATIONS("s_same_associations","rf2-association",null,null,null,false,"referencedComponentId,targetComponentId",new Integer[]{2,4},new String[]{"1","900000000000527005"}),
		
		/** The simplerefset. */
		SIMPLEREFSET("s_simplerefsets",null,null,null,null,false,null,null,null),
		
		/** The simplemaps. */
		SIMPLEMAPS("s_simplemaps",null,null,null,null,false,null,null,null),
		
		/** The language. */
		LANGUAGE("s_languages","rf2-language",null,null,"_ext",false,"referencedComponentId",new Integer[]{2,6},new String[]{"1","900000000000548007"}),
		
		/** The language. */
		LANGUAGE_PREVIOUS("s_languages_pre","rf2-language",null,null,"_ext",true,"referencedComponentId",new Integer[]{2,6},new String[]{"1","900000000000548007"}),
		
		/** The language. */
		EXT_LANGUAGE("s_ext_languages","rf2-language",null,null,"s_l",false,"referencedComponentId",new Integer[]{2},new String[]{"1"}),
		
		/** The tclosurestated. */
		TCLOSURESTATED("s_tclosure_stated","transitive-closure",null,"stated","pre",false,null,null,null),
		
		/** The tclosureinferred previous. */
		TCLOSUREINFERRED_PREVIOUS("s_tclosure_inferred_pre","transitive-closure",null,"pre",null,true,null,null,null),
		
		/** The tclosureinferred. */
		TCLOSUREINFERRED("s_tclosure_inferred","transitive-closure",null,"inferred","pre",false,null,null,null);

		/** The table name. */
		private String tableName;
		
		/** The pattern tag. */
		private String patternTag;
		
		/** The default folder. */
		private String defaultFolder;
		
		/** The file name must have. */
		private String fileNameMustHave;
		
		/** The file name doesnt must have. */
		private String fileNameDoesntMustHave;
		
		/** The is previous. */
		private boolean isPrevious;
		
		/** The fields. */
		private String fields;
		
		/** The field filter. */
		private Integer[] fieldFilter;
		
		/** The field filter value. */
		private String[] fieldFilterValue;

		/**
		 * Instantiates a new table.
		 *
		 * @param tableName the table name
		 * @param patternTag the pattern tag
		 * @param defaultFolder the default folder
		 * @param fileNameMustHave the file name must have
		 * @param fileNameDoesntMustHave the file name doesnt must have
		 * @param isPrevious the is previous
		 * @param fields the fields
		 * @param fieldFilter the field filter
		 * @param fieldFilterValue the field filter value
		 */
		TABLE (String tableName,
				String patternTag,
				String defaultFolder, 
				String fileNameMustHave,
				String fileNameDoesntMustHave,
				boolean isPrevious,
				String fields,
				Integer[] fieldFilter,
				String[] fieldFilterValue){
			this.tableName=tableName;
			this.patternTag=patternTag;
			this.defaultFolder=defaultFolder;
			this.fileNameMustHave=fileNameMustHave;
			this.fileNameDoesntMustHave=fileNameDoesntMustHave;
			this.isPrevious=isPrevious;
			this.fields=fields;
			this.fieldFilter=fieldFilter;
			this.fieldFilterValue=fieldFilterValue;
		}

		/**
		 * Gets the table name.
		 *
		 * @return the table name
		 */
		public String getTableName() {
			return tableName;
		}

		/**
		 * Gets the pattern tag.
		 *
		 * @return the pattern tag
		 */
		public String getPatternTag() {
			return patternTag;
		}

		/**
		 * Sets the pattern tag.
		 *
		 * @param patternTag the new pattern tag
		 */
		public void setPatternTag(String patternTag) {
			this.patternTag = patternTag;
		}

		/**
		 * Gets the default folder.
		 *
		 * @return the default folder
		 */
		public String getDefaultFolder() {
			return defaultFolder;
		}

		/**
		 * Sets the default folder.
		 *
		 * @param defaultFolder the new default folder
		 */
		public void setDefaultFolder(String defaultFolder) {
			this.defaultFolder = defaultFolder;
		}

		/**
		 * Gets the file name must have.
		 *
		 * @return the file name must have
		 */
		public String getFileNameMustHave() {
			return fileNameMustHave;
		}

		/**
		 * Sets the file name must have.
		 *
		 * @param fileNameMustHave the new file name must have
		 */
		public void setFileNameMustHave(String fileNameMustHave) {
			this.fileNameMustHave = fileNameMustHave;
		}

		/**
		 * Gets the file name doesnt must have.
		 *
		 * @return the file name doesnt must have
		 */
		public String getFileNameDoesntMustHave() {
			return fileNameDoesntMustHave;
		}

		/**
		 * Sets the file name doesnt must have.
		 *
		 * @param fileNameDoesntMustHave the new file name doesnt must have
		 */
		public void setFileNameDoesntMustHave(String fileNameDoesntMustHave) {
			this.fileNameDoesntMustHave = fileNameDoesntMustHave;
		}

		/**
		 * Checks if is previous.
		 *
		 * @return true, if is previous
		 */
		public boolean isPrevious() {
			return isPrevious;
		}

		/**
		 * Sets the previous.
		 *
		 * @param isPrevious the new previous
		 */
		public void setPrevious(boolean isPrevious) {
			this.isPrevious = isPrevious;
		}

		/**
		 * Sets the table name.
		 *
		 * @param tableName the new table name
		 */
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		/**
		 * Gets the fields.
		 *
		 * @return the fields
		 */
		public String getFields() {
			return fields;
		}

		/**
		 * Sets the fields.
		 *
		 * @param fields the new fields
		 */
		public void setFields(String fields) {
			this.fields = fields;
		}

		/**
		 * Gets the field filter.
		 *
		 * @return the field filter
		 */
		public Integer[] getFieldFilter() {
			return fieldFilter;
		}

		/**
		 * Sets the field filter.
		 *
		 * @param fieldFilter the new field filter
		 */
		public void setFieldFilter(Integer[] fieldFilter) {
			this.fieldFilter = fieldFilter;
		}

		/**
		 * Gets the field filter value.
		 *
		 * @return the field filter value
		 */
		public String[] getFieldFilterValue() {
			return fieldFilterValue;
		}

		/**
		 * Sets the field filter value.
		 *
		 * @param fieldFilterValue the new field filter value
		 */
		public void setFieldFilterValue(String[] fieldFilterValue) {
			this.fieldFilterValue = fieldFilterValue;
		}
	};

	/** The importer. */
	Importer importer;
	
	/** The data folder. */
	File dataFolder;
	
	/** The config file. */
	private File configFile;
	
	/** The release date. */
	private String releaseDate;
	
	/** The previous release date. */
	private String previousReleaseDate;
	
	/** The reduced snapshot folder. */
	private File reducedSnapshotFolder;
	
	/** The previous reduced snapshot folder. */
	private File previousReducedSnapshotFolder;
	
	/** The connection. */
	private Connection connection;
	
	/** The changed previous date. */
	private boolean changedPreviousDate;
	
	/** The changed date. */
	private boolean changedDate;
	
	/** The sort folder tmp. */
	private File sortFolderTmp;
	
	/** The sorted folder tmp. */
	private File sortedFolderTmp;
	
	/** The release dependencies. */
	private boolean releaseDependencies;

	private boolean lang_pre;

	private boolean lang;

	/** The logger. */
	private static Logger logger;
	
	/**
	 * Instantiates a new import manager.
	 *
	 * @param con the con
	 * @param file the file
	 * @param changedDate the changed date
	 * @param changedPreviousDate the changed previous date
	 */
	public ImportManager(Connection con, File file, boolean changedDate, boolean changedPreviousDate) {
		logger = LogManager.getLogger("com.termmed.statistics.db.importer.ImportManager");
		this.connection=con;
		this.configFile=file;
		this.changedDate=changedDate;
		this.changedPreviousDate=changedPreviousDate;
	}

	/**
	 * Creates the folders.
	 *
	 * @throws Exception the exception
	 */
	private void createFolders() throws Exception {

		dataFolder=new File(I_Constants.REPO_FOLDER);
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		sortFolderTmp=CurrentFile.get().getTempSortingFolder();
		sortedFolderTmp=CurrentFile.get().getTempSortedFinalfolder();

	}

	/**
	 * Execute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public void execute() throws IOException, Exception {
		logger.info("Starting import manager process");

		createFolders();
		importer=new Importer();
		if(!existingTables()){
			DbSetup dbSetup=new DbSetup(connection);
			dbSetup.execute();
			dbSetup=null;
		}
		XMLConfiguration xmlConfig;
		try {
			xmlConfig=new XMLConfiguration(configFile);
		} catch (ConfigurationException e) {
			logger.error("ClassificationRunner - Error happened getting params configFile." + e.getMessage());
			throw e;
		}

		refsetId=xmlConfig.getString("refsetId");
		releaseDependencies=CurrentFile.get().getReleaseDependenciesFullFolders()!=null && CurrentFile.get().getReleaseDependenciesFullFolders().size()>0;
		this.releaseDate = xmlConfig.getString("releaseDate");
		this.previousReleaseDate = xmlConfig.getString("previousReleaseDate");

		this.reducedSnapshotFolder = new File("reducedSnapshotFolder");
		if (!reducedSnapshotFolder.exists()){
			reducedSnapshotFolder.mkdirs();
		}
		this.previousReducedSnapshotFolder = new File("previousReducedSnapshotFolder");
		if (!previousReducedSnapshotFolder.exists()){
			previousReducedSnapshotFolder.mkdirs();
		}
		if (DependentFile.get()!=null){
			CurrentFile.get().setSnapshotExtensionLanguage(DependentFile.get().getSnapshotLanguageFile());
		}else{
			CurrentFile.get().setSnapshotExtensionLanguage(xmlConfig.getString("extensionLanguageSnapshotFile"));
		}
		List<HierarchicalConfiguration> fields = xmlConfig
				.configurationsAt("reports.reportDescriptor");

		if (fields!=null){
			for (HierarchicalConfiguration sub : fields) {

				String report=sub.getString("filename");
				String value=sub.getString("execute");

				if (value.toLowerCase().equals("true")){
					logger.info("Getting config for report " + report);
					ReportConfig reportCfg=ResourceUtils.getReportConfig(report);

					for (TABLE table:reportCfg.getInputFile()){
						switch (table) {
							case INFERREDROOTDESC:
								if (rootDesc) {
									continue;
								}
								rootDesc = true;
								break;
							case CONCEPTS:
								if (concepts) {
									continue;
								}
								concepts = true;
								break;
							case DESCRIPTIONS:
								if (descriptions) {
									continue;
								}
								descriptions = true;
								break;
							case DESCRIPTIONS_PREVIOUS:
								if (descriptions_pre) {
									continue;
								}
								descriptions_pre = true;
								break;
							case EXT_LANGUAGE:
								if (extLanguage) {
									continue;
								}
								extLanguage = true;

								break;
							case RELATIONSHIPS:
								if (relationships) {
									continue;
								}
								relationships = true;
								break;
							case STATEDRELS:
								if (statedRels) {
									continue;
								}
								statedRels = true;
								break;
							case OWLEXPRESS:
								if (owlExpress) {
									continue;
								}
								owlExpress = true;
								break;
							case TCLOSUREINFERRED:
								if (tClosureInferred) {
									continue;
								}
								tClosureInferred = true;
								break;
							case TCLOSURESTATED:
								if (tClosureStated) {
									continue;
								}
								tClosureStated = true;
								break;
							case CONCEPTS_PREVIOUS:
								if (concepts_pre) {
									continue;
								}
								concepts_pre = true;
								break;
							case RELATIONSHIPS_PREVIOUS:
								if (relationships_pre) {
									continue;
								}
								relationships_pre = true;
								break;
							case STATEDRELS_PREVIOUS:
								if (statedRels_pre) {
									continue;
								}
								statedRels_pre = true;
								break;
							case LANGUAGE_PREVIOUS:
								if (refsetId != null && !refsetId.equals("")) {
									Integer[] newfields = new Integer[]{2, 4, 6};
									String[] newValues = new String[]{"1", refsetId, "900000000000548007"};
									table.setFieldFilter(newfields);
									table.setFieldFilterValue(newValues);
								}
								if (lang_pre) {
									continue;
								}
								lang_pre = true;
								break;
							case LANGUAGE:
								if (refsetId != null && !refsetId.equals("")) {
									Integer[] newfields = new Integer[]{2, 4, 6};
									String[] newValues = new String[]{"1", refsetId, "900000000000548007"};
									table.setFieldFilter(newfields);
									table.setFieldFilterValue(newValues);
								}
								if (lang) {
									continue;
								}
								lang = true;
								break;
							case TCLOSUREINFERRED_PREVIOUS:
								if (tClosureInferred_pre) {
									continue;
								}
								tClosureInferred_pre = true;
								break;
							case SAME_AS_ASSOCIATIONS:
								if (same_associations) {
									continue;
								}
								same_associations = true;
								break;
						}
						ImportRf2Table(table);
					}
					System.out.println(report + " " + value);
				}
			}
		}
		logger.info("Updating date to " + releaseDate);
		saveNewDate(I_Constants.RELEASE_DATE, releaseDate);

		logger.info("Updating previous date to " + previousReleaseDate);
		saveNewDate(I_Constants.PREVIOUS_RELEASE_DATE, previousReleaseDate);

		fields = xmlConfig
				.configurationsAt("sp_params.param");

		params=new HashMap<String,String>();
		if (fields!=null){
			for (HierarchicalConfiguration sub : fields) {
				String paramName=sub.getString("name");
				String value=sub.getString("value");
				if (value.equals("$param")){
					value=xmlConfig.getString(paramName);
				}
				params.put(paramName, value);
			}
		}
		logger.info("End of import manager process");
	}


	/**
	 * Existing tables.
	 *
	 * @return true, if successful
	 */
	private boolean existingTables() {
		for(TABLE table:TABLE.values()){
			if (table.getPatternTag()!=null && !importer.tableExists(table, connection)){
				logger.info("Table " + table.getTableName() + " doesn't exist in db. Setup will create it.");

				return false;
			}
		}
		return true;

	}


	/**
	 * Import rf2 table.
	 *
	 * @param table the table
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	void ImportRf2Table( TABLE table) throws IOException, Exception{
		String newChk;
		String oldChk;
		File targetFolder;
		boolean recreate=false;
		String snapshotDate;
		if (table.isPrevious()){
			if (changedPreviousDate){
				recreate=true;
			}
			targetFolder=previousReducedSnapshotFolder;
			snapshotDate=previousReleaseDate;
		}else{
			if (changedDate){
				recreate=true;
			}
			targetFolder=reducedSnapshotFolder;
			snapshotDate=releaseDate;
		}
		String txt ="";
		logger.info("Evaluation if table " + table.getTableName() + " must be laoded.");
		if (!recreate){

			txt = FileHelper.getFile( targetFolder, table.getPatternTag(),table.getDefaultFolder(),table.getFileNameMustHave(),table.getFileNameDoesntMustHave(),table.isPrevious(),true);
			if (txt==null){
				txt=getSnapshot(table,targetFolder,snapshotDate);
				oldChk="";
			}else{
				oldChk= getOldCheck(table);
			}
			newChk=FileTest.getMD5Checksum(txt);
			if ( !newChk.equals(oldChk)){
				logger.info("Different checksum for file " + txt);

				importer.loadFileToDatabase(new File(txt), table, connection, null);
				saveNewCheck(table,newChk);
			}else{
				logger.info("Same data detected for table " + table.getTableName() + ". Skipping import process for it");
			}
		}else{
			logger.info("Regenerating table " + table.getTableName() );

			txt=getSnapshot(table,targetFolder,snapshotDate);
			newChk=FileTest.getMD5Checksum(txt);
			importer.loadFileToDatabase(new File(txt), table, connection, null);
			saveNewCheck(table,newChk);
		}
	}

	/**
	 * Save new check.
	 *
	 * @param table the table
	 * @param newChk the new chk
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void saveNewCheck(TABLE table, String newChk) throws IOException {
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		File file=new File(dataFolder, table.name() + ".dat");
		BufferedWriter bw = getWriter(file.getAbsolutePath());
		bw.append(newChk);
		bw.append("\r\n");
		bw.close();
		bw=null;
	}
	
	/**
	 * Save new date.
	 *
	 * @param fileName the file name
	 * @param date the date
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void saveNewDate(String fileName, String date) throws IOException {
		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		File file=new File(dataFolder, fileName + ".dat");
		BufferedWriter bw = getWriter(file.getAbsolutePath());
		bw.append(date);
		bw.append("\r\n");
		bw.close();
		bw=null;
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
	 * Gets the snapshot.
	 *
	 * @param table the table
	 * @param targetFolder the target folder
	 * @param snapshotDate the snapshot date
	 * @return the snapshot
	 * @throws Exception the exception
	 */
	private String getSnapshot(TABLE table,File targetFolder,String snapshotDate) throws Exception {
		logger.info("Getting snapshot for " + table.getTableName() );
		String[] outputFields=null;
		if (table.getPatternTag().equals("transitive-closure")){
			String txtTClos=table.getTableName() + ".txt";
			String isasFile=null;
			String isasFullFile;
			if (releaseDependencies){
				isasFullFile=CurrentFile.get().getCompleteRelationshipFull();
			}else{

				isasFullFile=CurrentFile.get().getRelationshipFile();
			}
			File isasTmpSnapshot=new File(targetFolder,"tmp_" + ((table.getFileNameMustHave()==null)? "":table.getFileNameMustHave()) + "relationships" + (table.isPrevious()?"_pre":"") + ".txt");
			File isasSnapshot=new File(targetFolder,((table.getFileNameMustHave()==null)? "":table.getFileNameMustHave()) + "relationships" + (table.isPrevious()?"_pre":"") + ".txt");
			logger.info("Getting snapshot from file " + isasFullFile);

			outputFields=new String[4];
			for (int i=0;i<outputFields.length;i++){
				outputFields[0]="sourceId";
				outputFields[1]="destinationId";
				outputFields[2]="relationshipGroup";
				outputFields[3]="typeId";
			}
			ConversionSnapshotDelta.snapshotFile(new File(isasFullFile), sortFolderTmp, sortedFolderTmp, isasTmpSnapshot, snapshotDate, new int[]{0,1}, 0, 1,null,null,null);
			ConversionSnapshotDelta.snapshotFile(isasTmpSnapshot, sortFolderTmp, sortedFolderTmp, isasSnapshot, snapshotDate, new int[]{0,1}, 0, 1,new Integer[]{2},new String[]{"1"},outputFields);
			isasFile=isasSnapshot.getAbsolutePath();
			TClosure tcf=new TClosure(isasFile,0,1,3,null);

			logger.info("Creating transitive closure from file " + isasFile);
			File tmpFile=new File(isasFile);
			txtTClos= tmpFile.getParent() + "/" + txtTClos;
			tcf.toFileFirstLevelHierarchy(txtTClos);
			
//			isasSnapshot.delete();
			isasTmpSnapshot.delete();
			tcf=null;
			return txtTClos;
		} 
		String txt ="";
		File txtFile;
		
		if (table.getPatternTag().equals("rf2-inferredrootdesc")){
			logger.info("Getting Top Level");
			if (!releaseDependencies){
				logger.info("Releases dependencies folders are null");
				txt=CurrentFile.get().getRelationshipFile();
				
			}else{
				txt = CurrentFile.get().getCompleteRelationshipFull();
			}
		}else if (table.getTableName().toLowerCase().equals("s_ext_languages")){
			txt=CurrentFile.get().getSnapshotExtensionLanguage();
		}else {
			txt = CurrentFile.get().getFullFileByPattern(table.getPatternTag(),table.getFileNameMustHave(),table.getFileNameDoesntMustHave());
		}
		if (txt==null || txt.equals("")){
			throw new Exception("Full source file not found for pattern:" + table.getPatternTag() );
		}
		txtFile=new File(targetFolder,table.getTableName() + ".txt");
		logger.info("Getting snapshot from file " + txt);
		if (table.getFields()!=null){
			outputFields=table.getFields().split(",");
			for (int i=0;i<outputFields.length;i++){
				outputFields[i]=outputFields[i].trim();
			}
		}
		if (table.getFieldFilter()!=null){
		
			File fileTxt=new File(txt);
			File txtTmpFile=new File(fileTxt.getParentFile(),"tmp_"+ fileTxt.getName());
			ConversionSnapshotDelta.snapshotFile(fileTxt, sortFolderTmp, sortedFolderTmp, txtTmpFile, snapshotDate, new int[]{0,1}, 0, 1, null, null, null);
			ConversionSnapshotDelta.snapshotFile(txtTmpFile, sortFolderTmp, sortedFolderTmp, txtFile, snapshotDate, new int[]{0,1}, 0, 1,table.getFieldFilter(),table.getFieldFilterValue(),outputFields);
			txtTmpFile.delete();
		}else{
			ConversionSnapshotDelta.snapshotFile(new File(txt), sortFolderTmp, sortedFolderTmp, txtFile, snapshotDate, new int[]{0,1}, 0, 1,null,null,outputFields);
		}
		if (table.getPatternTag().equals("rf2-inferredrootdesc")){
			logger.info("Getting Top Level terms");
			getDescriptionsForTopLevel(txtFile);
		}
		return txtFile.getAbsolutePath();
	}

	/**
	 * Gets the descriptions for top level.
	 *
	 * @param txtFile the txt file
	 * @return the descriptions for top level
	 * @throws Exception the exception
	 */
	private void getDescriptionsForTopLevel(File txtFile) throws Exception {
		String descFile;		
		if (releaseDependencies){
			descFile=CurrentFile.get().getCompleteDescriptionSnapshot();
		}else{
			descFile= CurrentFile.get().getSnapshotDescriptionFile();
		}
		BufferedReader br=FileHelper.getReader(txtFile);
		String header =br.readLine();
		String line;
		String[] spl;
		HashMap<String,String> mapTopLevel=new HashMap<String,String>();
		while((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			mapTopLevel.put(spl[0], "");
		}
		br.close();
		br=FileHelper.getReader(descFile);
		br.readLine();
		while((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (spl[2].equals("1") 
					&& mapTopLevel.containsKey(spl[4])){
				String tmp=mapTopLevel.get(spl[4]);
				if (tmp.equals("") ){
					mapTopLevel.put(spl[4], spl[7]);
				}else if(spl[6].equals("900000000000003001")){
					mapTopLevel.put(spl[4], spl[7]);
				}
			}
		}

		br.close();
		BufferedWriter bw = FileHelper.getWriter(txtFile);
		bw.append(header);
		bw.append("\r\n");
		for (String key:mapTopLevel.keySet()){
			bw.append(key);
			bw.append("\t");
			bw.append(mapTopLevel.get(key));
			bw.append("\r\n");

		}
		bw.close();
	}

	/**
	 * Gets the old check.
	 *
	 * @param table the table
	 * @return the old check
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String getOldCheck(TABLE table) throws IOException {

		if (!dataFolder.exists()){
			dataFolder.mkdirs();
		}
		File file=new File(dataFolder, table.name() + ".dat");
		if (file.exists()){
			FileInputStream rfis = new FileInputStream(file);
			InputStreamReader risr = new InputStreamReader(rfis,"UTF-8");
			BufferedReader rbr = new BufferedReader(risr);
			String ret=rbr.readLine();
			rbr.close();
			rbr=null;
			return ret;
		}
		return "";
	}

}
