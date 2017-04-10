package error_handling;

public class WebServerLogger {

	// This method will print the exception code and error msg
	public static void logException(ServerRequestException ex) {
		if (ex.httpCode != 1) {
			System.err.println("HTTP Error Code:" + ex.httpCode + " Error Msg:"
					+ ex.errMsg);
		} else {
			System.err.println("Error Msg:" + ex.errMsg);
		}
	}
	
	public static void logErrorMsg(String errMsg) {
		System.err.println("Error Msg:"+errMsg);
	}
}
