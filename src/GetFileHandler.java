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
		if (uri.length == 4){
			fileName = uri[3];
		}
		else if (uri.length == 3){
			return HttpResponseFactory.create200OK(FileManager.getInstance().getBaseHTML(), Protocol.CLOSE);
		}else{
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
		String resourcesDir = "./FileManagerResources/";
		File file = new File(resourcesDir + fileName);
		if (file.exists() && !file.isDirectory()){
			response = HttpResponseFactory.create200OK(file, Protocol.CLOSE);
		}else{
			String dir = System.getProperty("user.dir");
			File file404 = new File(dir + System.getProperty("file.separator") + "web"
					+ System.getProperty("file.separator") + "404message.txt"
					+ System.getProperty("file.separator"));
			response = HttpResponseFactory.create200OK(file404, Protocol.CLOSE);
		}
		return response;
	}
}
