package com.termmed.statistics.reportlisteners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import com.termmed.fileprovider.DependentFile;
import com.termmed.statistics.model.Filter;
import com.termmed.utils.FileHelper;

public class ChangedDescriptionsInLangRefset implements IReportListener {



	private BufferedWriter bw;
	private Filter reportFilter;

	@Override
	public void actionPerform(String reportline) throws Exception {

		if (bw!=null){
			String[] spl=reportline.split(",",-1);
			if (reportFilter!=null){
				if (spl[reportFilter.getFieldIndex()].equals(reportFilter.getFieldValue())){
					writeLines(reportline,spl);
				}
			} else
				writeLines(reportline, spl);
		}		
	}

	private void writeLines(String reportline, String[] spl) throws IOException, Exception {

		Long cid=Long.parseLong(spl[1]);
		Long did=Long.parseLong(spl[4]);
		if (DependentFile.get().getReferencedConcepts().contains(cid)
			||DependentFile.get().getReferencedDescriptions().contains(did)){
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
		bw.append("Hierarchy,Id,Term,ChangeType,DescriptionId,Priority");
		bw.append("\r\n");
	}

	@Override
	public void finalizeListener() throws IOException {
		bw.close();
	}
	
	
}
