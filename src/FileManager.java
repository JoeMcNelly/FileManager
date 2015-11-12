import java.util.HashMap;
import java.util.Map;

import Plugin.IPlugin;
import Servlet.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

public class FileManager implements IPlugin {

	private Map<String, IServlet> servlets;

	public FileManager() {
		servlets = new HashMap<String, IServlet>();
		servlets.put("getAllFiles", new GetAllFilesServlet());
		servlets.put("file", new GetFileServlet());
	}

	@Override
	public HttpResponse handle(HttpRequest request, String rootDir) {

		IServlet servlet;
		try {
			String[] uriParts = request.getUri().split("/");
			servlet = this.servlets.get(uriParts[2]);
			return servlet.handle(request, rootDir);
		} catch (Exception e) {
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
	}

}
