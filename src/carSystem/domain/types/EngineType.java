package carSystem.domain.types;

public enum EngineType {
    ELECTRIC("electric"),
    PETROLEUM("petroleum"),
    DIESEL("diesel"),;

    private String type;

    EngineType(String type){
        this.type = type;
    }
}
