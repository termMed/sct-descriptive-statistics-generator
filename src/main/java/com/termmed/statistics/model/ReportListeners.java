package com.termmed.statistics.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportListeners {

	List <ReportListenerDescriptor> reportListenerDescriptor;

	public List<ReportListenerDescriptor> getReportListenerDescriptor() {
		return reportListenerDescriptor;
	}

	public void setReportListener(List<ReportListenerDescriptor> reportListenerDescriptor) {
		this.reportListenerDescriptor = reportListenerDescriptor;
	}
}
