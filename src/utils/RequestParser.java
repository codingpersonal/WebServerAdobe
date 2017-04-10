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
				throw new ServerRequestException(-1,
						"Invalid Request Received, Less than 3 request lines received.");
			} else {
				ret.method = requestLineContents[0];
				ret.request_uri = requestLineContents[1];
				ret.http_version = requestLineContents[2];

				// proceed only if the valid values are received in the request
				if (!ret.http_version.startsWith(HttpConstants.HTTP_Protocol_Version)) {
					throw new ServerRequestException(-1,
							"Invalid Http Version, Version should be 1.1");
				}
			}

			// read the headers now in the next lines.
			String headerLine = null;
			while ((headerLine = br.readLine()).length() != 0) {
				String Parampair[] = headerLine.split(":");
				ret.headerParams.put(Parampair[0], Parampair[1]);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new ServerRequestException(-1, e.getMessage());
		}
		return ret;
	}
}
