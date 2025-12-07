package org.example;

class Owner {
    private Long id;
    private String firstName, lastName, address, phone;

    public Owner(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName; this.lastName = lastName;
        this.address = address; this.phone = phone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String toString() {
        return "Owner[ID=" + id + ", Name=" + firstName + " " + lastName +
                ", Address=" + address + ", Phone=" + phone + "]";
    }

    public String toFileString() {
        return id + "|" + firstName + "|" + lastName + "|" + address + "|" + phone;
    }

    public static Owner fromFileString(String line) {
        String[] parts = line.split("\\|");
        Owner owner = new Owner(parts[1], parts[2], parts[3], parts[4]);
        owner.setId(Long.parseLong(parts[0]));
        return owner;
    }
}
