package Converter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface Converter {
    void convert(String inputFile, String outputFile) throws IOException, ParserConfigurationException;
}