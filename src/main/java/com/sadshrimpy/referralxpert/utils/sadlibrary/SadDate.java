package com.sadshrimpy.referralxpert.utils.sadlibrary;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SadDate {

    // Date related
    private final String dateFormatStr = "dd/MM/yyyy HH:mm";
    private final String dateFormatStrNoHour = "dd/MM/yyyy";
    private final DateFormat dateFormat = new SimpleDateFormat(this.dateFormatStr);
    private final DateFormat dateFormatNoHour = new SimpleDateFormat(this.dateFormatStrNoHour);

    // Getters / Setters
    public String getTime() { return dateFormat.format(Calendar.getInstance().getTime()); }
    public Date getDefaultDate() { return Calendar.getInstance().getTime(); }
    public long getDefaultTimeInMills() { return Calendar.getInstance().getTimeInMillis(); }
    public String getTimeNoHour() { return dateFormatNoHour.format(Calendar.getInstance().getTime()); }
    public String getFormat() { return this.dateFormatStr; }
    public String getFormatNoHour() { return this.dateFormatStrNoHour; }
    public Calendar getCalendar() { return Calendar.getInstance(); }

    // Methods
    //@Nullable // todo: use collections (return null)
    private Date calculateTime(String time, String timeZone) {
        Calendar calendar = getCalendar();
        String[] arrTimesStr;
        arrTimesStr = time.split("-");
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        for (String subStr : arrTimesStr) {
            if (subStr.contains("*"))
                return null;
            else if (subStr.contains("g"))
                calendar.add(Calendar.HOUR, Integer.parseInt(subStr.substring(0, subStr.length() - 1)) * 24);
            else if (subStr.contains("o"))
                calendar.add(Calendar.HOUR, Integer.parseInt(subStr.substring(0, subStr.length() - 1)));
            else if (subStr.contains("m"))
                calendar.add(Calendar.MINUTE, Integer.parseInt(subStr.substring(0, subStr.length() - 1)));
            else if (subStr.contains("s"))
                calendar.add(Calendar.SECOND, Integer.parseInt(subStr.substring(0, subStr.length() - 1)));
        }
        return calendar.getTime();
    }
}
