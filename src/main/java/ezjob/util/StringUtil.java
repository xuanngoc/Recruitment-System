package ezjob.util;

public class StringUtil {
	public static String getCompanyName(String companyName) {
		String[] splitName = companyName.split(" ");
		return splitName[splitName.length - 1];
	}
}
