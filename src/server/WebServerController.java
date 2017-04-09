package server;

import java.io.IOException;

import request_handler.factory.HttpRequestHandlerFactory;
import utils.HttpConstants;

/*
 * This is the main server controller class which should initialize different types of
 * servers based on deployment configurations. e.g. on certain machines, one can start http Server and 
 * on some machines https server can be started. 
 */
public class WebServerController {
	
	public static void main(String[] args) throws IOException {
		/*
		 * One can use spring framework to get the required
		 * configurations for server to start
		 */

		//1. To start http server 
		ServerConfigurations httpServerConfig = new ServerConfigurations();
		httpServerConfig.serverPort = HttpConstants.HTTP_SERVER_PORT;
		httpServerConfig.maxThreadCount = HttpConstants.MAX_THREAD;
		httpServerConfig.handlerFactory = new HttpRequestHandlerFactory();
		new Thread(new Server(httpServerConfig)).start();
	}

}
