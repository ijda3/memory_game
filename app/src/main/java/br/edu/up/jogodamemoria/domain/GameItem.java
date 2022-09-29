package br.edu.up.jogodamemoria.domain;

public class GameItem {
    private final int id;
    private String status;

    public GameItem(int id) {
        this.id = id;
        this.status = "available";
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
