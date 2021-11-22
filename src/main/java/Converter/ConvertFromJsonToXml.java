package Converter;

import Classes.Network;
import Classes.Show;
import FileIO.JsonReader;
import FileIO.XmlWriter;

import java.io.File;
import java.util.ArrayList;

public class ConvertFromJsonToXml implements Converter {
    String path = new File("").getAbsolutePath();

    @Override
    public void convert(String inputFile, String outputFile) {
        JsonReader reader = new JsonReader();
        ArrayList<Network> networks = reader.readFromFile(path + "\\files\\Input\\" + inputFile);
        if (networks != null) {
            ArrayList<Show> shows = new ArrayList<>();
            for (Network network : networks)
                shows.addAll(network.getShows());
            XmlWriter writer = new XmlWriter();
            writer.writeToFile(shows, path + "\\files\\Output\\" + outputFile);
        }
    }
}