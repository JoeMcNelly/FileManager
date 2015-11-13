import java.io.File;
import java.util.HashMap;
import java.util.Map;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;
import Plugin.IPlugin;
import Servlet.IServlet;

public class FileManager implements IPlugin {

	private Map<String, IServlet> servlets;
	private static FileManager instance;
	private File baseHtml;
	private boolean noHTML;
	private static final String WORK_DIR = System.getProperty("user.dir");
	private static final String DIR_PATH = System.getProperty("file.separator")
			+ "FileManagerResources";
	private static final String PATH = System.getProperty("file.separator")
			+ "FileManager.html";

	public FileManager() {
		servlets = new HashMap<String, IServlet>();
		servlets.put("getAllFiles", new GetAllFilesServlet());
		servlets.put("file", new GetFileServlet());
		servlets.put("delete", new DeleteFileServlet());
		servlets.put("createNew", new PostFileServlet());
		servlets.put("appendingToFile", new PutFileServlet());

//		File dir = new File(DIR_PATH);
		baseHtml = new File(WORK_DIR + DIR_PATH + PATH);
		if (!baseHtml.exists()) {
			this.noHTML = true;
		} else {
			this.noHTML = false;
		}
		instance = this;
	}

	@Override
	public HttpResponse handle(HttpRequest request, String rootDir) {

		if(this.noHTML) {
			return HttpResponseFactory.create404NotFound(Protocol.CLOSE);
		}
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
