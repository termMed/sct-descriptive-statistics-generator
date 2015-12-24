package com.termmed.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.termmed.statistics.model.ReportConfig;

public class XmlMapUtil {


public static ReportConfig getReportConfigFromResource(String fileName) {

	ReportConfig config = null;
	try {

		JAXBContext context = JAXBContext.newInstance(ReportConfig.class);
		Unmarshaller u = context.createUnmarshaller();

		InputStream is = XmlMapUtil.class.getResourceAsStream(fileName);

		config = (ReportConfig) u.unmarshal(is);

	} catch (JAXBException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return config;
}

public static ReportConfig getReportConfigFromFileSystem(String fileName) {

	ReportConfig config = null;
	try {

		JAXBContext context = JAXBContext.newInstance(ReportConfig.class);
		Unmarshaller u = context.createUnmarshaller();

		FileInputStream rfis = new FileInputStream(fileName);
		InputStreamReader is = new InputStreamReader(rfis,"UTF-8");

		config = (ReportConfig) u.unmarshal(is);

	} catch (JAXBException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return config;
}

}