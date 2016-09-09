package com.termmed.statistics.reportlisteners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.termmed.fileprovider.DependentFile;
import com.termmed.statistics.model.Filter;
import com.termmed.utils.FileHelper;

public class ChangedDescriptionsInStatedTargets implements IReportListener {



	private BufferedWriter bw;
	private Filter reportFilter;

	@Override
	public void actionPerform(String reportline) throws Exception {

		if (bw!=null){
			String[] spl=reportline.split(",",-1);
			Long cid=Long.parseLong(spl[1]);
			if (reportFilter!=null){
				if (spl[reportFilter.getFieldIndex()].equals(reportFilter.getFieldValue())){
					writeLines(spl, cid);
				}
			} else
				writeLines(spl, cid);
		}		
	}

	private void writeLines(String[] spl, Long cid) throws Exception {
		if (DependentFile.get().getReferencedStatedTargets().containsKey(cid)){
			List<String> sources=DependentFile.get().getReferencedStatedTargets().get(cid);
			for (String source : sources){
				bw.append(spl[0]);
				bw.append(",");
				bw.append(spl[1]);
				bw.append(",");
				bw.append(spl[2]);
				bw.append(",");
				bw.append(source);
				bw.append("\r\n");
			}
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
		bw.append("Hierarchy,Id,Term,Source");
		bw.append("\r\n");
	}

	@Override
	public void finalizeListener() throws IOException {
		bw.close();
	}
	
	
}
