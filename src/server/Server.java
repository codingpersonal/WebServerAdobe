package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import request_handler.IServerRequestHandler;

public class Server implements Runnable {

	private ServerSocket serverSocket;
	private ExecutorService threadPoolSvc;
	private ServerConfigurations serverConfig;
	
	public static void main(String[] args) throws IOException {
//		new Thread(new Server()).start();
	}

	public Server(ServerConfigurations config) {
		this.serverConfig = config;
	}

	@Override
	public void run() {
		try {
			//keeping the port as 8080 
			serverSocket = new ServerSocket(this.serverConfig.serverPort);
			
			//keeping a max count of 10 threads
			threadPoolSvc = Executors.newFixedThreadPool(this.serverConfig.maxThreadCount);
		} catch (IOException e) {
			System.err.println("Cannot listen on port " + this.serverConfig.serverPort);
			System.exit(1);
		}
		
		System.out.println("Server Listening on port: "+ this.serverConfig.serverPort);
		
		//Loop until the main thread is not interupted
		while (!Thread.interrupted()) {
			try {
				// TODO: get the request handler from factory object
				IServerRequestHandler reqHandler = this.serverConfig.handlerFactory.createRequestHandler();
//				threadPoolSvc.execute(new HttpRequestHandler(serverSocket.accept()));
				threadPoolSvc.execute(reqHandler);
			} catch (IOException e) {
				System.err.println("Cannot accept requests from client.");
			}
		}
		
		//closing the server socket now
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		threadPoolSvc.shutdown();
		
		//wait for 20 more seconds so that tasks which are in execution also get completed before termination
		try {
			if (!threadPoolSvc.awaitTermination(20, TimeUnit.SECONDS)) 
				threadPoolSvc.shutdownNow();
		} catch (InterruptedException e) {}
	}
}
