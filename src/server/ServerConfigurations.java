package server;

import request_handler.factory.IRequestHandlerFactory;

// To initialize a server with certain configurations
public class ServerConfigurations {
	
	// port, num of threads, factory etc.
	private int serverPort;
	
	private int maxThreadCount;
	
	public IRequestHandlerFactory handlerFactory;
	
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public int getMaxThreadCount() {
		return maxThreadCount;
	}
	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}
}
