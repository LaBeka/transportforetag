package carSystem.domain;

import carSystem.domain.types.LicenceType;

public class Driver {

    private String name;
    private LicenceType licenceType;
    private int experience;

    public Driver(Builder builder) {
        this.name = builder.name;
        this.licenceType = builder.licenceType;
        this.experience = builder.experience;
    }

    public static class Builder {
        private String name;
        private LicenceType licenceType;
        private int experience;

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder licenceType(LicenceType licenceType) {
            this.licenceType = licenceType;
            return this;
        }
        public Builder experience(int experience) {
            this.experience = experience;
            return this;
        }
        public Driver build() {
            return new Driver(this);
        }
    }

    @Override
    public String toString() {
        return name + " licenceType (" + licenceType + ") total year of experience " + experience + " year)";
    }
}
