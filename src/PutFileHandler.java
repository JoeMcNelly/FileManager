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
//		System.out.println(request.getBody());
		
		
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
			File newFile = new File(rootDir + uri);
			FileOutputStream writer = new FileOutputStream(newFile, false);
			Gson gson = new Gson();
			String json = new String(request.getBody());
			String bodyContents = gson.fromJson(json, String.class);
			writer.write(bodyContents.getBytes());
			writer.close();
		} catch (IOException e) {
			//e.printStackTrace();
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
		return response;
	}

}