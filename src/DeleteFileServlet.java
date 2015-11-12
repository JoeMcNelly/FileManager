import java.util.HashMap;
import java.util.Map;

import Servlet.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.IRequestHandler;
import protocol.Protocol;

public class DeleteFileServlet implements IServlet {

	private Map<String, IRequestHandler> handlers;

	public DeleteFileServlet() {
		handlers = new HashMap<String, IRequestHandler>();
		handlers.put(Protocol.POST, new DeleteFileHandler());
	}

	@Override
	public HttpResponse handle(HttpRequest request, String rootDir) {
		if (handlers.containsKey(request.getMethod())) {
			return handlers.get(request.getMethod()).handleRequest(request,
					rootDir);
		} else {
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
	}
}
