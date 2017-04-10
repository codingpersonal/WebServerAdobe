package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;

import request_handler.IServerRequestHandler;
import request_handler.ServerRequest;
import request_handler.ServerResponse;
import request_handler.factory.IRequestHandlerFactory;
import utils.HttpHelper;
import utils.RequestParser;

// This class does the job of processing request from socket and operating on them
// This class acts as a bridge and reads and writes the request and response from socket
public class ServerWorker implements Runnable {

	private IRequestHandlerFactory reqHandlerFactory;
	private Socket socket;

	public ServerWorker(IRequestHandlerFactory inFactory, Socket inSocket) {
		this.reqHandlerFactory = inFactory;
		this.socket = inSocket;
	}

	@Override
	public void run() {
		try {
			ServerResponse outResponse = new ServerResponse();

			// obtain the input and output stream of the socket
			InputStream inp = socket.getInputStream();
			OutputStream op = socket.getOutputStream();

			// parse input request. This will create a ServerRequest object
			ServerRequest inRequest = RequestParser.parseRequest(inp);
			String errorMsg = inRequest.getErrorMsg();
			if (errorMsg == null
					|| errorMsg.length() > 0) {
				
				String msgContents[] = errorMsg.split(" ");
				outResponse = HttpHelper.formHttpErrorResponse(outResponse,
						HttpHelper.formHtmlFromErrorMsg(msgContents[1]),
						msgContents[0]);

			} else {

				// ask factory to create required handler
				IServerRequestHandler reqHandler = this.reqHandlerFactory
						.createRequestHandler(inRequest);

				// get response string from handler
				outResponse = reqHandler.processRequest(inRequest);
			}

			// convert the object to string format
			String strResponse = formHttpResponse(outResponse);

			// write back to socket
			PrintWriter writer = new PrintWriter(op);
			writer.write(strResponse);
			writer.flush();

			// close the input and output socket
			inp.close();
			op.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// convert the http response from obj to string
	// A string response is needed to write on the output stream
	private static String formHttpResponse(ServerResponse outResponse) {
		byte[] body = outResponse.getBody();
		HashMap<String, String> responseHeaders = outResponse
				.getResponseHeaders();

		String responseStr = outResponse.getProtocolVersion() + " "
				+ outResponse.getStatusCode() + "\n";
		for (Entry<String, String> key : (responseHeaders).entrySet()) {
			responseStr += key.getKey() + ": " + key.getValue() + "\n";
		}
		responseStr += "\r\n";
		if (body != null) {
			responseStr += new String(body);
		}
		return responseStr;
	}
}
