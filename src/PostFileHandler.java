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
	private static final String WORK_DIR = System.getProperty("user.dir");
	private static final String DIR_PATH = System.getProperty("file.separator")
			+ "FileManagerResources" + System.getProperty("file.separator");
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
		File file = new File(WORK_DIR + DIR_PATH +fileName);
		System.out.println(file.getAbsolutePath());
		if (file.exists()) {
			System.out.println(file.getAbsolutePath());
			if (file.isDirectory()) {
				System.out.println("hello");
				response = HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
			} else {
				System.out.println("else");
				Gson gson = new Gson();
				System.out.println("before json");
				String json = new String(request.getBody());
				System.out.println(json);
				System.out.println("after json");
				RawBody bodyContents = gson.fromJson(json, RawBody.class);
				System.out.println("hello: "+bodyContents.getContents());
				response = appendToFile(bodyContents.getContents(), file);
			}
		} else {
			Gson gson = new Gson();
			String json = new String(request.getBody());
			
			RawBody bodyContents = gson.fromJson(json, RawBody.class);
			
			response = appendToFile(bodyContents.getContents(), file);
		}
		return response;
	}

	public HttpResponse appendToFile(String body, File file) {
		try {
			FileOutputStream writer = new FileOutputStream(file, true);
			String contents = new String(body);
			System.out.println(contents);
			writer.write(contents.getBytes());
			writer.close();
		} catch (IOException e) {
			//e.printStackTrace();
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
		return HttpResponseFactory.create200OK(file, Protocol.CLOSE);
	}
}
