import java.io.File;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.IRequestHandler;
import protocol.Protocol;


public class DeleteFileHandler implements IRequestHandler{
	private static final String DIR = "./FileManagerResources/";
	
	/* (non-Javadoc)
	 * @see protocol.IRequestHandler#handleRequest(protocol.HttpRequest, java.lang.String)
	 */
	@Override
	public HttpResponse handleRequest(HttpRequest request, String rootDir) {

		HttpResponse response = null;
		String[] uri = request.getUri().split("/");
		String fileName = "";
		if (uri.length == 4){
			fileName = uri[3];
		}
		// Get root directory path from server
		// Combine them together to form absolute file path
		File file = new File(DIR + fileName);
		// Check if the file exists
		if(file.exists()) {
			if(file.isDirectory()) {
				response = HttpResponseFactory.create404NotFound(Protocol.CLOSE);
			}
			else { // Its a file
				// Lets create 200 OK response
				file.delete();
				response = HttpResponseFactory.create200OK(null, Protocol.CLOSE);
			}
		}
		else {
			// File does not exist so lets create 404 file not found code
			response = HttpResponseFactory.create404NotFound(Protocol.CLOSE);
		}
		return response;
	}

}
