import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.gson.Gson;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.IRequestHandler;
import protocol.Protocol;


public class PutFileHandler  implements IRequestHandler {
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
		if(file.exists()) 
		{
			if(file.isDirectory())
			{
				response = HttpResponseFactory.create404NotFound(Protocol.CLOSE);
			}
			else {
				if(file.exists()) {
					file.delete();
					response = HttpResponseFactory.create200OK(file, Protocol.CLOSE);
				}
			}
		}
		else{
			response = HttpResponseFactory.create200OK(file, Protocol.CLOSE);
		}
		try {
			file.createNewFile();
			FileOutputStream writer = new FileOutputStream(file, false);
			Gson gson = new Gson();
			String json = new String(request.getBody());
			RawBody bodyContents = gson.fromJson(json, RawBody.class);
			writer.write(bodyContents.getContents().getBytes());
			writer.close();
		} catch (IOException e) {
			//e.printStackTrace();
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
		return response;
	}

}