package request_handler;

import java.util.HashMap;

public class ServerRequest {
	private String method;
	private String request_uri;
	private String http_version;

	// it will store the http request header params after parsing
	private HashMap<String, String> headerParams = new HashMap<>();

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getHttp_version() {
		return http_version;
	}

	public void setHttp_version(String http_version) {
		this.http_version = http_version;
	}

	public String getRequest_uri() {
		return request_uri;
	}

	public void setRequest_uri(String request_uri) {
		this.request_uri = request_uri;
	}

	public HashMap<String, String> getHeaderParams() {
		return headerParams;
	}

	public void setHeaderParams(HashMap<String, String> headerParams) {
		this.headerParams = headerParams;
	}

}
