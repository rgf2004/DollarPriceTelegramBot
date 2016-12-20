package com.telegram.bot.core.httpclient.beans;

import java.util.HashMap;
import java.util.Map;


public class HttpGetRequest {

	private String URL;

	private Map<String, String> headers = new HashMap<String, String>();

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

}
