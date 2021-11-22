package FileIO;

import Classes.Network;
import Classes.Show;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonReader implements Reader<Network> {
    @Override
    public ArrayList<Network> readFromFile(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            return getNetworks(jsonObject);
        }
        catch (IOException | ParseException e) {
            System.out.println("При считывании данных произошла ошибка!");
        }
        return null;
    }

    private ArrayList<Network> getNetworks(JSONObject jsonObject) {
        ArrayList<Network> networks = new ArrayList<>();
        JSONArray networksArray = (JSONArray) jsonObject.get("networks");
        for (Object networkObj : networksArray)
            networks.add(getNetwork(networkObj));
        return networks;
    }

    private Network getNetwork(Object networkObj) {
        Network network = new Network();
        JSONObject networkJson = (JSONObject)((JSONObject)networkObj).get("network");
        network.setName((String)networkJson.get("name"));
        for (Object showObj : (JSONArray)networkJson.get("tvseries")) {
            Show show = new Show();
            JSONObject showJson = (JSONObject)showObj;
            show.setName((String)showJson.get("name"));
            show.setDate((String)showJson.get("date"));
            show.setCountry((String)showJson.get("country"));
            show.setNetwork((String)networkJson.get("name"));
            ArrayList<String> genres = new ArrayList<>();
            for (Object genreObj : (JSONArray)showJson.get("genres"))
                genres.add((String)genreObj);
            show.setGenres(genres);
            network.addShow(show);
        }
        return network;
    }
}