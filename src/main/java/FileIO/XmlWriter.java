package FileIO;

import Classes.Show;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XmlWriter implements Writer<Show> {
    @Override
    public void writeToFile(ArrayList<Show> array, String filename) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element tvseries = document.createElement("tvseries");
            document.appendChild(tvseries);
            for (int i = 0; i < array.size(); ++i) {
                Element show = document.createElement("show");
                show.setAttribute("id", String.valueOf(i));
                tvseries.appendChild(show);
                fillShow(document, show, array.get(i));
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filename));
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(domSource, streamResult);
        }
        catch (ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
        }
    }

    private void fillShow(Document document, Element show, Show curShow) {
        Element name = document.createElement("name");
        name.appendChild(document.createTextNode(curShow.getName()));
        show.appendChild(name);

        Element date = document.createElement("date");
        date.appendChild(document.createTextNode(curShow.getDate()));
        show.appendChild(date);

        Element country = document.createElement("country");
        country.appendChild(document.createTextNode(curShow.getCountry()));
        show.appendChild(country);

        Element network = document.createElement("network");
        network.appendChild(document.createTextNode(curShow.getNetwork()));
        show.appendChild(network);

        Element genres = document.createElement("genres");
        for (String genreStr : curShow.getGenres()) {
            Element genre = document.createElement("genre");
            genre.appendChild(document.createTextNode(genreStr));
            genres.appendChild(genre);
        }
        show.appendChild(genres);
    }
}