package com.dxt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateTimeAdapter extends XmlAdapter<String, Date> {
	private final static SimpleDateFormat format = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	@Override
	public String marshal(Date v) throws Exception {
		try {
			return format.format(v);
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		try {
			return format.parse(v);
		} catch (Exception ex) {
			return null;
		}
	}
}
