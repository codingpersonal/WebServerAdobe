package error_handling;

// custom class to represent any exception while processing http requests
public class ServerRequestException extends Exception{
	/**
	 * auto-generated
	 */
	private static final long serialVersionUID = -5045375210575511396L;

	public int httpCode;
	public String errMsg;
	
	public ServerRequestException(int httpCode, String errMsg) {
		this.httpCode = httpCode;
		this.errMsg = errMsg;
	}
	
	
}
