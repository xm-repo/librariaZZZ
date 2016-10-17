package libraria;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

class HtmlReport {

    private static Logger log = Logger.getLogger(HtmlReport.class.getName());

    static {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            public String format(LogRecord record) {
                return record.getLevel() + ": " + record.getMessage() + "\n";
            }});
        log.setUseParentHandlers(false);
        log.addHandler(consoleHandler);
    }

    private List<SomeDocument> mDocuments = new ArrayList<>();

    HtmlReport(List<SomeDocument> documents) {
        mDocuments = documents;
    }

    void writeHTML(Path targetDirectory) {

        if(targetDirectory == null || mDocuments.isEmpty()) {
            return;
        }

        String htmlReport = targetDirectory.resolve("report.html").toString();

        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlReport), StandardCharsets.UTF_16))) {
            doWriteHTML(writer);
        } catch (IOException ex) {
            log.log(Level.WARNING, "can not write  \"" + htmlReport + "\"");
        }
    }

    private void doWriteHTML(Writer writer) throws IOException {

        writer.write("<!DOCTYPE html>\n");

        writer.write("<html>\n");

        writer.write("<head>\n");
        writer.write("<style>\n");
        writer.write("table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; }\n");
        writer.write("table tr:nth-child(even) { background-color: #dddddd; }\n");
        writer.write("th { background-color: #87cefa; padding: 10px; }");
        writer.write("</style>\n");
        writer.write("</head>\n");

        writer.write("<body>\n");

        writer.write("<table style=\"width:100%\">\n");

        addTH(writer, "File");

        addTH(writer, "OSSize");
        addTH(writer, "OSCreationTime");
        addTH(writer, "OSLastAccessTime");
        addTH(writer, "OSLastModifiedTime");

        addTH(writer, "Creator");
        addTH(writer, "Created");
        addTH(writer, "Modified");
        addTH(writer, "LastPrinted");

        for(SomeDocument document : mDocuments) {

            writer.write("<tr>\n");

            addTD(writer, document.getPath());

            addTD(writer, document.getDocumentAttributes().getOsSize());
            addTD(writer, document.getDocumentAttributes().getOsCreationTime());
            addTD(writer, document.getDocumentAttributes().getOsLastAccessTime());
            addTD(writer, document.getDocumentAttributes().getOsLastModifiedTime());

            addTD(writer, document.getDocumentAttributes().getCreator());
            addTD(writer, document.getDocumentAttributes().getCreated());
            addTD(writer, document.getDocumentAttributes().getModified());
            addTD(writer, document.getDocumentAttributes().getLastPrinted());

            writer.write("</tr>\n");
        }

        writer.write("</table>\n");

        writer.write("</body>\n");
        writer.write("</html>\n");
    }

    private void addTD(Writer writer, String value) throws IOException {
        writer.write("<td>");
        writer.write(value);
        writer.write("</td>\n");
    }

    private void addTH(Writer writer, String value) throws IOException {
        writer.write("<th>");
        writer.write(value);
        writer.write("</th>\n");
    }

}
