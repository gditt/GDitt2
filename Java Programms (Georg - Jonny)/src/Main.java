import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        Converter converterOne = new Converter("C:\\Users\\Jonny\\Documents\\Uni\\Georg\\GDitt2\\200115_original_.apr_Daten", "C:\\Users\\Jonny\\Documents\\Georg\\");
        converterOne.umwandeln();
    }
}
