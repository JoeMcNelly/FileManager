import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.IRequestHandler;
import protocol.Protocol;

public class GetAllFilesHandler implements IRequestHandler {
	private static final String DIR = "./FileManagerResources";
	private static final String PATH = "/getAll.html";

	@Override
	public HttpResponse handleRequest(HttpRequest request, String rootDir) {
		File dir = new File(DIR);
		if(dir.exists() || !dir.isDirectory()){
			dir.delete();
			dir.mkdirs();
		}
		File newFile = new File(DIR + PATH);
		if(newFile.exists()){
			newFile.delete();
		}
		try {
			newFile.createNewFile();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] listOfFiles = dir.list();
		return appendToFile(newFile, listOfFiles);
	}

	public HttpResponse appendToFile(File file, String[] listOfFiles) {
		try {
			FileOutputStream writer = new FileOutputStream(file, true);
			String fileHeader = "<h1> All Files </h1>";
			writer.write(fileHeader.getBytes());
			for (int i = 0; i < listOfFiles.length; i++) {
				String contents = new String(listOfFiles[i]);
				writer.write(contents.getBytes());
				contents = new String("<br>");
				writer.write(contents.getBytes());
			}
			String contents = new String("<br>");
			writer.write(contents.getBytes());
			writer.close();
		} catch (IOException e) {
			// e.printStackTrace();
			return HttpResponseFactory.create400BadRequest(Protocol.CLOSE);
		}
		return HttpResponseFactory.create200OK(file, Protocol.CLOSE);
	}

}
