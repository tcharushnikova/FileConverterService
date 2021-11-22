package FileIO;

import Classes.Network;
import Classes.Show;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonWriter implements Writer<Network> {
    @Override
    public void writeToFile(ArrayList<Network> array, String filename) throws IOException {
        JSONArray networksList = new JSONArray();
        for (Network network : array) {
            JSONObject networkObj = new JSONObject();
            networkObj.put("name", network.getName());
            networkObj.put("tvseries", getShows(network.getShows()));
            JSONObject networksObj = new JSONObject();
            networksObj.put("network", networkObj);
            networksList.add(networksObj);
        }
        JSONObject root = new JSONObject();
        root.put("networks", networksList);
        FileWriter file = new FileWriter(filename);
        file.write(prettyPrintJson(root.toJSONString()));
        file.flush();
        file.close();
    }

    private JSONArray getShows(ArrayList<Show> shows) {
        JSONArray showsObj = new JSONArray();
        for (Show show : shows) {
            JSONObject showObj = new JSONObject();
            showObj.put("name", show.getName());
            showObj.put("date", show.getDate());
            showObj.put("country", show.getCountry());
            JSONArray genres = new JSONArray();
            for (String genre : show.getGenres())
                genres.add(genre);
            showObj.put("genres", genres);
            showsObj.add(showObj);
        }
        return showsObj;
    }

    public static String prettyPrintJson(String unformattedJsonString) {
        StringBuilder prettyJSONBuilder = new StringBuilder();
        int indentLevel = 0;
        boolean inQuote = false;
        for (char charFromUnformattedJson : unformattedJsonString.toCharArray()) {
            switch (charFromUnformattedJson) {
                case '"':
                    inQuote = !inQuote;
                    prettyJSONBuilder.append(charFromUnformattedJson);
                    break;
                case ' ':
                    if (inQuote)
                        prettyJSONBuilder.append(charFromUnformattedJson);
                    break;
                case '{':
                case '[':
                    prettyJSONBuilder.append(charFromUnformattedJson);
                    ++indentLevel;
                    appendIndentedNewLine(indentLevel, prettyJSONBuilder);
                    break;
                case '}':
                case ']':
                    --indentLevel;
                    appendIndentedNewLine(indentLevel, prettyJSONBuilder);
                    prettyJSONBuilder.append(charFromUnformattedJson);
                    break;
                case ',':
                    prettyJSONBuilder.append(charFromUnformattedJson);
                    if (!inQuote)
                        appendIndentedNewLine(indentLevel, prettyJSONBuilder);
                    break;
                default:
                    prettyJSONBuilder.append(charFromUnformattedJson);
            }
        }
        return prettyJSONBuilder.toString();
    }

    private static void appendIndentedNewLine(int indentLevel, StringBuilder stringBuilder) {
        stringBuilder.append("\n");
        stringBuilder.append("  ".repeat(Math.max(0, indentLevel)));
    }
}