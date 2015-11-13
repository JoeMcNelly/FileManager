import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.gson.Gson;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.IRequestHandler;
import protocol.Protocol;

public class PostFileHandler implements IRequestHandler {
	/*
	 * (non-Javadoc)
	 * 
	 * @see protocol.IRequestHandler#handleRequest(protocol.HttpRequest,
	 * java.lang.String)
	 */
	@Override
	public HttpResponse handleRequest(HttpRequest request, String rootDir) {
		HttpResponse response = null;
		String[] uri = request.getUri().split("/");
		String fileName = "";
		if (uri.length == 4){
			fileName = uri[3];
		}
		File file = new File(rootDir + fileName);
		if (file.exists()) {
			if (file.isDirectory()) {
				response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
			} else {
				Gson gson = new Gson();
				String json = new String(request.getBody());
				String bodyContents = gson.fromJson(json, String.class);
				response = appendToFile(bodyContents, file);
			}
		} else {
			Gson gson = new Gson();
			String json = new String(request.getBody());
			String bodyContents = gson.fromJson(json, String.class);
			response = appendToFile(bodyContents, file);
		}
		return response;
	}

	public HttpResponse appendToFile(String body, File file) {
		try {
			FileOutputStream writer = new FileOutputStream(file, true);
			String contents = new String(body);
			writer.write(contents.getBytes());
			writer.close();
		} catch (IOException e) {
			//e.printStackTrace();
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
		return HttpResponseFactory.create200OK(file, Protocol.CLOSE);
	}
}
