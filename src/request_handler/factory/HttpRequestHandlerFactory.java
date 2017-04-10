package request_handler.factory;

import request_handler.HttpGetRequestHandler;
import request_handler.IServerRequestHandler;
import request_handler.ServerRequest;
import utils.HttpConstants;

public class HttpRequestHandlerFactory implements IRequestHandlerFactory {
	public IServerRequestHandler createRequestHandler(ServerRequest inRequest) {
		String httpMethod = inRequest.method;
		
		if(httpMethod.equals(HttpConstants.HTTP_GET)) {
			return new HttpGetRequestHandler();
		} else if(httpMethod.equals(HttpConstants.HTTP_POST)) {
			//return some POST handler object
			//returning null since POST is not handled
			return null;
		} else {
			return null;
		}
	}
}
