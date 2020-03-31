package basf.knowledge.omf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class Utils {

	public static InputStream getResourceStream(String fileName) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream is = classLoader.getResourceAsStream(fileName);
		if (is == null)
			return null;
		return is;
	}
	
	public static File getResourceFile(String fileName) throws IOException {
		InputStream is = getResourceStream(fileName);
		File tempFile = File.createTempFile(fileName, "temp");
		tempFile.deleteOnExit();
		FileOutputStream out = new FileOutputStream(tempFile);
		IOUtils.copy(is, out);
		return tempFile;
	}
}
