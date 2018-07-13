package ru.mkrf.label.entity.jsconfig;

import ru.mkrf.label.entity.web.TerminalWeb;

import java.util.List;

public class TerminalJSConfig {
    private String type;
    private GridJsConfig grid;
    private List<Content> contents;

    public TerminalJSConfig() {
    }

    public TerminalJSConfig(TerminalWeb terminal, List<Content> contents) {
        this.type = "terminal";
        this.grid = new GridJsConfig(terminal.getGrid());
        this.contents = contents;
    }

    public TerminalJSConfig(GridJsConfig grid, List<Content> contents) {
        this.type = "terminal";
        this.grid = grid;
        this.contents = contents;
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

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
