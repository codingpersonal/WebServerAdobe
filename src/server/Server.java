package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {

	private ServerSocket serverSocket;
	private ExecutorService threadPoolSvc;
	private ServerConfigurations serverConfig;
	
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
			e.printStackTrace();
			System.exit(10);			// some exception. ideally, it should exit gracefully
		}
		
		System.out.println("Server Listening on port: "+ this.serverConfig.serverPort);
		
		//Loop until the main thread is not interupted
		while (!Thread.interrupted()) {
			// keep getting the requests on socket
			Socket inReqSocket;
			try {
				inReqSocket = serverSocket.accept(); 					// blocking call
				threadPoolSvc.execute(new ServerWorker(this.serverConfig.handlerFactory, inReqSocket));
			} catch (IOException e) {
				e.printStackTrace();
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
