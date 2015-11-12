import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
	private static FileManager instance;
	private File baseHtml;
	private static final String DIR_PATH = "./FileManagerResources";

	public FileManager() {
		servlets = new HashMap<String, IServlet>();
		servlets.put("getAllFiles", new GetAllFilesServlet());
		servlets.put("file", new GetFileServlet());
		
		File dir = new File(DIR_PATH);
		if (dir.exists()||!dir.isDirectory()){
			dir.delete();
			dir.mkdir();
		}
		baseHtml = new File("FileManager.html");
		
		instance = this;
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
	
	public static FileManager getInstance() {
		return instance;
	}
	
	public File getBaseHTML() {
		return baseHtml;
	}

}
