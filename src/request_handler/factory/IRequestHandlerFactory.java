package request_handler.factory;

import request_handler.IServerRequestHandler;

public interface IRequestHandlerFactory {

	// based on the request type, it will return the request handler. It will look at following information: 
	// 1. Get/post/delete/update
	public IServerRequestHandler createRequestHandler();
}
