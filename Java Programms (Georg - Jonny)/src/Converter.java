import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
    String pathFrom;
    String pathTo;

    public Converter(String pathFrom, String pathTo) throws FileNotFoundException {
        this.pathFrom = pathFrom;
        this.pathTo = pathTo;
    }

    public void umwandeln() throws IOException {
        File folder = new File(pathFrom);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                wandleUm(file.toString());
            }
        }
    }


    public void wandleUm(String file) throws IOException {
        Workbook workbook = new HSSFWorkbook();

        int reiheZahl = 0;

        String dateiName = file.substring(69);

        System.out.println(dateiName);

        boolean neverFound = true;

        short wichProp = 0;

        File f1 = new File(file); //Creation of File Descriptor for input file
        FileReader fr = new FileReader(f1);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String s;

        Sheet sheet = workbook.createSheet();
        Row reihe = sheet.createRow(reiheZahl);

        writeInFile("Probe", sheet, reihe, 0);
//        reihe.createCell(0).setCellValue("Probe");
        reihe.createCell(1).setCellValue("ID");
        reihe.createCell(2).setCellValue("Art");
        reihe.createCell(3).setCellValue("Ret. Time");
        reihe.createCell(4).setCellValue("Area");
        reihe.createCell(5).setCellValue("% Area");
        reihe.createCell(6).setCellValue("Name");
        reihe.createCell(7).setCellValue("SI");
        reihe.createCell(8).setCellValue("Probe");
        reihe.createCell(9).setCellValue("Name");
        reihe.createCell(10).setCellValue("SI");
        reihe.createCell(11).setCellValue("Probe");
        reihe.createCell(12).setCellValue("Name");
        reihe.createCell(13).setCellValue("SI");
        reihe.createCell(14).setCellValue("Probe");

        while ((s = br.readLine()) != null)   //Reading Content from the file
        {
            if (neverFound) {
                String regex = "Range[ ,\\t]+Peak[ ,\\t]+Ret\\.Time[ ,\\t]+Area[ ,\\t]+%\\sArea[ ,\\t]+";

                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    neverFound = false;
                    reiheZahl++;
                }

            } else {
                String regexRange = "\\d{1,4}[ ,\\t]+-[ ,\\t]+\\d\\d{1,4}";
                String regexPeak = "\\d{2,3}";
                String regexRetTime = "\\d\\d:\\d\\d";
                String regexArea = "\\d+";
                String regexPercArea = "\\d{1,2}\\.\\d{1,2}";
                String regexProp = "[ ,\\t]+\\d\\.[ ,\\t]+.+";
                String regexPropSec = "[ ,\\t]+SI\\s+#:\\s+\\d{2,4}[ ,\\t]+Formula: [\\w,\\d]+[ ,\\t]+CAS #: [\\d,none]+";

//                Finde die erste Zeile und speichere sie
                String regexFirstRow = "\\d{1,4}[ ,\\t]+-[ ,\\t]+\\d\\d{1,4}[ ,\\t]+\\d{2,3}[ ,\\t]+\\d\\d:\\d\\d[ ,\\t]+\\d+[ ,\\t]+\\d{1,2}\\.\\d{1,2}[ ,\\t]*";
                Pattern pattern = Pattern.compile(regexFirstRow, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(s);

//              Suche in erster Zeile, was du brauchst
                if (matcher.find()) {
//                    nächste Reihe
                    reihe = sheet.createRow(reiheZahl);
                    regexFirstRow = matcher.group();
//                    Finde Range, Ret.Time, Area, % Area und schreibe sie
                    pattern = Pattern.compile(regexRange, Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(regexFirstRow);
                    if (matcher.find()) {
//                        writeInFile(matcher1.group(),sheet1,reiheZahl,0);
                        regexFirstRow = regexFirstRow.substring(matcher.end()).trim();
//                        System.out.println(matcher.group());
                    }

                    pattern = Pattern.compile(regexPeak, Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(regexFirstRow);
                    if (matcher.find()) {
                        regexFirstRow = regexFirstRow.substring(matcher.end()).trim();
                    }

                    pattern = Pattern.compile(regexRetTime, Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(regexFirstRow);
                    if (matcher.find()) {
                        writeInFile(matcher.group(), sheet, reihe, 3);
//                        writeInFile("Wasn Das Hier", sheet1, reiheZahl +45, 3);
                        regexFirstRow = regexFirstRow.substring(matcher.end()).trim();
                    }

                    pattern = Pattern.compile(regexArea, Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(regexFirstRow);
                    if (matcher.find()) {
                        writeInFile(matcher.group(), sheet, reihe, 4);
                        regexFirstRow = regexFirstRow.substring(matcher.end()).trim();
                    }

                    pattern = Pattern.compile(regexPercArea, Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(regexFirstRow);
                    if (matcher.find()) {
                        writeInFile(matcher.group(), sheet, reihe, 5);
                        regexFirstRow.substring(matcher.end()).trim();
                    }

                    reiheZahl++;
                }

//                Finde Namen
                pattern = Pattern.compile(regexProp, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(s);

                if (matcher.find()){
                    String allName = matcher.group().trim();

                    switch ((allName.substring(0,1))){
                        case "1":
                            writeInFile(allName.substring(2).trim(),sheet,reihe,6);
                            wichProp = 1;
                            break;
                        case "2":
                            writeInFile(allName.substring(2).trim(),sheet,reihe,9);
                            wichProp = 2;
                            break;
                        case "3":
                            writeInFile(allName.substring(2).trim(),sheet,reihe,12);
                            wichProp = 3;
                            break;
                        default:
                            wichProp = 0;
                    }

                }

//                Finde SI
                pattern = Pattern.compile(regexPropSec, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(s);

                if (matcher.find()) {
                    pattern = Pattern.compile("SI\\s?#:\\s?\\d+\\s", Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(s);
                    if (matcher.find()) {
                        switch (wichProp){
                            case 1:
                                writeInFile(matcher.group().substring(5).trim(), sheet, reihe, 7);
                                break;
                            case 2:
                                writeInFile(matcher.group().substring(5).trim(), sheet, reihe, 10);
                                break;
                            case 3:
                                writeInFile(matcher.group().substring(5).trim(), sheet, reihe, 13);
                                break;
                        }
                    }
                    // Finde Formula

                    pattern = Pattern.compile("Formula:\\s?[\\d,\\w]+\\s?", Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(s);
                    if (matcher.find()) {
                        switch (wichProp){
                            case 1:
                                writeInFile(matcher.group().substring(8).trim(), sheet, reihe, 8);
                                break;
                            case 2:
                                writeInFile(matcher.group().substring(8).trim(), sheet, reihe, 11);
                                break;
                            case 3:
                                writeInFile(matcher.group().substring(8).trim(), sheet, reihe, 14);
                                break;
                        }
                    }
                }
            }


        }

        for (int i = 0; i < 20; i++) {
            sheet.autoSizeColumn(i);
        }

        fr.close();
        try {
            //mache einen FileOutputStream, den wir später verwenden um Sachen
            //auf unserer Festplatte speichern zu können
            FileOutputStream output = new FileOutputStream(pathTo + dateiName + ".xls");

            //schreibe die Datei auf unsere Festplatte
            workbook.write(output);

            //beenden wir das Ganze, indem wir das Output wieder schließen
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeInFile(String text, Sheet sheet, Row row, int cellNum) {
        row.createCell(cellNum).setCellValue(text);
    }
}