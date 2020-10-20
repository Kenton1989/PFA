package sg.edu.ntu.gg4u.pfa.Dataloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Dataloader {
    public static void loadData() throws IOException, JSONException {
        URL url = new URL("https://data.gov.sg/api/action/datastore_search?resource_id=af5f9e35-9747-4b1e-97ad-bec7f43b0f15");
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
        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());

        JSONArray result = myResponse.getJSONObject("result")
                .getJSONArray("records");

        HashMap<String, HashMap<String, Double>> hashMap = new HashMap<>();
        for(int i = 0; i < result.length(); i++) {
            JSONObject jsonObject = result.getJSONObject(i);
            String level1 = jsonObject.getString("level_1");
            String level2 = jsonObject.getString("level_2");
            double value = jsonObject.getDouble("value");

            if (hashMap.containsKey(level1)) {
                hashMap.get(level1).put(level2, value);
            }
            else {
                HashMap<String, Double> newHashMap = new HashMap<>();
                newHashMap.put(level2, value);
                hashMap.put(level1, newHashMap);
            }
        }



    }
}
