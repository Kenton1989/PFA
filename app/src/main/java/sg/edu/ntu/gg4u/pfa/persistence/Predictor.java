package sg.edu.ntu.gg4u.pfa.persistence;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.AcademicQualification;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.Gender;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.JobField;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;

import static sg.edu.ntu.gg4u.pfa.persistence.Dataloader.govDataSetFileNames;
import static sg.edu.ntu.gg4u.pfa.persistence.Dataloader.readFromSerial;


public class Predictor {

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
        else if (intVal > 20000)
            intVal = 20000;
        else if (intVal > 12000)
            intVal = 12000;
        else if (intVal > 6000)
            intVal = intVal / 2000 * 2000;
        else
            intVal = intVal / 1000 * 1000;

        return intVal.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @NonNull
    public static HashMap<String, Double> readSer(String key, String data) {
        HashMap<String, HashMap<String, Double>> dataset =
                Objects.requireNonNull(readFromSerial(govDataSetFileNames.get(key)));
        return Objects.requireNonNull(dataset.get(key));
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void generatePrediction(UserProfile userProfile,
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
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void main(String[] args) {
        UserProfile userProfile = new UserProfile("Peter Wen", Gender.MALE, JobField.NOT_WORKING,
                4, 500.0, 19, AcademicQualification.UNIVERSITY);
        HashMap<String, Double> prediction = new HashMap<>(), distribution = new HashMap<>();
        generatePrediction(userProfile, prediction, distribution);
        System.out.println(prediction);
        System.out.println(distribution);
    }
}
