package utils;

import java.util.Date;
import java.util.HashMap;

import request_handler.ServerResponse;

public class HttpHelper {
	/*
	 * Function which will form the response headers and body in case of an
	 * error. ErrorStr passed as a param will become the error message or body
	 * of the response.
	 * 
	 * @errorStr : the errorStr should be HTML style error msg string
	 */
	public static ServerResponse formHttpErrorResponse(
			ServerResponse outResponse, String errorStr, String statusCode) {

		outResponse.setProtocolVersion(HttpConstants.HTTP_Protocol_Version);
		outResponse.setStatusCode(statusCode);

		HashMap<String, String> responseHeaders = fillResponseHeader(
				errorStr.getBytes().length, HttpConstants.HTML);
		outResponse.setResponseHeaders(responseHeaders);
		outResponse.setBody(errorStr.getBytes());

		return outResponse;
	}

	/* Function to set the Date header in the header response map */
	public static HashMap<String, String> fillResponseHeader(
			long contentLength, String contentType) {
		HashMap<String, String> responseHeaders = new HashMap<>();

		responseHeaders.put("Date", new Date().toString());
		responseHeaders.put("Content-Length", String.valueOf(contentLength));
		responseHeaders.put("Content-Type", contentType);

		return responseHeaders;
	}

	//It creates an html msg from the string msg to write back to response.
	public static String formHtmlFromErrorMsg(String msg) {
		String errorStr = "<html><body> "+ msg+ " </body></html>";
		return errorStr;
	}
}
