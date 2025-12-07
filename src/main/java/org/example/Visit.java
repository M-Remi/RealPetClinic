package org.example;

class Visit {
    private Long id;
    private String date, description;
    private Long petId;

    public Visit(String date, String description, Long petId) {
        this.date = date; this.description = description; this.petId = petId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public Long getPetId() { return petId; }

    public String toString() {
        return "Visit[ID=" + id + ", Date=" + date + ", Description=" + description +
                ", PetID=" + petId + "]";
    }

    public String toFileString() {
        return id + "|" + date + "|" + description + "|" + petId;
    }

    public static Visit fromFileString(String line) {
        String[] parts = line.split("\\|");
        Visit visit = new Visit(parts[1], parts[2], Long.parseLong(parts[3]));
        visit.setId(Long.parseLong(parts[0]));
        return visit;
    }
}

