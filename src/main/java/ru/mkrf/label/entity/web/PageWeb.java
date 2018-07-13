package ru.mkrf.label.entity.web;

import ru.mkrf.label.entity.db.Page;
import ru.mkrf.label.entity.db.Text;
import ru.mkrf.label.entity.db.Title;

import java.util.List;

public class PageWeb extends Page {
    private List<Title> titles;
    private List<Text> texts;
    private List<MediaWeb> medias;

    public PageWeb() {
    }

    public PageWeb(
            Page page
            , List<Title> titles
            , List<Text> texts
            , List<MediaWeb> medias
    ) {
        super(
                page.getTreeId()
                , page.getContent()
                , page.getFullscreen()
                , page.getDeleted()
                , page.getSortord()
        );
        this.titles = titles;
        this.texts = texts;
        this.medias = medias;
    }

    public PageWeb(List<Title> titles, List<Text> texts) {
        this.titles = titles;
        this.texts = texts;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public List<Text> getTexts() {
        return texts;
    }

    public void setTexts(List<Text> texts) {
        this.texts = texts;
    }

    public List<MediaWeb> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaWeb> medias) {
        this.medias = medias;
    }
}
