package dev.fejda.latinoamericacomparte.model.constant;

public enum Status {

    STATUS_DRAFT("Borrador"),
    STATUS_PUBLISHED("Publicado");

    private final String visualName;

    Status(String visualName){
        this.visualName = visualName;

    }

    public String getVisualName() {
        return visualName;
    }
}
