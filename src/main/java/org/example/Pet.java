package org.example;

class Pet {
    private Long id;
    private String name;
    private String birthDate;
    private String type;
    private Long ownerId;

    public Pet(String name, String birthDate, String type, Long ownerId) {
        this.name = name; this.birthDate = birthDate;
        this.type = type; this.ownerId = ownerId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBirthDate() { return birthDate; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getOwnerId() { return ownerId; }

    public String toString() {
        return "Pet[ID=" + id + ", Name=" + name + ", Type=" + type +
                ", BirthDate=" + birthDate + ", OwnerID=" + ownerId + "]";
    }

    public String toFileString() {
        return id + "|" + name + "|" + birthDate + "|" + type + "|" + ownerId;
    }

    public static Pet fromFileString(String line) {
        String[] parts = line.split("\\|");
        Pet pet = new Pet(parts[1], parts[2], parts[3], Long.parseLong(parts[4]));
        pet.setId(Long.parseLong(parts[0]));
        return pet;
    }
}
