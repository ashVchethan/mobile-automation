package utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonUtils {
    private static String udidArray;
    private static JSONArray shutdownSimulatorDetailsArray = new JSONArray();
    private static JSONArray bootedSimulatorsDetailsArray = new JSONArray();
    private static HashMap<String, String> simulatorUdidNameMap = new HashMap<>();

    public static void readJSONFile(String absoluteFileName) throws IOException, ParseException {
        // JSON parser object to parse read file
        FileReader reader = new FileReader(absoluteFileName);
        org.json.simple.parser.JSONParser jsonParser = new JSONParser();
        try {
            Object p = jsonParser.parse(reader);
            if (p instanceof org.json.simple.JSONArray) {
                JSONArray jsonArrayObject = (JSONArray) p;
                for (int i = 0; i < jsonArrayObject.size(); i++) {
                    JSONObject object = (JSONObject) jsonArrayObject.get(i);
                    parseJSONObject(object);
                }
            } else if (p instanceof org.json.simple.JSONObject) {
                org.json.simple.JSONObject jsonObject = (JSONObject) p;
                parseJSONObject(jsonObject);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseJSONObject(JSONObject jsonObject) {
        int index = 0;
        String simulatorName = (String) jsonObject.get("name");
        String udid = (String) jsonObject.get("udid");
        String simulatorState = (String) jsonObject.get("state");
        String iosVersion = (String) jsonObject.get("version");
        if (simulatorName.contains("iPhone")) {
            if (simulatorState.equalsIgnoreCase("shutdown")) {
                shutdownSimulatorDetailsArray.add(jsonObject);
            } else if (simulatorState.equalsIgnoreCase("booted")) {
                bootedSimulatorsDetailsArray.add(jsonObject);
            }
        }
    }

    public static HashMap<String, String> getBootedSimulatorsDetailsArray() {
        return getSimulatorDetailsMap(bootedSimulatorsDetailsArray);
    }

    public static HashMap<String, String> getShutdownSimulatorDetailsArray() {
        return getSimulatorDetailsMap(shutdownSimulatorDetailsArray);
    }

    public static HashMap<String, String> getSimulatorDetailsMap(JSONArray jsonArray) {
        simulatorUdidNameMap = new HashMap<>();
        JSONObject jsonObject;
        String name, udid;
        for (int index = 0; index < jsonArray.size(); index++) {
            jsonObject = (JSONObject) jsonArray.get(index);
            name = jsonObject.get("name").toString();
            udid = jsonObject.get("udid").toString();
            simulatorUdidNameMap.put(udid, name);
        }
        ArrayList<String> listOfKeys = new ArrayList<>(simulatorUdidNameMap.keySet());
        udidArray = listOfKeys.toString().replace('[', ' ').replace(']', ' ').replace(", ", " ");
        return simulatorUdidNameMap;
    }

    public static String getBootedSimulatorUdidArray() {
        simulatorUdidNameMap = getBootedSimulatorsDetailsArray();
        ArrayList<String> listOfKeys = new ArrayList<>(simulatorUdidNameMap.keySet());
        udidArray = listOfKeys.toString().replace('[', ' ').replace(']', ' ').replace(", ", " ");
        return udidArray;
    }

    public static String getShutdownSimulatorUdidArray() {
        simulatorUdidNameMap = getShutdownSimulatorDetailsArray();
        ArrayList<String> listOfKeys = new ArrayList<>(simulatorUdidNameMap.keySet());
        udidArray = listOfKeys.toString().replace('[', ' ').replace(']', ' ').replace(", ", " ");
        return udidArray;
    }
}
