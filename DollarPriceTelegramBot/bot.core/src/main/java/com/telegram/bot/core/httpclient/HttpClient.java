package com.telegram.bot.core.httpclient;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telegram.bot.core.httpclient.beans.HttpGetRequest;

public class HttpClient {

	final static Logger logger = LoggerFactory.getLogger(HttpClient.class);

	private ResponseHandler<String> responseHandler = new ResponseHandlerImpl();

	public String GetRequestJson(HttpGetRequest request) throws Exception {
		String response = "";

		logger.debug("Invoking WS with URL {}", request.getURL());

		HttpGet httpget = new HttpGet(request.getURL());
		httpget.addHeader("accept", "application/json");

		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(
				SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build())).build();
		response = httpclient.execute(httpget, responseHandler);

		return response;
	}
}
