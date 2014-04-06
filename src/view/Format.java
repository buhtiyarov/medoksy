package view;

import java.util.Date;
import java.text.DateFormat;

public class Format {
    public static String date(Date value) {
        return DateFormat.getInstance().format(value);
    }
}
