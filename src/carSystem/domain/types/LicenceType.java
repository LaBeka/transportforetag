package carSystem.domain.types;

public enum LicenceType {
    A("a"),
    B("b"),
    C("c");

    public String value;
    private LicenceType(String value) {
        this.value = value;
    }
}
