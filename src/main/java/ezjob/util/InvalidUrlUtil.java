package ezjob.util;

public class InvalidUrlUtil {
	
	public static String parsePathFileToValidUrl(String pathFile) {
		return pathFile.replace("\\", "%5C");
	}
}
