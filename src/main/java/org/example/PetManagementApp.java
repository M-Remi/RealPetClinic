package org.example;
import java.io.*;
import java.util.*;
import java.util.List;
public class PetManagementApp {
    private static Scanner scanner = new Scanner(System.in);
    private static OwnerService ownerService = new OwnerService();
    private static PetService petService = new PetService();
    private static VisitService visitService = new VisitService();
    private static DataStorage storage = new DataStorage();
    public static void main(String[] args) {
        // Load data from file
        storage.loadData(ownerService, petService, visitService);

        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: manageOwners(); break;
                case 2: managePets(); break;
                case 3: manageVisits(); break;
                case 4: viewReports(); break;
                case 5: saveAndExit(); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== PET CLINIC MANAGEMENT SYSTEM ===");
        System.out.println("1. Manage Owners");
        System.out.println("2. Manage Pets");
        System.out.println("3. Manage Visits");
        System.out.println("4. View Reports");
        System.out.println("5. Save & Exit");
    }

    private static void manageOwners() {
        while (true) {
            System.out.println("\n--- Owner Management ---");
            System.out.println("1. Add Owner  2. View All  3. Search  4. Update  5. Delete  6. Back");
            int choice = getIntInput("Choice: ");

            if (choice == 6) break;
            switch (choice) {
                case 1: addOwner(); break;
                case 2: viewAllOwners(); break;
                case 3: searchOwner(); break;
                case 4: updateOwner(); break;
                case 5: deleteOwner(); break;
            }
        }
    }

    private static void addOwner() {
        System.out.println("\n--- Add New Owner ---");
        String firstName = getInput("First Name: ");
        String lastName = getInput("Last Name: ");
        String address = getInput("Address: ");
        String phone = getInput("Phone: ");

        Owner owner = new Owner(firstName, lastName, address, phone);
        ownerService.save(owner);
        System.out.println("Owner added successfully! ID: " + owner.getId());
    }

    private static void viewAllOwners() {
        List<Owner> owners = ownerService.findAll();
        if (owners.isEmpty()) {
            System.out.println("No owners found.");
        } else {
            for (Owner owner : owners) {
                System.out.println(owner);
            }
        }
    }

    private static void searchOwner() {
        String lastName = getInput("Enter last name to search: ");
        List<Owner> owners = ownerService.findByLastName(lastName);
        if (owners.isEmpty()) {
            System.out.println("No owners found.");
        } else {
            for (Owner owner : owners) {
                System.out.println(owner);
            }
        }
    }

    private static void updateOwner() {
        long id = getLongInput("Enter Owner ID: ");
        Owner owner = ownerService.findById(id);
        if (owner == null) {
            System.out.println("Owner not found!");
            return;
        }
        System.out.println("Current: " + owner);
        owner.setAddress(getInput("New Address (or press Enter to keep): ", owner.getAddress()));
        owner.setPhone(getInput("New Phone (or press Enter to keep): ", owner.getPhone()));
        ownerService.save(owner);
        System.out.println("Owner updated successfully!");
    }
    private static void deleteOwner() {
        long id = getLongInput("Enter Owner ID to delete: ");
        ownerService.delete(id);
        System.out.println("Owner deleted successfully!");
    }

    private static void managePets() {
        while (true) {
            System.out.println("\n--- Pet Management ---");
            System.out.println("1. Add Pet  2. View All  3. View by Owner  4. Update  5. Delete  6. Back");
            int choice = getIntInput("Choice: ");

            if (choice == 6) break;
            switch (choice) {
                case 1: addPet(); break;
                case 2: viewAllPets(); break;
                case 3: viewPetsByOwner(); break;
                case 4: updatePet(); break;
                case 5: deletePet(); break;
            }
        }
    }

