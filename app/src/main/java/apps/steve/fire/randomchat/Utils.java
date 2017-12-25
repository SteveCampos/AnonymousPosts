package apps.steve.fire.randomchat;

import android.content.res.Resources;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Steve on 17/12/2017.
 */

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    private static final String FIREBASEKEY_INVALID_CHARACTERS = ".$#[]/";

    public static String normalizeTag(String tag) {
        String normalizedTag = tag.replaceAll(Pattern.quote(FIREBASEKEY_INVALID_CHARACTERS), "");
        if (isNumeric(normalizedTag)) {
            normalizedTag = null;
        }
        return normalizedTag;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    private static String[] sortAlphabetical(String key1, String key2) {
        String temp = null;
        int compare = key1.compareTo(key2);//Comparing strings by their alphabetical order
        if (compare > 0) {
            temp = key2;
            key2 = key1;
            key1 = temp;
        }
        return new String[]{key1, key2};
    }

    public static String getId(String id1, String id2) {
        String[] idsOrdered = sortAlphabetical(id1, id2);
        return idsOrdered[0] + "-" + idsOrdered[1];
    }


    public static String calculateLastConnection(long startDate, long endDate, Resources resources) {

        String lastConnection = "";
        String period = "";
        long rest = 0;
        //milliseconds
        long different = endDate - startDate;

        if (different < 0) {
            different = -different;
        }

        Log.d(TAG, "startDate : " + startDate);
        Log.d(TAG, "endDate : " + endDate);
        Log.d(TAG, "different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthInMilli = daysInMilli * 30;

        if (different <= minutesInMilli) {
            rest = (different / secondsInMilli);
            period = resources.getString(R.string.time_seconds);
        } else if (different <= hoursInMilli) {
            rest = (different / minutesInMilli);
            period = resources.getString(R.string.time_minutes);
        } else if (different <= daysInMilli) {
            rest = (different / hoursInMilli);
            period = resources.getString(R.string.time_hours);
        } else if (different <= monthInMilli) {
            rest = (different / daysInMilli);
            period = resources.getString(R.string.time_days);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            return formatter.format(startDate);
        }
        lastConnection = String.format(Locale.getDefault(), resources.getString(R.string.last_connection), rest, period);
        Log.d(TAG, "lastConnection: " + lastConnection);
        return lastConnection;
    }

    public static String calculateTimePassed(long startDate, long endDate, Resources resources) {

        String lastConnection = "";
        String period = "";
        long rest = 0;
        //milliseconds
        long different = endDate - startDate;

        if (different < 0) {
            different = -different;
        }

        Log.d(TAG, "startDate : " + startDate);
        Log.d(TAG, "endDate : " + endDate);
        Log.d(TAG, "different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthInMilli = daysInMilli * 30;

        if (different <= daysInMilli) {
            rest = (different / hoursInMilli);
            period = resources.getString(R.string.time_hours);
            SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("h:mm a", Locale.getDefault());
            return SIMPLE_DATE_FORMAT.format(startDate);

        } else if (different <= monthInMilli) {
            rest = (different / daysInMilli);
            period = resources.getString(R.string.time_days);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            return formatter.format(startDate);
        }
        Log.d(TAG, "%d resto: " + rest + ", %s periodo: " + period);

        lastConnection = String.format(Locale.getDefault(), resources.getString(R.string.last_message_time), rest, period);
        return lastConnection;
    }

    public static String formatDate(long timestamp, Resources resources) {

        Calendar dateToFormat = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        dateToFormat.setTimeInMillis(timestamp);
        now.setTimeInMillis(new Date().getTime());

        boolean sameYear = dateToFormat.get(Calendar.YEAR) == now.get(Calendar.YEAR);
        boolean sameMonth = dateToFormat.get(Calendar.MONTH) == now.get(Calendar.MONTH);
        boolean sameWeek = dateToFormat.get(Calendar.WEEK_OF_MONTH) == now.get(Calendar.WEEK_OF_MONTH);
        boolean sameDay = dateToFormat.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH);


        if (sameYear && sameMonth && sameDay) {
            return resources.getString(R.string.global_time_today);
        } else if (sameYear && sameMonth && sameWeek) {
            return new SimpleDateFormat("EEEE", Locale.getDefault()).format(dateToFormat.getTime());
        }
        return new SimpleDateFormat("EEEE, d 'de' MMMM 'del' yyyy", Locale.getDefault()).format(dateToFormat.getTime());
    }
}
