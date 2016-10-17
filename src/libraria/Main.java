package libraria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Path mTargetDirectory;
    private static List<SomeDocument> mDocuments = new ArrayList<>();

    public static void main(String[] args) {

        if(args.length < 2) {
            return;
        }

        Path inputFile = Paths.get(args[0]);
        mTargetDirectory = Paths.get(args[1]);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile.toString()))) {
            String line;
            while ((line = br.readLine()) != null) {
                mDocuments.add(new SomeDocument(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mDocuments.forEach(document -> document.copyTo(mTargetDirectory.toString()));
        new HtmlReport(mDocuments).writeHTML(mTargetDirectory);
    }

    /*private static void writeTXT() {

        String txtReport = mTargetDirectory.resolve("report.txt").toString();

        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtReport), StandardCharsets.UTF_16))) {
            doWriteTXT(writer);
        } catch (IOException ex) {
            log.log(Level.WARNING, "can not write  \"" + txtReport + "\"");
        }
    }

    private static void doWriteTXT(Writer writer) throws IOException {

        for(SomeDocument document : mDocuments) {

            writer.write(System.lineSeparator() + document.getPath() + System.lineSeparator());

            List<DocumentAttributes> OSAttributes = document.getOSAttributes();

            if (OSAttributes != null) {

                writer.write("OS" + System.lineSeparator());

                for (DocumentAttributes attribute : OSAttributes) {
                    writer.write(attribute.getAttributeName() + ": ");
                    writer.write(attribute.getAttributeValue() + System.lineSeparator());
                }
            }

            List<DocumentAttributes> MicrosoftAttributes = document.getPOIAttributes();

            if (MicrosoftAttributes != null) {

                writer.write("Microsoft" + System.lineSeparator());

                for (DocumentAttributes attribute : MicrosoftAttributes) {
                    writer.write(attribute.getAttributeName() + ": ");
                    writer.write(attribute.getAttributeValue() + System.lineSeparator());
                }
            }
        }
    }
*/

}


//java -jar /Users/quake/IdeaProjects/xxx/out/artifacts/tabularia_jar/tabularia.jar file.txt /Users/quake/Desktop/
//https://pdfbox.apache.org/download.cgi
//http://svn.apache.org/repos/asf/poi/trunk/src/ooxml/java/org/apache/poi/POIXMLPropertiesTextExtractor.java
//!!!  http://tika.apache.org ///http://stackoverflow.com/questions/18723038/get-metadata-from-open-office-files
// https://www.tutorialspoint.com/tika/tika_metadata_extraction.htm
