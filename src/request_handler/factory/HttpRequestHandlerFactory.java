package request_handler.factory;

import request_handler.HttpRequestHandler;
import request_handler.IServerRequestHandler;

public class HttpRequestHandlerFactory implements IRequestHandlerFactory {
	public IServerRequestHandler createRequestHandler() {
		// TODO: do basic parsing of http request here
		return new HttpRequestHandler(null);
	}
}
