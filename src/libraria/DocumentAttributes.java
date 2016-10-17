package libraria;

class DocumentAttributes {

    //OS
    private String osSize = "null";
    private String osCreationTime = "null";
    private String osLastAccessTime = "null";
    private String osLastModifiedTime = "null";

    //Custom
    private String creator = "null";
    private String created = "null";
    private String modified = "null";
    private String lastPrinted = "null";

    String getOsSize() {
        return osSize != null? osSize : "";
    }

    void setOsSize(String osSize) {
        this.osSize = osSize;
    }

    String getOsCreationTime() {
        return osCreationTime != null? osCreationTime : "";
    }

    void setOsCreationTime(String osCreationTime) {
        this.osCreationTime = osCreationTime;
    }

    String getOsLastAccessTime() {
        return osLastAccessTime != null? osLastAccessTime : "";
    }

    void setOsLastAccessTime(String osLastAccessTime) {
        this.osLastAccessTime = osLastAccessTime;
    }

    String getOsLastModifiedTime() {
        return osLastModifiedTime != null? osLastModifiedTime : "";
    }

    void setOsLastModifiedTime(String osLastModifiedTime) {
        this.osLastModifiedTime = osLastModifiedTime;
    }

    String getCreated() {
        return created != null? created : "";
    }

    void setCreated(String created) {
        this.created = created;
    }

    String getCreator() {
        return creator != null? creator : "";
    }

    void setCreator(String creator) {
        this.creator = creator;
    }

    String getLastPrinted() {
        return lastPrinted != null? lastPrinted : "";
    }

    void setLastPrinted(String lastPrinted) {
        this.lastPrinted = lastPrinted;
    }

    String getModified() {
        return modified != null? modified : "";
    }

    void setModified(String modified) {
        this.modified = modified;
    }
}
