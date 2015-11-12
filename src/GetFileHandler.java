import java.io.File;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.IRequestHandler;
import protocol.Protocol;

public class GetFileHandler implements IRequestHandler {

	@Override
	public HttpResponse handleRequest(HttpRequest request, String rootDir) {
		HttpResponse response = null;
		
		String[] uri = request.getUri().split("/");
		String fileName = "";
		if (uri.length == 3){
			fileName = uri[2];
		}
		else{
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
		String resourcesDir = "./FileManagerResources/";
		File file = new File(resourcesDir + fileName);
		if (file.exists() && !file.isDirectory()){
			response = HttpResponseFactory.create200OK(file, Protocol.CLOSE);
		}else{
			response = HttpResponseFactory.create404NotFound(Protocol.CLOSE);
		}
		return response;
	}
}
