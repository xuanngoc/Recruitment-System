package ezjob.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String getDateFormat(Date date) {
		return new SimpleDateFormat("dd-MM-YYYY HH:mm").format(date);
	}
	
}
