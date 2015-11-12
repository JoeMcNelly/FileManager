import java.util.HashMap;
import java.util.Map;

import Plugin.IPlugin;
import Servlet.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;

public class FileManager implements IPlugin{

	private Map<String, IServlet> servlets;
	
	public FileManager() {
		servlets = new HashMap<String, IServlet>();
		servlets.put("getAllFiles", new GetAllFilesServlet());
	}
	
	@Override
	public HttpResponse handle(HttpRequest request, String rootDir) {

		return null;
	}

}
