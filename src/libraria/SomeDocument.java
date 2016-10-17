package libraria;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.*;

class SomeDocument {

    private Path mPath;
    private DocumentAttributes documentAttributes = new DocumentAttributes();
    private static Logger log = Logger.getAnonymousLogger();

    static {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
                                        public String format(LogRecord record) {
                                            return record.getLevel() + ": " + record.getMessage() + "\n";
                                        }});
        log.setUseParentHandlers(false);
        log.addHandler(consoleHandler);
    }

    SomeDocument(String source) {
        mPath = Paths.get(source);

        getOSAttributes();
        getCustomAttributes();
    }

    private void getOSAttributes() {

        if(!Files.exists(mPath) || !Files.isRegularFile(mPath)) {
            log.log(Level.WARNING, "file \"" + mPath + "\" does not exist");
            return;
        }

        BasicFileAttributes basicFileAttributes;

        try {
            basicFileAttributes = Files.readAttributes(mPath, BasicFileAttributes.class);
        } catch (IOException e) {
            log.log(Level.WARNING, "can not read OS attributes for \"" + mPath + "\"");
            return;
        }

        documentAttributes.setOsSize(formatFileSize(basicFileAttributes.size()));
        documentAttributes.setOsCreationTime(formatFileTime(basicFileAttributes.creationTime()));
        documentAttributes.setOsLastAccessTime(formatFileTime(basicFileAttributes.lastAccessTime()));
        documentAttributes.setOsLastModifiedTime(formatFileTime(basicFileAttributes.lastModifiedTime()));

        //log.log(Level.INFO, "read OS attributes for \"" + mPath + "\"");
    }

    private void getCustomAttributes() {

        try(FileInputStream fis = new FileInputStream(mPath.toString())) {

            Metadata metadata = new Metadata();

            metadata.set(Metadata.RESOURCE_NAME_KEY, mPath.getFileName().toString());

            new AutoDetectParser().parse(fis, new BodyContentHandler(), metadata, new ParseContext());

            documentAttributes.setCreator(metadata.get(TikaCoreProperties.CREATOR));
            documentAttributes.setCreated(metadata.get(TikaCoreProperties.CREATED));
            documentAttributes.setModified(metadata.get(TikaCoreProperties.MODIFIED));
            documentAttributes.setLastPrinted(metadata.get(TikaCoreProperties.PRINT_DATE));

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    void copyTo(String targetDirectory) {

        if(!Files.exists(mPath) || !Files.isRegularFile(mPath)) {
            log.log(Level.WARNING, "file \"" + mPath + "\" does not exist");
            return;
        }

        Path targetPath = Paths.get(targetDirectory)
                .resolve(mPath.subpath(0, mPath.getNameCount()))
                .normalize();

        try {
            Files.createDirectories(targetPath.getParent());
        } catch (IOException e) {
            log.log(Level.WARNING, "can not create directory \"" + targetPath.getParent() + "\"");
            return;
        }

        try {
            Files.copy(mPath, targetPath, StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            log.log(Level.WARNING, "can not copy \"" + mPath + "\" to \"" + targetPath + "\"");
            return;
        }

        log.log(Level.INFO, "\"" + mPath + "\" -> \"" + targetPath + "\"");
    }

    private String formatFileTime(FileTime fileTime) {
        if(fileTime == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss");
        return df.format(fileTime.toMillis());
    }

    private String formatFileSize(long size) {
        if(size <= 0) {
            return null;
        }
        String[] units = new String[] { "b", "kb", "mb", "gb", "tb" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    String getPath() {
        return mPath.toAbsolutePath().normalize().toString();
    }

    DocumentAttributes getDocumentAttributes() {
        return this.documentAttributes;
    }

}
