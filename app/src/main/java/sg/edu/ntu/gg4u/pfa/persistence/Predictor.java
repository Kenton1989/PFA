package sg.edu.ntu.gg4u.pfa.persistence;


import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.AcademicQualification;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.Gender;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.JobField;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;

import static sg.edu.ntu.gg4u.pfa.persistence.Dataloader.govDataSetFileNames;


public class Predictor {

    private static final String TAG = Predictor.class.getSimpleName();

    private Context context;
    private Dataloader loader;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public Predictor(Context context) {
        loader = new Dataloader(context);
        this.context = context;
    }

    public static String age2key(String age) {
        if (age.charAt(0) == 'T')
            return age;

        Integer value = Integer.parseInt(age);
        if (value < 25)
            value = 0;
        else if (value > 65)
            value = 65;
        else value = value / 5 * 5;

        return value.toString();
    }

    public static String income2key(String income) {
        if (income.charAt(0) == 'T')
            return income;

        Double value = Double.parseDouble(income);
        Integer intVal = value.intValue();
        if (intVal < 1000)
            intVal = 0;
        else if (intVal > 15000)
            intVal = 15000;
        else if (intVal > 12000)
            intVal = 12000;
        else if (intVal > 6000)
            intVal = intVal / 2000 * 2000;
        else
            intVal = intVal / 1000 * 1000;

        if (intVal < 1000)
            return intVal.toString();

        return String.format("%d,%03d", intVal/1000, intVal%1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @NonNull
    public HashMap<String, Double> readSer(String key, String data) {
        Log.d(TAG, "readSer: key=" + key + " data=" + data);
        HashMap<String, HashMap<String, Double>> dataset =
                Objects.requireNonNull(loader.readFromSerial(govDataSetFileNames.get(key)));
        Log.d(TAG, "Read dataset with keys"+dataset.keySet());
        return Objects.requireNonNull(dataset.get(data));
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
   /* public void generatePrediction(UserProfile userProfile,
                                   HashMap<String, Double> prediction,
                                   HashMap<String, Double> distribution) {

        HashMap<String, Double> jobFieldPrediction, AQPrediction, agePrediction;
        Set<String> categorySet;


        String jobField, AQ, age, income;
        AQ = userProfile.getQualification() == null ?
                "Total" : userProfile.getQualification().toString();
        jobField = userProfile.getJobField() == null ?
                "Total" : userProfile.getJobField().toString();
        age = userProfile.getAge() == null ?
                "Total" : userProfile.getAge().toString();
        income = userProfile.getIncome() == null ?
                "Total" : userProfile.getIncome().toString();

        age = age2key(age);
        income = income2key(income);

        jobFieldPrediction = readSer("JobField", jobField);
        AQPrediction = readSer("Academic Qualification", AQ);
        agePrediction = readSer("Age", age);
        distribution.putAll(Objects.requireNonNull(readSer("Income Group", income)));
        categorySet = jobFieldPrediction.keySet();

        Double obj = distribution.remove("Below 1,000");
        distribution.put("0 - 1,000", obj);

        // add columns if it's null in the dataset
        if(!distribution.containsKey("10,000 - 11,999"))
            distribution.put("10,000 - 11,999", 0.0);

        if(!distribution.containsKey("12,000 - 14,999"))
            distribution.put("12,000 - 14,999", 0.0);

        if(distribution.containsKey("15,000 & Over"))
        {
            Double obj2 = distribution.remove("15,000 & Over");
            distribution.put(">=15,000", obj2);
        }
        else
            distribution.put(">=15,000", 0.0);
//        System.out.println(distribution);
//
//        System.out.println(categorySet);
//        System.out.println(jobFieldPrediction);
//        System.out.println(AQPrediction.keySet());
//        System.out.println(agePrediction.keySet());
//        System.out.println(distribution.keySet());

        for (String category : categorySet) {
            prediction.put(category,
                    jobFieldPrediction.get(category) * 0.24 +
                            AQPrediction.get(category) * 0.36 +
                            agePrediction.get(category) * 0.4);
        }

        //remove useless column
        distribution.remove("Number of Resident Households");
        distribution.remove("Average Monthly Household Expenditure ($)");
        distribution.remove("Total");
    }*/


    public HashMap<String, Double> predictDistributionByCategory(UserProfile userProfile) {

        HashMap<String, Double> jobFieldPrediction, AQPrediction, agePrediction;


        String jobField, AQ, age;
        AQ = (userProfile.getQualification() == null || userProfile.getQualification() == AcademicQualification.UNKNOWN)?
                "Total" : userProfile.getQualification().toString();
        jobField = (userProfile.getJobField() == null || userProfile.getJobField() == JobField.UNKNOWN)?
                "Total" : userProfile.getJobField().toString();
        age = userProfile.getAge() == null ?
                "Total" : userProfile.getAge().toString();

        age = age2key(age);

        jobFieldPrediction = readSer("JobField", jobField);
        AQPrediction = readSer("Academic Qualification", AQ);
        agePrediction = readSer("Age", age);

        Set<String> categorySet;
        categorySet = jobFieldPrediction.keySet();

        HashMap<String, Double> prediction = new HashMap<>();

        BigDecimal bd;
        double cat;

        for (String category : categorySet) {
           bd = BigDecimal.valueOf(jobFieldPrediction.get(category) * 0.24 +
                   AQPrediction.get(category) * 0.36 +
                   agePrediction.get(category) * 0.4).setScale(3, RoundingMode.HALF_UP);

           cat = bd.doubleValue();

            prediction.put(category, cat);
        }
        prediction.remove("TOTAL");
        return prediction;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public HashMap<String, Double> predictDistributionByIncomeGroup(UserProfile userProfile) {
        String income = userProfile.getIncome() == null ?
                "Total" : userProfile.getIncome().toString();

        income = income2key(income);
        HashMap<String, Double> distribution =
                new HashMap<>(Objects.requireNonNull(readSer("Income Group", income)));

        Double obj = distribution.remove("Below 1,000");
        distribution.put("0 - 1,000", obj);

        // add columns if it's null in the dataset
        if(!distribution.containsKey("10,000 - 11,999"))
            distribution.put("10,000 - 11,999", 0.0);

        if(!distribution.containsKey("12,000 - 14,999"))
            distribution.put("12,000 - 14,999", 0.0);

        if(distribution.containsKey("15,000 & Over"))
        {
            Double obj2 = distribution.remove("15,000 & Over");
            distribution.put(">=15,000", obj2);
        }
        else
            distribution.put(">=15,000", 0.0);

        distribution.remove("Number of Resident Households");
        distribution.remove("Average Monthly Household Expenditure ($)");
        distribution.remove("Total");

        return distribution;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void main(String[] args) {
      /*  UserProfile userProfile = new UserProfile("Peter Wen", Gender.MALE, JobField.NOT_WORKING,
                4, 500.0, 19, AcademicQualification.UNIVERSITY);
        HashMap<String, Double> prediction = new HashMap<>(), distribution = new HashMap<>();*/
        //generatePrediction(userProfile, prediction, distribution);
       // System.out.println(prediction);
      //  System.out.println(distribution);
    }
    public ArrayList<Double> getValue(TreeMap<String, Double> map)
    {
        ArrayList<Double> temp = new ArrayList<Double>();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            temp.add(entry.getValue());
        }
        return temp;
    }
}
