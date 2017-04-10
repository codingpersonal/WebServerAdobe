package request_handler;

import error_handling.ServerRequestException;

public interface IServerRequestHandler {
	public ServerResponse processRequest(ServerRequest inRequest) throws ServerRequestException;
}
