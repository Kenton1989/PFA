package sg.edu.ntu.gg4u.pfa.persistence.UserProfile;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Gender {
    UNKNOWN("Unknown"),
    MALE("Male"),
    FEMALE("Female"),
    SPECIAL("Special");

    private static final String TAG = "UserProfile.Gender";

    private final String fullName;
    private static HashMap<String, Gender> str2gen;

    Gender(String fullName) {
        if (getMap() == null) {
            initMap();
        }
        if(getMap().containsKey(fullName)) {
            throw new RuntimeException("The full name of gender must be unique");
        }
        this.fullName = fullName;

        Log.d("enum Gender", fullName + " created");
        getMap().put(fullName, this);
    }

    public String getFullName() {
        return fullName;
    }

    public static Gender toGender(String fullName) {
        return str2gen.get(fullName);
    }

    @Override
    public String toString() {
        return getFullName();
    }

    private HashMap<String, Gender> getMap() {
        return str2gen;
    }
    private void initMap() {
        str2gen = new HashMap<>();
    }

    public static String[] getAllGender() {
        Map.Entry<String, Gender>[] entrys = new Map.Entry[0];
        entrys = str2gen.entrySet().toArray(entrys);
        Arrays.sort(entrys, (a, b)->(a.getValue().compareTo(b.getValue())));

        String[] ret = new String[entrys.length];
        for (int i = 0; i < entrys.length; i++) {
            ret[i] = entrys[i].getKey();
        }
        return ret;
    }

}
