import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GenerateFile {

    private static File inputFile;
    private static File sqlFile;
    private static Scanner scan;
    private static ArrayList<String> attributeNames = new ArrayList<>();
    private static ArrayList<String> values = new ArrayList<>();
    private static String fileName;
    private static int numOfAttribute;


    public static void createSQLFile(File input, String outputFileName, String outputFilePath){

        sqlFile = new File("resources/" + outputFileName + ".sql");
        inputFile = input;
        fileName = getFileName(input);
        writeFile();
        System.out.println(sqlFile.getName() + " is succesfully saved in " + sqlFile.getAbsolutePath());
    }

    private static String getFileName(File input){

        String[] fileNameElements = input.getName().split("[.]");

        return fileNameElements[0];
    }

    private static void writeFile(){

        String toWrite = getDDLFormat() + "\n\n" + getDMLFormat();
        PrintWriter write = null;

        try {
            write = new PrintWriter(sqlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        write.print(toWrite);
        write.close();
    }

    private static String getDMLFormat(){

        String toReturn = "";
        String insertCommand = "INSERT INTO `" + fileName +  "` VALUES (";
        String valuesToInsert = "";

        for (int i = 1; i < values.size(); i++) {

            if (i % (numOfAttribute) != 0) {
                valuesToInsert += "'" + values.get(i - 1) + "', ";

            } else {
                valuesToInsert += "'" + values.get(i - 1) + "');";
                toReturn += insertCommand + valuesToInsert + "\n";
                valuesToInsert = "";
            }
        }

        return toReturn;
    }

    private static String getDDLFormat(){

        columnSetup();

        String toReturn = "CREATE TABLE " + fileName + " (";

        for (int i = 0; i < attributeNames.size(); i++) {

            if (i == attributeNames.size() - 1){
                toReturn += "\n\t" + attributeNames.get(i) + " varChar(255)";
            }
            else{
                toReturn += "\n\t" + attributeNames.get(i) + " varChar(255),";
            }
        }

        toReturn += "\n);";

        return toReturn;

    }

    private static void columnSetup(){

        try {
            scan = new Scanner(inputFile);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int lineCount = 0;
        while(scan.hasNextLine()){

            String[] list = scan.nextLine().split(";");
            numOfAttribute = list.length;
            lineCount ++;

            if (lineCount == 1){
                for (String column : list) {
                    attributeNames.add(column);
                }
            }
            else{
                for (String column : list) {
                    values.add(column);
                }
            }
        }
    }

}