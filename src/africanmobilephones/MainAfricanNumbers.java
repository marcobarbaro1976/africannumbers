package africanmobilephones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainAfricanNumbers {

    private String path;
    private ArrayList elencoNumeriCorrettiInPartenza = new ArrayList();
    private ArrayList elencoNumeriCorrettiDopo = new ArrayList();
    private ArrayList elencoNumeriNonCorretti = new ArrayList();

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        MainAfricanNumbers ogg = new MainAfricanNumbers(args[0]);
        ogg.parseNumbers();

    }

    public MainAfricanNumbers(String path) {
        this.path = path;
    }

    public boolean isNumberCorrect(String number) {
        return elencoNumeriCorrettiInPartenza.contains(number);
    }

    public void parseNumbers() throws IOException {
        this.elencoNumeriCorrettiInPartenza = new ArrayList();
        this.elencoNumeriCorrettiDopo = new ArrayList();
        this.elencoNumeriNonCorretti = new ArrayList();

        BufferedReader br = Files.newBufferedReader(Paths.get(this.path));

        // CSV file delimiter
        String DELIMITER = ",";

        // read the file line by line
        String line;
        Pattern pattern = Pattern.compile("^\\d{11}$");

        while ((line = br.readLine()) != null) {

            // convert line into columns
            String[] columns = line.split(DELIMITER);

            // print all columns
            System.out.println("Linea[" + String.join(", ", columns) + "]");

            this.isNumberGoodShaped(pattern, columns[1]);
        }

        File fileOutput = new File("C:\\temp\\output.html");
        FileWriter fileWriter = new FileWriter(fileOutput);
        BufferedWriter bufWriter = new BufferedWriter(fileWriter);
        bufWriter.write("<HTML><BODY>");
        bufWriter.write("<P><H3 style=\"text:center;\">GOOD NUMBERS </H3>");
        bufWriter.write("\n<OL>");
        for (int i = 0; i < elencoNumeriCorrettiInPartenza.size(); i++) {
            bufWriter.write("<LI style=\"text:center;\">");
            bufWriter.write((String) elencoNumeriCorrettiInPartenza.get(i));
            bufWriter.write("</LI>");

        }
        bufWriter.write("\n</OL>");
        bufWriter.write("<P><H3 style=\"text:center;\">GOOD NUMBERS AFTER TRUNCATE</H3>");
        bufWriter.write("\n<OL>");
        for (int i = 0; i < elencoNumeriCorrettiDopo.size(); i++) {
            bufWriter.write("<LI style=\"text:center;\">");
            bufWriter.write((String) elencoNumeriCorrettiDopo.get(i));
            bufWriter.write("</LI>");

        }
        bufWriter.write("\n</OL>");
        bufWriter.write("<P><H3 style=\"text:center;\">BAD SHAPED NUMBERS</H3>");
        bufWriter.write("\n<OL>");
        for (int i = 0; i < elencoNumeriNonCorretti.size(); i++) {
            bufWriter.write("<LI style=\"text:center;\">");
            bufWriter.write((String) elencoNumeriNonCorretti.get(i));
            bufWriter.write("</LI>");

        }
        bufWriter.write("\n</OL>");

        bufWriter.write("</HTML></BODY>");
        bufWriter.close();
        fileWriter.close();
    }

    public String isNumberGoodShaped(Pattern pattern, String number) {
        String daRestituire = "Number Not Correctly Formed!";
        Matcher matcher = pattern.matcher(number);
        if (matcher.matches() && (number.startsWith("27"))) {

            daRestituire = "Number " + number + " is Good Shaped!";
            elencoNumeriCorrettiInPartenza.add(number);
        } else {
            if (!matcher.matches() && (number.startsWith("27"))) {
                if (number.length() > 11) {
                    String telefonoCorretto = number.substring(0, 11);
                    daRestituire = "Number good if truncated --> " + telefonoCorretto;
                    elencoNumeriCorrettiDopo.add(number + " ---> " + telefonoCorretto);

                } else {
                    System.out.println("else");
                }
            } else {
                elencoNumeriNonCorretti.add(number);
            }
        }

        return daRestituire;
    }
}
