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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainAfricanNumbers {

    private String path;
    private ArrayList elencoNumeriCorrettiInPartenza = new ArrayList();
    private ArrayList elencoNumeriCorrettiDopo = new ArrayList();
    private ArrayList elencoNumeriNonCorretti = new ArrayList();

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MainAfricanNumbers ogg = new MainAfricanNumbers(args[0]);
        try {
            ogg.parseNumbers();
        } catch (IOException ex) {
            Logger.getLogger(MainAfricanNumbers.class.getName()).log(Level.SEVERE, "An IOException has been caught! Read the stack trace.", ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MainAfricanNumbers.class.getName()).log(Level.SEVERE, "An URIException has been caught! Read the stack trace.", ex);
        }
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
        HashMap<String, String> hashIdNumeri = new HashMap<>();

        BufferedReader br = Files.newBufferedReader(Paths.get(this.path));

        // CSV file delimiter
        String DELIMITER = ",";

        // read the file line by line
        String line;
        Pattern pattern = Pattern.compile("^\\d{11}$");

        while ((line = br.readLine()) != null) {

            // convert line into columns
            String[] columns = line.split(DELIMITER);

            // print all columns - uncomment following line to DEBUG
            //System.out.println("Linea[" + String.join(", ", columns) + "]");
            hashIdNumeri.put(columns[0], this.isNumberGoodShaped(pattern, columns[1]));
        }

        /**
         ********************************
         * HTML FILE CREATION *******************************
         */
        File htmlFileOutput = new File(java.util.ResourceBundle.getBundle("africanmobilephones/props").getString("outputPath"));
        FileWriter htmlFileWriter = new FileWriter(htmlFileOutput);
        BufferedWriter htmlBufferedWriter = new BufferedWriter(htmlFileWriter);

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

        htmlBufferedWriter.write("<HTML><BODY>");
        htmlBufferedWriter.write("<P style=\"text-align:center;\">");
        htmlBufferedWriter.write("<H2>Following you can find CSV file numbers parsing results.<BR>");
        htmlBufferedWriter.write("Please note that these Results have been stored also into " + java.util.ResourceBundle.getBundle("africanmobilephones/props").getString("resultsPath") + " </H2><BR>");

        writeHTMLList(htmlBufferedWriter, elencoNumeriCorrettiInPartenza, "GOOD AFRICAN MOBILE NUMBERS");

        writeHTMLList(htmlBufferedWriter, elencoNumeriCorrettiDopo, "GOOD AFRICAN MOBILE NUMBERS AFTER TRUNCATE");

        writeHTMLList(htmlBufferedWriter, elencoNumeriNonCorretti, "BAD SHAPED NUMBERS");

        htmlBufferedWriter.write("</P></HTML></BODY>");

        htmlBufferedWriter.close();
        htmlFileWriter.close();

        bufResultsWriter.close();
        resultsWriter.close();

        Desktop.getDesktop().browse(htmlFileOutput.toURI());

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(htmlFileOutput.toURI());
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

                }
            } else {
                elencoNumeriNonCorretti.add(number);
            }
        }

        return daRestituire;
    }

    private void writeHTMLList(BufferedWriter htmlBufferedWriter, ArrayList elencoNumeri, String message) throws IOException {
        htmlBufferedWriter.write("<H3 style=\"text-align:center;\">" + message + "</H3><HR><OL>\n");

        for (int i = 0; i < elencoNumeri.size(); i++) {
            if (((String)elencoNumeri.get(i)).matches("[+-]?\\d*(\\.\\d+)?")) {
                htmlBufferedWriter.write("<LI>" + (String) elencoNumeri.get(i) + "</LI>\n");
            }
        }
        htmlBufferedWriter.write("\n</OL></TABLE></CENTER>");
    }
}
