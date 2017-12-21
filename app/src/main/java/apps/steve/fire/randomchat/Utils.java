package apps.steve.fire.randomchat;

/**
 * Created by Steve on 17/12/2017.
 */

public class Utils {
    public static String[] sortAlphabetical(String key1, String key2) {
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
}
