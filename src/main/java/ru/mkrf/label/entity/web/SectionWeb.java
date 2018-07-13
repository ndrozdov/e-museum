package ru.mkrf.label.entity.web;

import ru.mkrf.label.entity.db.Grid;
import ru.mkrf.label.entity.db.Section;
import ru.mkrf.label.entity.db.Title;

import java.util.List;

public class SectionWeb extends Section {
    private Grid grid;
    private List<Title> titles;
    private List<MediaWeb> medias;

    public SectionWeb() {
    }

    public SectionWeb(Section section, Grid grid, List<Title> titles, List<MediaWeb> medias) {
        super(section.getTreeId(), section.getDeleted(), section.getSortord());
        this.grid = grid;
        this.titles = titles;
        this.medias = medias;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public List<MediaWeb> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaWeb> medias) {
        this.medias = medias;
    }
}
