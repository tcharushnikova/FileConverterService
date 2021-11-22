package FileIO;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public interface Writer<T> {
    void writeToFile(ArrayList<T> array, String filename) throws IOException, ParserConfigurationException;
}