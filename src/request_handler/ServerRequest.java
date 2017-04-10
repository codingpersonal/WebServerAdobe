package request_handler;

import java.util.HashMap;

public class ServerRequest {
	public String method;
	public String request_uri;
	public String http_version;

	// it will store the http request header params after parsing
	public HashMap<String, String> headerParams = new HashMap<>();

}
