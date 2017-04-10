package request_handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import error_handling.WebServerLogger;
import utils.HttpConstants;
import utils.HttpHelper;
import utils.HttpStatusCodes;

public class HttpGetRequestHandler implements IServerRequestHandler {

	// this method will be called from run to process the request
	public ServerResponse processRequest(ServerRequest inRequest)
			throws IOException {
		
		// if the request is valid, send the response back
		File outFile = new File(HttpConstants.RESOURCE_DIR
				+ inRequest.getRequest_uri());
		ServerResponse response = formHttpResponse(outFile);
		
		return response;
	}

	/* This function will form the http response - error or success */
	public ServerResponse formHttpResponse(File outFile) {
		ServerResponse response = new ServerResponse();

		if (outFile == null) {
			WebServerLogger.logErrorMsg(HttpStatusCodes.STATUS_400);
			String errorMsg = HttpHelper
					.formHtmlFromErrorMsg("Bad Protocol Request, Either Version or the Method not supported");
		
			return HttpHelper.formHttpErrorResponse(response, errorMsg,
					HttpStatusCodes.STATUS_400);

		} else {
			if (outFile.isFile()) {
				response.setStatusCode(HttpStatusCodes.STATUS_SUCCESS);
				return sendHttpSuccessResponseWithFile(response, outFile);
				
			} else {
				WebServerLogger.logErrorMsg(HttpStatusCodes.STATUS_404);
				String msgStr = "File " + outFile.getName() + " not found";
				String errorMsg = HttpHelper.formHtmlFromErrorMsg(msgStr);
				
				return HttpHelper.formHttpErrorResponse(response, errorMsg,
						HttpStatusCodes.STATUS_404);
			}
		}
	}

	/*
	 * Function which will form the response headers and body in case of an
	 * success. The contents of the outFile passed as a parameter will be the
	 * body of the response
	 */
	private ServerResponse sendHttpSuccessResponseWithFile(
			ServerResponse response, File outFile) {
		try {
			FileInputStream reader = new FileInputStream(outFile);

			int contentLength = reader.available();
			String fileName = outFile.getName();

			// set the content type based on the file extension
			String contentType = "";
			
			if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
				contentType = HttpConstants.HTML;
			} else if (fileName.endsWith(".txt")) {
				contentType = HttpConstants.TEXT;
			} else {
				String errorMsg = HttpHelper
						.formHtmlFromErrorMsg("File extension is not supported.");
				response = HttpHelper.formHttpErrorResponse(response, errorMsg,
						HttpStatusCodes.STATUS_415);
				reader.close();
			
				return response;
			}

			// fill the response headers.
			HashMap<String, String> responseHeaders = HttpHelper
					.fillResponseHeader(contentLength, contentType);
			response.setResponseHeaders(responseHeaders);

			// read the contents of the file in the body
			byte[] body = new byte[contentLength];
			reader.read(body);

			// set the body as well
			response.setBody(body);
			response.setProtocolVersion(HttpConstants.HTTP_Protocol_Version);

			reader.close();

		} catch (IOException e) {
			WebServerLogger
					.logErrorMsg("Exception caught while reading from file" + e);
		}

		return response;
	}

}
