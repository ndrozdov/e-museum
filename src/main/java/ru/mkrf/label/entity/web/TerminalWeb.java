package ru.mkrf.label.entity.web;

import ru.mkrf.label.entity.db.Grid;
import ru.mkrf.label.entity.db.Terminal;
import ru.mkrf.label.entity.db.Title;

import java.util.List;

public class TerminalWeb extends Terminal {
    private Grid grid;
    private List<Title> titles;

    public TerminalWeb() {
    }

    public TerminalWeb(Terminal terminal, Grid grid, List<Title> titles) {
        super(
                terminal.getTreeId()
                , terminal.getTemplateId()
                , terminal.getVersion()
                , terminal.getTerminalVersion()
                , terminal.getLastActiveTime()
                , terminal.getDeleted()
                , terminal.getSortord()
        );

        this.grid = grid;
        this.titles = titles;
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

    @Override
    public String toString() {
        return "TerminalWeb{" +
                "grid=" + grid +
                ", titles=" + titles +
                '}';
    }
}
