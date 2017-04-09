package server;

import request_handler.factory.IRequestHandlerFactory;

// To initialize a server with certain configurations
public class ServerConfigurations {
	// port, num of threads, factory etc.
	public int serverPort;
	public int maxThreadCount;
	public IRequestHandlerFactory handlerFactory;
}
