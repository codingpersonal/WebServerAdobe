package request_handler;

import java.util.HashMap;

public class ServerResponse {
	private String protocolVersion;
	private String statusCode;
	private HashMap<String, String>responseHeaders = new HashMap<>();
	private byte[] body = null;
	private String errStr;
	
	public String getProtocolVersion() {
		return protocolVersion;
	}
	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public byte[] getBody() {
		return body;
	}
	public void setBody(byte[] body) {
		this.body = body;
	}
	public HashMap<String, String> getResponseHeaders() {
		return responseHeaders;
	}
	public void setResponseHeaders(HashMap<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
	public String getErrStr() {
		return errStr;
	}
	public void setErrStr(String errStr) {
		this.errStr = errStr;
	}
	
}
