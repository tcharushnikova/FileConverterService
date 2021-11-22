import Converter.ConvertFromXmlToJson;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2)
            System.out.println("Должно быть введено два файла");
        else {
            if (args[0].endsWith(".xml") && args[1].endsWith(".json")) {
                ConvertFromXmlToJson converter = new ConvertFromXmlToJson();
                converter.convert(args[0], args[1]);
            }
            else
                System.out.println("Расширение файла не соответствует требуемому");
        }
    }
}