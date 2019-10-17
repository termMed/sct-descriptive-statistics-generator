package com.termmed.statistics.reportlisteners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import com.termmed.fileprovider.DependentFile;
import com.termmed.statistics.model.Filter;
import com.termmed.utils.FileHelper;

public class ReactConceptDescriptionsInLangRefset implements IReportListener {



	private BufferedWriter bw;
	private Filter reportFilter;

	@Override
	public void actionPerform(String reportline) throws Exception {

		if (bw!=null){
			String[] spl=reportline.split(",",-1);
			Long cid=Long.parseLong(spl[1]);
			if (reportFilter!=null){
				if (spl[reportFilter.getFieldIndex()].equals(reportFilter.getFieldValue())){
					writeLines(reportline, cid);
				}
			} else
				writeLines(reportline, cid);
		}		
	}

	private void writeLines(String reportline, Long cid) throws IOException, Exception {
		if (DependentFile.get().getReferencedConcepts().contains(cid)){
			bw.append(reportline);
			bw.append("\r\n");
		}
	}

	@Override
	public void setReportFilter(Filter reportFilter) {
		this.reportFilter=reportFilter;

	}

	@Override
	public void setReportFile(File reportFile) throws IOException {

		bw=FileHelper.getWriter(reportFile);	
		addHeader(bw);
	}

	private void addHeader(BufferedWriter bw2) throws IOException {
		bw.append("Hierarchy,Id,Term,DefinitionStatus");
		bw.append("\r\n");
	}

	@Override
	public void finalizeListener() throws IOException {
		bw.close();
	}
	
	
}
