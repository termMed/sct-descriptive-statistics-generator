package com.termmed.fileprovider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.termmed.utils.FileHelper;

public class DependentFileProvider extends FileProvider {

	private HashSet<Long>referencedConcepts;

	private HashSet<Long> referencedDescriptions;

	private HashMap<Long,List<String>> referencedStatedTargets;

	private HashMap<Long,List<String>> referencedSimpleRefsets;

	public DependentFileProvider(File sourceFullFolder, File baseFolder,
			String releaseDate) {
		super(sourceFullFolder, baseFolder, null, releaseDate);
	}

	private void getReferencedComponents() throws IOException, Exception {
		referencedConcepts=new HashSet<Long>();
		referencedDescriptions=new HashSet<Long>();
		referencedStatedTargets=new HashMap<Long,List<String>>();
		referencedSimpleRefsets=new HashMap<Long,List<String>>();

		HashMap<Long,String>concepts=new HashMap<Long,String>();
		String file=getSnapshotConceptFile();
		BufferedReader br;
		String line;
		String[] spl;
		if (file!=null){
			br=FileHelper.getReader(file);
			br.readLine();
			while ((line=br.readLine())!=null){
				spl=line.split("\t",-1);
				concepts.put(Long.parseLong(spl[0]),null);
			}
			br.close();
		}
		HashMap<Long,Long>descriptions=new HashMap<Long,Long>();
		file=getSnapshotDescriptionFile();
		if (file!=null){
			br=FileHelper.getReader(file);
			br.readLine();
			while ((line=br.readLine())!=null){
				spl=line.split("\t",-1);
				Long cid=Long.parseLong(spl[4]);
				if (spl[6].equals("900000000000003001") && spl[2].equals("1")){
					concepts.put(cid,spl[7]);
				}
				descriptions.put(Long.parseLong(spl[0]),cid);
			}
			br.close();
		}
		file=getSnapshotLanguageFile();
		if (file!=null){
			br=FileHelper.getReader(file);
			br.readLine();
			while ((line=br.readLine())!=null){
				spl=line.split("\t",-1);
				Long ref=Long.parseLong(spl[5]);
				Long cid=descriptions.get(ref);
				if (cid==null){

					referencedDescriptions.add(ref);

				}else{

					referencedConcepts.add(cid);

				}
			}
			br.close();
		}
		System.out.println("Concept referenced:" + referencedConcepts.size());
		System.out.println("Desc referenced:" + referencedDescriptions.size());
		file=getSnapshotStatedRelationshipFile();
		if (file!=null){
			br=FileHelper.getReader(file);
			br.readLine();
			while ((line=br.readLine())!=null){
				spl=line.split("\t",-1);
				if(spl[2].equals("1")){
					Long ref=Long.parseLong(spl[5]);

					String fsn=concepts.get(Long.parseLong(spl[4]));

					List<String> list=referencedStatedTargets.get(ref);
					if (list==null){
						list=new ArrayList<String>();
					}
					list.add(spl[4] + "|" + fsn);
					referencedStatedTargets.put(ref,list);
				}
			}
			br.close();
		}
		HashSet<String>files=getSnapshotRefsetSimpleFiles();
		if (files!=null){
			for (String fil:files){
				br=FileHelper.getReader(fil);
				br.readLine();
				String refsetName=null;
				while ((line=br.readLine())!=null){
					spl=line.split("\t",-1);
					Long ref=Long.parseLong(spl[5]);
					refsetName=concepts.get(Long.parseLong(spl[4]));
					List<String> list=referencedSimpleRefsets.get(ref);
					if (list==null){
						list=new ArrayList<String>();
					}
					list.add(spl[4] + "|" + refsetName);
					referencedSimpleRefsets.put(ref,list);
				}
				br.close();
			}
		}

		concepts=null;
		descriptions=null;
	}
	public HashSet<Long> getReferencedConcepts() throws IOException, Exception {
		if (referencedConcepts==null){
			getReferencedComponents();
		}
		return referencedConcepts;
	}


	public void setReferencedConcepts(HashSet<Long> referencedConcepts) {
		this.referencedConcepts = referencedConcepts;
	}

	public HashSet<Long> getReferencedDescriptions() throws IOException, Exception {
		if (referencedDescriptions==null){
			getReferencedComponents();
		}
		return referencedDescriptions;
	}

	public void setReferencedDescriptions(HashSet<Long> referencedDescriptions) {
		this.referencedDescriptions = referencedDescriptions;
	}

	public HashMap<Long, List<String>> getReferencedStatedTargets() throws IOException, Exception {
		if (referencedStatedTargets==null){
			getReferencedComponents();
		}
		return referencedStatedTargets;
	}

	public void setReferencedStatedTargets(HashMap<Long, List<String>> referencedStatedTargets) {
		this.referencedStatedTargets = referencedStatedTargets;
	}

	public HashMap<Long, List<String>> getReferencedSimpleRefsets() {
		return referencedSimpleRefsets;
	}

	public void setReferencedSimpleRefsets(HashMap<Long, List<String>> referencedSimpleRefsets) {
		this.referencedSimpleRefsets = referencedSimpleRefsets;
	}
}
