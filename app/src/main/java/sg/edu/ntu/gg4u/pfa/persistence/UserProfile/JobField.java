package sg.edu.ntu.gg4u.pfa.persistence.UserProfile;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum JobField {
    UNKNOWN("Unknown"),
    NOT_WORKING("Not Working"),
    OFFICIAL_MANAGER("Senior Officials & Managers"),
    PROFESSIONAL("Professionals"),
    TECHNICIAN_ASSOC_PROF("Associate Professionals & Technicians"),
    CLERICAL("Clerical Workers"),
    SERVICE_SALE("Service & Sales Workers"),
    PRODUCT_CRAFTING("Production Craftsmen & Related Workers"),
    MACHINE_OPERATOR_ASSEMBLER("Plant & Machine Operators & Assemblers"),
    CLEANER_LABOURER("Cleaners, Labourers & Related Workers"),
    OTHERS("Others");

    private static final String TAG = "UserProfile.JobField";

    private final String fullName;
    private static HashMap<String, JobField> str2job;

    JobField(String fullName) {
        if (getMap() == null) {
            initMap();
        }
        if(getMap().containsKey(fullName)) {
            throw new RuntimeException("The full name of job field must be unique");
        }
        this.fullName = fullName;
        getMap().put(fullName, this);
    }

    public String getFullName() {
        return fullName;
    }

    public static JobField toJobField(String fullName) {
        return str2job.get(fullName);
    }

    @Override
    public String toString() {
        return getFullName();
    }

    private HashMap<String, JobField> getMap() {
        return str2job;
    }
    private void initMap() {
        str2job = new HashMap<>();
    }

    public static String[] getAllJobField() {

        Map.Entry<String, JobField>[] entrys = new Map.Entry[0];
        entrys = str2job.entrySet().toArray(entrys);
        Arrays.sort(entrys, (a, b)->(a.getValue().compareTo(b.getValue())));

        String[] ret = new String[entrys.length];
        for (int i = 0; i < entrys.length; i++) {
            ret[i] = entrys[i].getKey();
        }
        return ret;
    }
}
