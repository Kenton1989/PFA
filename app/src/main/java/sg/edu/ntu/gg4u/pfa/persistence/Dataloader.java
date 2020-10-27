package sg.edu.ntu.gg4u.pfa.persistence;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static sg.edu.ntu.gg4u.pfa.persistence.Preprocessors.preProcess;

@RequiresApi(api = Build.VERSION_CODES.R)
public class Dataloader {
    public static Set<String> categorySet;

    public static final Map<String, String> govDataSetUrls = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Age",
                    "https://data.gov.sg/api/action/datastore_search?resource_id=af5f9e35-9747-4b1e-97ad-bec7f43b0f15&&limit=10000"),
            new AbstractMap.SimpleEntry<>("JobField",
                    "https://data.gov.sg/api/action/datastore_search?resource_id=112ac457-dad9-4e48-a2e6-651066709d8f&limit=5000"),
            new AbstractMap.SimpleEntry<>("Academic Qualification",
                    "https://data.gov.sg/api/action/datastore_search?resource_id=6e79f514-4d6a-41d1-a944-7a310af7ace0&limit=5000"),
            new AbstractMap.SimpleEntry<>("Income Group",
                    "https://data.gov.sg/api/action/datastore_search?resource_id=ede8abca-2545-456e-bff7-c1501b1ffd06&limit=173")
    );

    public static final String dataDirectory = "Government_database/";

    public static final Map<String, String> govDataSetFileNames = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Age", "Age.ser"),
            new AbstractMap.SimpleEntry<>("JobField", "JobField.ser"),
            new AbstractMap.SimpleEntry<>("Academic Qualification", "Academic Qualification.ser"),
            new AbstractMap.SimpleEntry<>("Income Group", "Income Group.ser")
    );


    public static String cutPrefix(String original) {
        return original.substring(original.indexOf('-') + 2);
    }

    public static void printContent(HashMap<String, HashMap<String, Double>> hashMap) {
        System.out.println("Printing content...");
        for (Map.Entry<String, HashMap<String, Double>> oneEntry : hashMap.entrySet()) {
            for (Map.Entry<String, Double> val : oneEntry.getValue().entrySet()) {
                System.out.println(oneEntry.getKey() + " " + val.getKey() + " " + val.getValue());
            }
        }
    }

    private static String requestDataFromOnlineDatabase(String key) throws IOException {
        URL url = new URL(govDataSetUrls.get(key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private void printToast(String msg) {

    }
    public static HashMap<String, HashMap<String, Double>> loadData(String key) throws JSONException, IOException {
        //print in String
        //System.out.println(response.toString());
        String response = "";
        response = requestDataFromOnlineDatabase(key);

        //Read JSON response and print
        System.out.println("Putting response into JSONObject...");
        JSONObject myResponse = new JSONObject(response);

        JSONArray result = myResponse.getJSONObject("result")
                .getJSONArray("records");

        return preProcess(result, key);
    }

    public static void writeToSerial(Object object, String serialFileName) {
        System.out.println("Serializing...");
        try {
            FileOutputStream fos = new FileOutputStream(serialFileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Serialization completed. Saved into " + serialFileName + ".");
    }

    public static HashMap<String, HashMap<String, Double>> readFromSerial(String serialFileName) {
        System.out.println("De-serializing from " + serialFileName + " ...");
        HashMap<String, HashMap<String, Double>> hashMap;
        try {
            FileInputStream fis = new FileInputStream(serialFileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            hashMap = (HashMap<String, HashMap<String, Double>>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("De-serialization completed.");
            return hashMap;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Class " + serialFileName + " not found");
            c.printStackTrace();
            return null;
        }
        // printContent(hashMap);
    }

    public static void loadAllData() throws IOException, JSONException {
        String[] str = {
                "Age", "JobField", "Academic Qualification", "Income Group"
        };
        HashMap<String, HashMap<String, Double>> hashMap = null;
        for (String key : str) {
            hashMap = loadData(key);
            writeToSerial(hashMap, dataDirectory + govDataSetFileNames.get(key));
        }
        categorySet = hashMap.get("Total").keySet();
        categorySet.remove("TOTAL");
    }
}