    private static void addPet() {
        System.out.println("\n--- Add New Pet ---");
        long ownerId = getLongInput("Owner ID: ");
        Owner owner = ownerService.findById(ownerId);
        if (owner == null) {
            System.out.println("Owner not found!");
            return;
        }

        String name = getInput("Pet Name: ");
        String birthDate = getInput("Birth Date (YYYY-MM-DD): ");
        String type = getInput("Type (dog/cat/bird/etc): ");

        Pet pet = new Pet(name, birthDate, type, ownerId);
        petService.save(pet);
        System.out.println("Pet added successfully! ID: " + pet.getId());
    }
    private static void viewAllPets() {
        List<Pet> pets = petService.findAll();
        if (pets.isEmpty()) {
            System.out.println("No pets found.");
        } else {
            for (Pet pet : pets) {
                System.out.println(pet);
            }
        }
    }

    private static void viewPetsByOwner() {
        long ownerId = getLongInput("Enter Owner ID: ");
        List<Pet> pets = petService.findByOwnerId(ownerId);
        if (pets.isEmpty()) {
            System.out.println("No pets found for this owner.");
        } else {
            for (Pet pet : pets) {
                System.out.println(pet);
            }
        }
    }

    private static void updatePet() {
        long id = getLongInput("Enter Pet ID: ");
        Pet pet = petService.findById(id);
        if (pet == null) {
            System.out.println("Pet not found!");
            return;
        }
        System.out.println("Current: " + pet);
        pet.setName(getInput("New Name (or press Enter to keep): ", pet.getName()));
        pet.setType(getInput("New Type (or press Enter to keep): ", pet.getType()));
        petService.save(pet);
        System.out.println("Pet updated successfully!");
    }

    private static void deletePet() {
        long id = getLongInput("Enter Pet ID to delete: ");
        petService.delete(id);
        System.out.println("Pet deleted successfully!");
    }
    private static void manageVisits() {
        while (true) {
            System.out.println("\n--- Visit Management ---");
            System.out.println("1. Add Visit  2. View All  3. View by Pet  4. Delete  5. Back");
            int choice = getIntInput("Choice: ");

            if (choice == 5) break;
            switch (choice) {
                case 1: addVisit(); break;
                case 2: viewAllVisits(); break;
                case 3: viewVisitsByPet(); break;
                case 4: deleteVisit(); break;
            }
        }
    }

    private static void addVisit() {
        System.out.println("\n--- Add New Visit ---");
        long petId = getLongInput("Pet ID: ");
        Pet pet = petService.findById(petId);
        if (pet == null) {
            System.out.println("Pet not found!");
            return;
        }

        String date = getInput("Visit Date (YYYY-MM-DD): ");
        String description = getInput("Description: ");

        Visit visit = new Visit(date, description, petId);
        visitService.save(visit);
        System.out.println("Visit added successfully! ID: " + visit.getId());
    }

    private static void viewAllVisits() {
        List<Visit> visits = visitService.findAll();
        if (visits.isEmpty()) {
            System.out.println("No visits found.");
        } else {
            for (Visit visit : visits) {
                System.out.println(visit);
            }
        }
    }


    private static void viewVisitsByPet() {
        long petId = getLongInput("Enter Pet ID: ");
        List<Visit> visits = visitService.findByPetId(petId);
        if (visits.isEmpty()) {
            System.out.println("No visits found for this pet.");
        } else {
            for (Visit visit : visits) {
                System.out.println(visit);
            }
        }
    }

    private static void deleteVisit() {
        long id = getLongInput("Enter Visit ID to delete: ");
        visitService.delete(id);
        System.out.println("Visit deleted successfully!");
    }

    private static void viewReports() {
        System.out.println("\n=== REPORTS ===");
        System.out.println("Total Owners: " + ownerService.findAll().size());
        System.out.println("Total Pets: " + petService.findAll().size());
        System.out.println("Total Visits: " + visitService.findAll().size());
    }

    private static void saveAndExit() {
        storage.saveData(ownerService, petService, visitService);
        System.out.println("Data saved. Goodbye!");
    }

    private static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static String getInput(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number!");
            }
        }
    }

    private static long getLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number!");
            }
        }
    }


}
