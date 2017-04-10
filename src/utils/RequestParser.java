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
				throw new ServerRequestException(400,
						"Bad Request, Less than 3 request lines received.");
			} else {
				ret.setMethod(requestLineContents[0]);
				ret.setRequest_uri(requestLineContents[1]);
				ret.setHttp_version(requestLineContents[2]);

				// proceed only if the valid values are received in the request
				if (!ret.getHttp_version().startsWith(HttpConstants.HTTP_Protocol_Version)) {
					throw new ServerRequestException(505,
							"Http Version not supported, Version should be 1.1");
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
			throw new ServerRequestException(-1, e.getMessage());
		}
		return ret;
	}
}
