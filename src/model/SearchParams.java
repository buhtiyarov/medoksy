package model;

import java.util.Date;
import java.util.Calendar;

public class SearchParams extends SimpleDispatcher {
    private String search = "";
    private Date date;

    public void setSearch(String value) {
        search = value;
        fireChanged();
    }

    public String getSearch() {
        return search;
    }

    public void setDate(Date value) {
        date = value;
        fireChanged();
    }

    public Date getDate() {
        return date;
    }

    public Date getDateFrom() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public Date getDateTo() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFrom());
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        return cal.getTime();
    }
}
