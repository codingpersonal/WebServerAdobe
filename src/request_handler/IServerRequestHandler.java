package request_handler;

import java.io.IOException;

import error_handling.ServerRequestException;

public interface IServerRequestHandler {
	public ServerResponse processRequest(ServerRequest inRequest) throws ServerRequestException, IOException;
}
