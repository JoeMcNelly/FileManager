import java.util.HashMap;
import java.util.Map;

import Servlet.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.IRequestHandler;
import protocol.Protocol;

public class GetFileServlet implements IServlet {

private Map<String, IRequestHandler> handlers;
	
	
	public GetFileServlet() {
		handlers = new HashMap<String, IRequestHandler>();
		handlers.put(Protocol.GET, new GetFileHandler());
	}
	@Override
	public HttpResponse handle(HttpRequest request, String rootDir) {
		IRequestHandler handler;
		try {
			handler = handlers.get(request.getMethod());
			return handler.handleRequest(request, rootDir);
		}catch (Exception e){
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
	}

}
