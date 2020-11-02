package sg.edu.ntu.gg4u.pfa.persistence;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public static final String DATA_LOADER_TAG = "Gov data loader";

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

    public static final String GOV_DATA_PREFIX = "government_database-";

    public static final Map<String, String> govDataSetFileNames = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Age", GOV_DATA_PREFIX + "Age.ser"),
            new AbstractMap.SimpleEntry<>("JobField", GOV_DATA_PREFIX + "JobField.ser"),
            new AbstractMap.SimpleEntry<>("Academic Qualification", GOV_DATA_PREFIX + "Academic Qualification.ser"),
            new AbstractMap.SimpleEntry<>("Income Group", GOV_DATA_PREFIX + "Income Group.ser")
    );

    private Context context;

    public Dataloader(Context context) {
        this.context = context;
    }

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

    private String requestDataFromOnlineDatabase(String key) throws IOException {
        URL url = new URL(govDataSetUrls.get(key));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        Log.d(DATA_LOADER_TAG, "\nSending 'GET' request to URL : " + url);
        Log.d(DATA_LOADER_TAG, "Response Code : " + responseCode);

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
        Toast.makeText(context, msg, Toast.LENGTH_LONG);
    }

    public HashMap<String, HashMap<String, Double>> loadData(String key) throws JSONException, IOException {
        //print in String
        //Log.d(DATA_LOADER_TAG, response.toString());
        String response = "";
        response = requestDataFromOnlineDatabase(key);

        //Read JSON response and print
        Log.d(DATA_LOADER_TAG, "Putting response into JSONObject...");
        JSONObject myResponse = new JSONObject(response);

        JSONArray result = myResponse.getJSONObject("result")
                .getJSONArray("records");

        return preProcess(result, key);
    }

    public void writeToSerial(Object object, String serialFileName) {
        Log.d(DATA_LOADER_TAG, "Serializing...");
        try {
            FileOutputStream fos = context.openFileOutput(serialFileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(DATA_LOADER_TAG, "Serialization failed.");
            Log.e(DATA_LOADER_TAG, Log.getStackTraceString(e));
            return;
        } catch (IOException e) {
            Log.d(DATA_LOADER_TAG, "Serialization failed.");
            Log.e(DATA_LOADER_TAG, Log.getStackTraceString(e));
            return;
        }
        Log.d(DATA_LOADER_TAG, "Serialization completed. Saved into " + serialFileName + ".");
    }

    public HashMap<String, HashMap<String, Double>> readFromSerial(String serialFileName) {
        Log.d(DATA_LOADER_TAG, "De-serializing from " + serialFileName + " ...");
        HashMap<String, HashMap<String, Double>> hashMap;
        try {
            FileInputStream fis = context.openFileInput(serialFileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            hashMap = (HashMap<String, HashMap<String, Double>>) ois.readObject();
            ois.close();
            fis.close();
            Log.d(DATA_LOADER_TAG, "De-serialization completed.");
            return hashMap;
        } catch (IOException ioe) {
            Log.e(DATA_LOADER_TAG, Log.getStackTraceString(ioe));
            return null;
        } catch (ClassNotFoundException c) {
            Log.e(DATA_LOADER_TAG, "Class " + serialFileName + " not found");
            Log.e(DATA_LOADER_TAG, Log.getStackTraceString(c));
            return null;
        }
        // printContent(hashMap);
    }

    public void loadAllData() throws IOException, JSONException {
        String[] str = {
                "Age", "JobField", "Academic Qualification", "Income Group"
        };
        HashMap<String, HashMap<String, Double>> hashMap = null;
        for (String key : str) {
            hashMap = loadData(key);
            String serialFilename = govDataSetFileNames.get(key);
            writeToSerial(hashMap, serialFilename);
        }
        categorySet = hashMap.get("Total").keySet();
        categorySet.remove("TOTAL");
    }

    public void startLoadingGovData(Runnable doOnFinishLoad) {
        Runnable loadDataTask = () -> {
            try {
                loadAllData();
            } catch (IOException e) {
                printToast("Network failure.\nCannot load data from data.gov.sg.");
                Log.e(DATA_LOADER_TAG, Log.getStackTraceString(e));
                return;
            } catch (JSONException e) {
                printToast("Cannot parse data from data.gov.sg as JSON.");
                Log.e(DATA_LOADER_TAG, Log.getStackTraceString(e));
                return;
            }
            doOnFinishLoad.run();
        };

        Thread loadDataThread = new Thread(loadDataTask);

        loadDataThread.start();
    }
}
