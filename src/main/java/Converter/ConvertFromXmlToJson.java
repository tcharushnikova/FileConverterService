package Converter;

import Classes.Network;
import Classes.Show;
import FileIO.JsonWriter;
import FileIO.XmlReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConvertFromXmlToJson implements Converter {
    String path = new File("").getAbsolutePath();

    @Override
    public void convert(String inputFile, String outputFile) throws IOException {
        XmlReader reader = new XmlReader();
        ArrayList<Show> shows = reader.readFromFile(path + "\\files\\Input\\" + inputFile);
        if (shows != null) {
            ArrayList<Network> networks = new ArrayList<>();
            for (Show show : shows) {
                String networkName = show.getNetwork();
                if (!containNetwork(networks, networkName)) {
                    Network network = new Network();
                    network.setName(networkName);
                    network.addShow(show);
                    networks.add(network);
                }
                else
                    for (Network network : networks)
                        if (network.getName().equals(show.getNetwork()))
                            network.addShow(show);
            }
            JsonWriter writer = new JsonWriter();
            writer.writeToFile(networks, path + "\\files\\Output\\" + outputFile);
        }
    }

    private boolean containNetwork(ArrayList<Network> networks, String networkName) {
        for (Network network : networks)
            if (network.getName().equals(networkName))
                return true;
        return false;
    }
}