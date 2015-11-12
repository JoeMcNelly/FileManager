import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
		String uri = request.getUri();
		File file = new File(rootDir + uri);
		if (file.exists()) {
			if (file.isDirectory()) {
				response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
			} else {
				response = appendToFile(new String(request.getBody()), file);
			}
		} else {
			response = appendToFile(new String(request.getBody()), file);
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
