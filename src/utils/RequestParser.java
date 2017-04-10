/**
 * This class parses the request and creates the ServerReuest object.
 * For now, it is parsing the HTTP request.
 */
package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import error_handling.ServerRequestException;
import error_handling.WebServerLogger;
import request_handler.ServerRequest;

public class RequestParser {

	public static ServerRequest parseRequest(InputStream inp)
			throws ServerRequestException {
		ServerRequest ret = new ServerRequest();
		BufferedReader br = new BufferedReader(new InputStreamReader(inp));
		try {

			// read the first request line
			String requestLine = br.readLine();
			String requestLineContents[] = requestLine.split(" ");
			
			if (requestLineContents.length != 3) {
				// send 404 bad request in the response
				WebServerLogger.logErrorMsg(HttpStatusCodes.STATUS_404);
				ret.setErrorMsg(HttpStatusCodes.STATUS_404);
				
			} else {
				ret.setMethod(requestLineContents[0]);
				ret.setRequest_uri(requestLineContents[1]);
				ret.setHttp_version(requestLineContents[2]);

				// proceed only if the valid values are received in the request
				if (!ret.getHttp_version().startsWith(HttpConstants.HTTP_Protocol_Version)) {
					
					//send 505 protocol version not supported in response
					WebServerLogger.logErrorMsg(HttpStatusCodes.STATUS_505);
					ret.setErrorMsg(HttpStatusCodes.STATUS_505);
				}
			}

			// read the headers now in the next lines.
			String headerLine = null;
			
			while ((headerLine = br.readLine()).length() != 0) {
				String Parampair[] = headerLine.split(":");
				ret.getHeaderParams().put(Parampair[0], Parampair[1]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
