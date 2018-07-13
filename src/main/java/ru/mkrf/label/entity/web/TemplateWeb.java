package ru.mkrf.label.entity.web;

import ru.mkrf.label.entity.db.Template;
import ru.mkrf.label.entity.db.Title;

import java.util.List;

public class TemplateWeb extends Template{
    private List<Title> titles;
    private List<MediaWeb> medias;

    public TemplateWeb() {
    }

    public TemplateWeb(Template template, List<Title> titles, List<MediaWeb> medias) {
        super(template.getTreeId(), template.getDeleted(), template.getSortord());
        this.titles = titles;
        this.medias = medias;
    }

    public List<MediaWeb> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaWeb> medias) {
        this.medias = medias;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }
}
