package org.uip.mobilebanking.utils;

import org.uip.mobilebanking.models.notification.MifosNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by dilpreet on 14/9/17.
 */

public class NotificationComparator implements Comparator<MifosNotification> {
    @Override
    public int compare(MifosNotification mifosNotification1, MifosNotification mifosNotification2) {
        String string_date1 = mifosNotification1.getTimeStamp();
        String string_date2 = mifosNotification2.getTimeStamp();

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long milliseconds1=0, milliseconds2=0;
        try {
            Date d1 = f.parse(string_date1);
            Date d2 = f.parse(string_date2);
            milliseconds1 = d1.getTime();
            milliseconds2 = d2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return milliseconds2 < milliseconds1 ? -1 :
                milliseconds1 >milliseconds2 ? 1 : 0;
    }
}
