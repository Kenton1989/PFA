package sg.edu.ntu.gg4u.pfa.persistence.UserProfile;

import java.util.HashMap;

public enum Gender {
    UNKNOWN("Unknown"),
    MALE("Male"),
    FEMALE("Female"),
    SPECIAL("Special");

    private static final String TAG = "UserProfile.Gender";

    private final String fullName;
    private static HashMap<String, Gender> str2gen = null;

    Gender(String fullName) {
        if (getMap() == null) {
            initMap();
        }
        if(getMap().containsKey(fullName)) {
            throw new RuntimeException("The full name of gender must be unique");
        }
        this.fullName = fullName;
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
        return (String[]) str2gen.keySet().toArray();
    }

}
