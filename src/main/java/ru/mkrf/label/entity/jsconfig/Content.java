package ru.mkrf.label.entity.jsconfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import ru.mkrf.label.entity.db.Text;
import ru.mkrf.label.entity.db.Title;
import ru.mkrf.label.entity.db.Tree;
import ru.mkrf.label.entity.web.MediaWeb;
import ru.mkrf.label.entity.web.PageWeb;
import ru.mkrf.label.entity.web.SectionWeb;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Content implements Comparable<Content> {
    private Tree tree;
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GridJsConfig grid;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> titles;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> texts;
    private List<MediaJsConfig> medias;
    private Integer sortord;
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Content> contents;

    public Content() {
    }

    public Content(PageWeb page, Tree tree, List<MediaWeb> medias) {
        this.type = "page";
        this.titles = page.getTitles() != null ?
                page.getTitles().stream().map(Title::getTitle).collect(Collectors.toList()) : null;

        this.texts = page.getTexts() != null ?
                page.getTexts().stream().map(Text::getText).collect(Collectors.toList()) : null;

        this.medias = medias != null ?
                medias.stream().map(MediaJsConfig::new).collect(Collectors.toList()) : null;

        this.tree = tree;
        this.sortord = page.getSortord();
    }

    public Content(SectionWeb section, Tree tree, List<MediaWeb> medias) {
        this.type = "section";
        this.titles = section.getTitles() != null ?
                section.getTitles().stream().map(Title::getTitle).collect(Collectors.toList()) : null;
        this.grid = section.getGrid() != null ? new GridJsConfig(section.getGrid()) : null;

        this.medias = medias != null ?
                medias.stream().map(MediaJsConfig::new).collect(Collectors.toList()) : null;

        this.tree = tree;
        this.sortord = section.getSortord();
    }

    public Tree getTree() {
        return tree;
    }

    @JsonIgnore
    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GridJsConfig getGrid() {
        return grid;
    }

    public void setGrid(GridJsConfig grid) {
        this.grid = grid;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public List<MediaJsConfig> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaJsConfig> medias) {
        this.medias = medias;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

//    @JsonIgnore
    public Integer getSortord() {
        return sortord;
    }

    public void setSortord(Integer sortord) {
        this.sortord = sortord;
    }

    @Override
    public int compareTo(Content o) {
        int sortord = o.getSortord();
        return this.sortord - sortord;
    }
}
