package ru.mkrf.label.util.configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
public class LabelConfig {
    private String pathToDB;
    private String pathToMedia;
    private String urlMedia;

    public LabelConfig() {
    }

    public LabelConfig(String pathToDB, String pathToMedia, String urlMedia) {
        this.pathToDB = pathToDB;
        this.pathToMedia = pathToMedia;
        this.urlMedia = urlMedia;
    }

    public String getPathToDB() {
        return pathToDB;
    }

    @XmlElement(name = "path_to_db_file")
    public void setPathToDB(String pathToDB) {
        this.pathToDB = pathToDB;
    }

    public String getPathToMedia() {
        return pathToMedia;
    }

    @XmlElement(name = "path_to_media")
    public void setPathToMedia(String pathToMedia) {
        this.pathToMedia = pathToMedia;
    }

    public String getUrlMedia() {
        return urlMedia;
    }

    @XmlElement(name = "url_media")
    public void setUrlMedia(String urlMedia) {
        this.urlMedia = urlMedia;
    }
}
