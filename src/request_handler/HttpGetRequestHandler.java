package request_handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import utils.HttpConstants;

public class HttpGetRequestHandler implements IServerRequestHandler {

	// this method will be called from run to process the request
	public ServerResponse processRequest(ServerRequest inRequest)
			throws IOException {
		// if the request is valid, send the response back
		File outFile = new File(HttpConstants.RESOURCE_DIR
				+ inRequest.request_uri);
		ServerResponse response = formHttpResponse(outFile);
		return response;
	}

	/* This function will form the http response - error or success */
	public ServerResponse formHttpResponse(File outFile) {
		ServerResponse response = new ServerResponse();

		if (outFile == null) {
			System.err
					.println("Invalid HttpRequest, sending back error response");
			String errorStr = "<html><body> Bad Protocol Request, Either Version or the Method not supported </body></html>";
			response.setStatusCode(HttpStatusCodes.STATUS_400);
			return sendHttpErrorResponse(response, errorStr);
		} else {
			if (outFile.isFile()) {
				response.setStatusCode(HttpStatusCodes.STATUS_SUCCESS);
				return sendHttpSuccessResponseWithFile(response, outFile);
			} else {
				System.err.println("Invalid File name or location");
				String errorStr = "<html><body>File " + outFile
						+ " not found.</body></html>";
				response.setStatusCode(HttpStatusCodes.STATUS_404);
				return sendHttpErrorResponse(response, errorStr);
			}

		}
	}

	/*
	 * Function which will form the response headers and body in case of an
	 * error. ErrorStr passed as a param will become the error message or body
	 * of the response
	 */
	private ServerResponse sendHttpErrorResponse(ServerResponse outResponse,
			String errorStr) {
		HashMap<String, String> responseHeaders = fillResponseHeader(
				errorStr.getBytes().length, HttpConstants.HTML);
		outResponse.setResponseHeaders(responseHeaders);
		outResponse.setBody(errorStr.getBytes());

		return outResponse;
	}

	/* Function to set the Date header in the header response map */
	public HashMap<String, String> fillResponseHeader(long contentLength,
			String contentType) {
		HashMap<String, String> responseHeaders = new HashMap<>();

		responseHeaders.put("Date", new Date().toString());
		responseHeaders.put("Content-Length", String.valueOf(contentLength));
		responseHeaders.put("Content-Type", contentType);

		return responseHeaders;
	}

	/*Function which will form the response headers and body in case of an success.
	 * The contents of the outFile passed as a parameter will be the body of the response*/
	private ServerResponse sendHttpSuccessResponseWithFile(ServerResponse response, File outFile) {
		try {
			FileInputStream reader = new FileInputStream(outFile);
			int contentLength = reader.available();
			
			//set the content type based on the file extension
			String contentType = "";
			if (outFile.getName().endsWith(".htm") || outFile.getName().endsWith(".html")) {
				contentType = HttpConstants.HTML;
			} else {
				contentType = HttpConstants.TEXT;
			}
			
			//fill the response headers.
			HashMap<String, String> responseHeaders = fillResponseHeader(
					contentLength, contentType);
			response.setResponseHeaders(responseHeaders);
			
			// read the contents of the file in the body
			byte[] body = new byte[contentLength];
			reader.read(body);
			
			//set the body as well
			response.setBody(body);
			
			reader.close();
		} catch (IOException e) {
			System.err.println("Exception caught while reading from file" + e);
		}
		
		return response;
	}

}
