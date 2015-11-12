import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.IRequestHandler;
import protocol.Protocol;

public class GetAllFilesHandler implements IRequestHandler {
	private static final String DIR = System.getProperty("user.dir")
			+ System.getProperty("file.separator") + "FileMangerResources"
			+ System.getProperty("file.separator");

	@Override
	public HttpResponse handleRequest(HttpRequest request, String rootDir) {
		HttpResponse response = null;
		File fileDirectory = new File(DIR);
		File newFile = new File(DIR + "getAll.html");

		String[] listOfFiles = fileDirectory.list();
		return appendToFile(newFile, listOfFiles);
	}

	public HttpResponse appendToFile(File file, String[] listOfFiles) {
		try {
			FileOutputStream writer = new FileOutputStream(file, true);
			for (int i = 0; i < listOfFiles.length; i++) {
				String contents = new String(listOfFiles[i]);
				writer.write(contents.getBytes());
			}
			writer.close();
		} catch (IOException e) {
			// e.printStackTrace();
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
		return HttpResponseFactory.create200OK(file, Protocol.CLOSE);
	}

}
