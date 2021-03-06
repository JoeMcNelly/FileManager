import java.util.HashMap;
import java.util.Map;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.IRequestHandler;
import protocol.Protocol;
import Servlet.IServlet;


public class GetAllFilesServlet implements IServlet{
	private Map<String, IRequestHandler> handlers;
	
	public GetAllFilesServlet(){
		handlers = new HashMap<String, IRequestHandler>();
		handlers.put(Protocol.GET, new GetAllFilesHandler());
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
