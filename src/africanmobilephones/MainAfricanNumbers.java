package africanmobilephones;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainAfricanNumbers {

    private String path;
    private ArrayList elencoNumeriCorrettiInPartenza = new ArrayList();
    private ArrayList elencoNumeriCorrettiDopo = new ArrayList();
    private ArrayList elencoNumeriNonCorretti = new ArrayList();

    public static void main(String[] args) throws IOException, URISyntaxException {
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

    public void parseNumbers() throws IOException, URISyntaxException {
        this.elencoNumeriCorrettiInPartenza = new ArrayList();
        this.elencoNumeriCorrettiDopo = new ArrayList();
        this.elencoNumeriNonCorretti = new ArrayList();
        HashMap<String, String> hashIdNumeri = new HashMap<String, String>();

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

            hashIdNumeri.put(columns[0], this.isNumberGoodShaped(pattern, columns[1]));
        }

        /**
         ********************************
         * HTML FILE CREATION *******************************
         */
        File fileOutput = new File(java.util.ResourceBundle.getBundle("africanmobilephones/props").getString("outputPath"));
        FileWriter fileWriter = new FileWriter(fileOutput);
        BufferedWriter bufWriter = new BufferedWriter(fileWriter);

        /**
         *****************************
         * RESULTS FILE CREATION ****************************
         */
        File fileResults = new File(java.util.ResourceBundle.getBundle("africanmobilephones/props").getString("resultsPath"));
        FileWriter resultsWriter = new FileWriter(fileResults);
        BufferedWriter bufResultsWriter = new BufferedWriter(resultsWriter);

        bufResultsWriter.write("id,results\n");
        // Iterating HashMap through for loop
        for (HashMap.Entry<String, String> set
                : hashIdNumeri.entrySet()) {

            bufResultsWriter.write(set.getKey() + "," + set.getValue() + "\n");
        }

        bufWriter.write("<HTML><BODY>");
        bufWriter.write("<H3>Results have been stored also into " + java.util.ResourceBundle.getBundle("africanmobilephones/props").getString("resultsPath") + " </H3><BR>");
        bufWriter.write("<P style=\"text-align:center;\"><H3 style=\"text:center;\">GOOD NUMBERS </H3>");
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

        bufResultsWriter.close();
        resultsWriter.close();

        Desktop.getDesktop().browse(fileOutput.toURI());

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(fileOutput.toURI());
        }

    }

    public String isNumberGoodShaped(Pattern pattern, String number) {
        String daRestituire = number + " Number Not Correctly Formed!";
        Matcher matcher = pattern.matcher(number);
        if (matcher.matches() && (number.startsWith("27"))) {

            daRestituire = number + " Number Good Shaped!";
            elencoNumeriCorrettiInPartenza.add(number);
        } else {
            if (!matcher.matches() && (number.startsWith("27"))) {
                if (number.length() > 11) {
                    String telefonoCorretto = number.substring(0, 11);
                    daRestituire = number + " Number good if truncated --> " + telefonoCorretto;
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
