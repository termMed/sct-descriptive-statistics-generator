package com.termmed.statistics.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class SpParam {

	private static final long serialVersionUID = 4L;
	String name;
	int order;
	String sqlType;
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getSQLType() {
		return sqlType;
	}
	public void setSQLType(String sQLType) {
		sqlType = sQLType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
