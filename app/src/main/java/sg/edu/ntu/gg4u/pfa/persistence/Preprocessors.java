package sg.edu.ntu.gg4u.pfa.persistence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Preprocessors {

    public static String cutPrefix(String original) {
        return original.substring(original.indexOf('-') + 2);
    }

    public static String preProcessLevel1AQ_Job(String level1) {
        if (level1.indexOf('-') != -1)
            return cutPrefix(level1);
        return level1;
    }

    public static String preProcessLevel1Age_Income(String level1) {
        if (level1.indexOf('-') != -1)
            level1 = cutPrefix(level1);

        if (level1.charAt(0) == 'B')
            level1 = "0";
        else if (level1.indexOf('-') != -1) {
            level1 = level1.substring(0, level1.indexOf('-') - 1);
        } else if (level1.indexOf('&') != -1)
            level1 = level1.substring(0, level1.indexOf('&') - 1);

        return level1;
    }

    public static HashMap<String, HashMap<String, Double>> preProcess(JSONArray result, String key) throws JSONException {
        System.out.println("Pre-processing data...");
        HashMap<String, HashMap<String, Double>> hashMap = new HashMap<>();
        for (int i = 0; i < result.length(); i++) {
            JSONObject jsonObject = result.getJSONObject(i);
            String level1 = jsonObject.getString("level_1");
            if (key.equals("Age") || key.equals("Income Group"))
                level1 = preProcessLevel1Age_Income(level1);
            else
                level1 = preProcessLevel1AQ_Job(level1);
            String level2 = jsonObject.getString("level_2");
            double value = jsonObject.getDouble("value");

            if (hashMap.containsKey(level1)) {
                hashMap.get(level1).put(level2, value);
            } else {
                HashMap<String, Double> newHashMap = new HashMap<>();
                newHashMap.put(level2, value);
                hashMap.put(level1, newHashMap);
            }
        }

        System.out.println("Pre-processing completed.");
        return hashMap;
    }

    public static void main(String[] args) {
        String str = "Age Group of Main Income Earner (Years) - 30 - 34";
        str = cutPrefix(str);
        str = str.substring(0, str.indexOf('-') - 1);
        System.out.println(str);
    }
}
