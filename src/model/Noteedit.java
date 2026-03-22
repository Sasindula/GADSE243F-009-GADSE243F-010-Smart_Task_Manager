package model;

public class Noteedit {
    private int noteId;
    private String title;
    private String content;

    public Noteedit(int noteId, String title, String content) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
