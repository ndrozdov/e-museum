package ru.mkrf.label.entity;

public class ExhibitConfig {
    public static final String GET_MEDIA_FROM_EXHIBIT_BY_TERMINAL_ID =
            "SELECT\n" +
            "  'content/media/' || m.file_name AS bigMedia\n" +
            "  , 'content/media/' || m.file_name AS smallMedia\n" +
            "  , e.title\n" +
            "  , e.content AS html\n" +
            "  , e.fullscreen\n" +
            "  , e.sortord\n" +
            "FROM\n" +
            "  exhibit e\n" +
            "    LEFT JOIN media m ON e.media_id = m.ROWID\n" +
            "WHERE\n" +
            "  e.terminal_id = :terminal_id" +
            "  AND e.deleted <> 1;";

    private String bigMedia;
    private String smallMedia;
    private String title;
    private String html;
    private Integer fullscreen;
    private Integer sortord;

    public ExhibitConfig() {
    }

    public ExhibitConfig(String bigMedia, String smallMedia, String title, String html, Integer fullscreen, Integer sortord) {
        this.bigMedia = bigMedia;
        this.smallMedia = smallMedia;
        this.title = title;
        this.html = html;
        this.fullscreen = fullscreen;
        this.sortord = sortord;
    }

    public String getBigMedia() {
        return bigMedia;
    }

    public void setBigMedia(String bigMedia) {
        this.bigMedia = bigMedia;
    }

    public String getSmallMedia() {
        return smallMedia;
    }

    public void setSmallMedia(String smallMedia) {
        this.smallMedia = smallMedia;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(Integer fullscreen) {
        this.fullscreen = fullscreen;
    }

    public Integer getSortord() {
        return sortord;
    }

    public void setSortord(Integer sortord) {
        this.sortord = sortord;
    }
}
