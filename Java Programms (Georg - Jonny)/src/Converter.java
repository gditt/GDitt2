import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Converter {
    String path;

    Array[][] masterTable = new Array[0][0];




    public Converter(String path) throws FileNotFoundException {
        this.path = path;
    }

    public String umwandeln() throws IOException {
        Workbook workbook = new HSSFWorkbook();

        int reiheZahl = 0;

        Sheet sheet1 = workbook.createSheet();
        Row reihe = sheet1.createRow(reiheZahl);
        reihe.createCell(0).setCellValue("Probe");
        reihe.createCell(1).setCellValue("ID");
        reihe.createCell(2).setCellValue("Art");
        reihe.createCell(3).setCellValue("Ret. Time");
        reihe.createCell(4).setCellValue("Area");
        reihe.createCell(4).setCellValue("% Area");
        reihe.createCell(5).setCellValue("Name");
        reihe.createCell(6).setCellValue("ID");
        reihe.createCell(7).setCellValue("Probe");
        reihe.createCell(8).setCellValue("Name");
        reihe.createCell(9).setCellValue("ID");
        reihe.createCell(10).setCellValue("Probe");
        reihe.createCell(11).setCellValue("Name");
        reihe.createCell(12).setCellValue("ID");
        reihe.createCell(13).setCellValue("Probe");
        reihe.createCell(13).setCellValue("Probe");

        String dateiName = path.substring(26);
        System.out.println(dateiName);

        try {
            //mache einen FileOutputStream, den wir später verwenden um Sachen
            //auf unserer Festplatte speichern zu können
            FileOutputStream output = new FileOutputStream("C:\\Users\\Nutzer\\Documents\\Dateien_Georg_Uni\\" + dateiName + ".xsl");

            //schreibe die Datei auf unsere Festplatte
            workbook.write(output);

            //beenden wir das Ganze, indem wir das Output wieder schließen
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean neverFound = true;

        String content = new String(Files.readAllBytes(Paths.get(path)));

        File f1 = new File(path); //Creation of File Descriptor for input file
        String[] words = null;  //Intialize the word Array
        FileReader fr = new FileReader(f1);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String s;
        int count = 0;   //Intialize the word to zero
        /*while ((s = br.readLine()) != null)   //Reading Content from the file
        {
            if (neverFound) {
                String regex = "Range[ ,\\t]+Peak[ ,\\t]+Ret\\.Time[ ,\\t]+Area[ ,\\t]+%\\sArea[ ,\\t]+";

                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    neverFound = false;
                    System.out.println(neverFound);
                }

            } else {
                String regexRetTime = "\\d\\d:\\d\\d";




                Pattern pattern = Pattern.compile(regexRetTime, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(s);

                if(matcher.find())
                {
                    reiheZahl++;

                    System.out.println("Ich hab es eigentlich gefunden und die reiheZahl ist: " + reiheZahl);
                    System.out.println("Matcher.group value is: " + matcher.group());

                    writeInFile(matcher.group(), sheet1, count);
                    count++;
                }


                *//*String regexPart = "\\d\\d:\\d\\d";

                Pattern patternPart = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcherPart = pattern.matcher(s);

                if (matcher.find()) {

                }*//*


            }


        }*/
        int j = 0;
        while (j<5)
        {
            reihe = sheet1.createRow(j);
            reihe.createCell(3).setCellValue("hih");
            //writeInFile("hi", sheet1, j);
            j++;
        }
        fr.close();

        return content;
    }

    private void writeInFile (String text, Sheet sheet1, int index) {
        System.out.println("writeInFile aufgerufen");
        Row row = sheet1.createRow(index);
        //Row row = CellUtil.createCell(index).setCellValue("hih");
        row.createCell(index).setCellValue("Hih");

    }
}