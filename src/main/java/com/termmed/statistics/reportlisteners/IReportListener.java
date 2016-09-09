package com.termmed.statistics.reportlisteners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.termmed.statistics.model.Filter;

public interface IReportListener {

	void actionPerform(String reportline) throws IOException, Exception;
	void setReportFilter(Filter reportFilter);
	void setReportFile(File reportFile) throws UnsupportedEncodingException, FileNotFoundException, IOException;
	void finalizeListener() throws IOException;
}
