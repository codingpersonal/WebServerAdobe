import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_SingleThread {

	public static void main(String[] args) throws IOException {
		ServerSocket server = null;
		try {
			server = new ServerSocket(HttpConstants.SERVER_PORT);
			System.out.println("Server listening on port"+HttpConstants.SERVER_PORT);
			
			Socket client = server.accept();
			if(client != null) {
				HttpRequest req = new HttpRequest(client);
			}
		} catch (IOException e) {
			System.err.println("Server cannot listen on 8080");
		} finally {
			System.out.println("Closing the server connection");
			server.close();
		}
	}
}
