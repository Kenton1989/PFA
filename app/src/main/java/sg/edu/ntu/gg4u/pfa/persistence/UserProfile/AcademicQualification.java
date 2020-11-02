package sg.edu.ntu.gg4u.pfa.persistence.UserProfile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum AcademicQualification {
    UNKNOWN("unknown"),
    NO_AQ("No Qualification"),
    PRIMARY("Primary"),
    LOWER_SEC("Lower Secondary"),
    SECONDARY("Secondary"),
    POST_SEC("Post Secondary (Non-Tertiary)"),
    POLY("Polytechnic"),
    PROFESSIONAL("Professional Qualification & Other Diploma"),
    UNIVERSITY("University");

    private static final String TAG = "UserProfile.AcademicQualification";

    private final String fullName;
    private static HashMap<String, AcademicQualification> str2AQ;

    AcademicQualification(String fullName) {
        if (getMap() == null) {
            initMap();
        }
        if (getMap().containsKey(fullName)) {
            throw new RuntimeException("The full name of academic qualification" +
                    " must be unique");
        }
        this.fullName = fullName;
        getMap().put(fullName, this);
    }

    public String getFullName() {
        return fullName;
    }

    public static AcademicQualification toAcademicQualification(String fullName) {
        return str2AQ.get(fullName);
    }

    @Override
    public String toString() {
        return getFullName();
    }

    private HashMap<String, AcademicQualification> getMap() {
        return str2AQ;
    }

    private void initMap() {
        str2AQ = new HashMap<>();
    }

    public static String[] getAllAcademicQualification() {
        Map.Entry<String, AcademicQualification>[] entrys = new Map.Entry[0];
        entrys = str2AQ.entrySet().toArray(entrys);
        Arrays.sort(entrys, (a, b)->(a.getValue().compareTo(b.getValue())));

        String[] ret = new String[entrys.length];
        for (int i = 0; i < entrys.length; i++) {
            ret[i] = entrys[i].getKey();
        }
        return ret;
    }
}
