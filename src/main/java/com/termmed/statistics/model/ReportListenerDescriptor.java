package com.termmed.statistics.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportListenerDescriptor implements IReportDetail{
	String executionClass;
	String file;
	Filter filter;
	Integer excluyentListPriority;
	private Integer priorityListColumnIndex;
	private Integer sctIdIndex;
	public String getExecutionClass() {
		return executionClass;
	}
	public void setExecutionClass(String executionClass) {
		this.executionClass = executionClass;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Filter getFilter() {
		return filter;
	}
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	public Integer getExcluyentListPriority() {
		return excluyentListPriority;
	}
	public void setExcluyentListPriority(Integer excluyentListPriority) {
		this.excluyentListPriority = excluyentListPriority;
	}
	@Override
	public Integer getSctIdIndex() {
		return this.sctIdIndex;
	}
	public void setSctIdIndex(Integer sctIdIndex) {
		this.sctIdIndex = sctIdIndex;
	}
	@Override
	public Integer getPriorityListColumnIndex() {
		return this.priorityListColumnIndex ;
	}
	public void setPriorityListColumnIndex(Integer priorityListColumnIndex) {
		this.priorityListColumnIndex = priorityListColumnIndex;
	}
}
