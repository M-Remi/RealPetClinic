package org.example;
import java.util.*;
import java.util.List;

import java.util.logging.Logger;
public class PetManagementApp {
    static Logger logger = Logger.getLogger(PetManagementApp.class.getName());

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
                default: logger.info ("Invalid choice!");
            }
        }
    }

    private static void displayMainMenu() {
        logger.info("\n=== PET CLINIC MANAGEMENT SYSTEM ===");
        logger.info ("1. Manage Owners");
        logger.info("2. Manage Pets");
        logger.info("3. Manage Visits");
        logger.info("4. View Reports");
        logger.info("5. Save & Exit");
    }

    private static void manageOwners() {
        while (true) {
            logger.info("\n--- Owner Management ---");
            logger.info("1. Add Owner  2. View All  3. Search  4. Update  5. Delete  6. Back");
            int choice = getIntInput("Choice: ");

            if (choice == 6) break;
            switch (choice) {
                default: Default(); break;
                case 1: addOwner(); break;
                case 2: viewAllOwners(); break;
                case 3: searchOwner(); break;
                case 4: updateOwner(); break;
                case 5: deleteOwner(); break;
            }
        }
    }

    private static void Default() {


    }


    private static void addOwner() {
        logger.info ("\n--- Add New Owner ---");
        String firstName = getInput("First Name: ");
        String lastName = getInput("Last Name: ");
        String address = getInput("Address: ");
        String phone = getInput("Phone: ");

        Owner owner = new Owner(firstName, lastName, address, phone);
        ownerService.save(owner);
        logger.info ("Owner added successfully! ID: " + owner.getId());
    }

    private static void viewAllOwners() {
        List<Owner> owners = ownerService.findAll();
        if (owners.isEmpty()) {
            logger.info ("No owners found.");
        } else {
            for (Owner owner : owners) {
                logger.info(owner.toString());
            }
        }
    }

    private static void searchOwner() {
        String lastName = getInput("Enter last name to search: ");
        List<Owner> owners = ownerService.findByLastName(lastName);
        if (owners.isEmpty()) {
            logger.info ("No owners found.");
        } else {
            for (Owner owner : owners) {
               logger.info (owner.toString());
            }
        }
    }

    private static void updateOwner() {
        long id = getLongInput("Enter Owner ID: ");
        Owner owner = ownerService.findById(id);
        if (owner == null) {
           logger.info ("Owner not found!");
            return;
        }
        logger.info ("Current: " + owner);
        owner.setAddress(getInput("New Address (or press Enter to keep): ", owner.getAddress()));
        owner.setPhone(getInput("New Phone (or press Enter to keep): ", owner.getPhone()));
        ownerService.save(owner);
        logger.info ("Owner updated successfully!");
    }
    private static void deleteOwner() {
        long id = getLongInput("Enter Owner ID to delete: ");
        ownerService.delete(id);
        logger.info ("Owner deleted successfully!");
    }

    private static void managePets() {
        while (true) {
            logger.info ("\n--- Pet Management ---");
            logger.info ("1. Add Pet  2. View All  3. View by Owner  4. Update  5. Delete  6. Back");
            int choice = getIntInput("Choice: ");

            if (choice == 6) break;
            switch (choice) {
                default:Default(); break;
                case 1: addPet(); break;
                case 2: viewAllPets(); break;
                case 3: viewPetsByOwner(); break;
                case 4: updatePet(); break;
                case 5: deletePet(); break;
            }
        }
    }

    private static void addPet() {
        logger.info ("\n--- Add New Pet ---");
        long ownerId = getLongInput("Owner ID: ");
        Owner owner = ownerService.findById(ownerId);
        if (owner == null) {
            logger.info ("Owner not found!");
            return;
        }

        String name = getInput("Pet Name: ");
        String birthDate = getInput("Birth Date (YYYY-MM-DD): ");
        String type = getInput("Type (dog/cat/bird/etc): ");

        Pet pet = new Pet(name, birthDate, type, ownerId);
        petService.save(pet);
        logger.info ("Pet added successfully! ID: " + pet.getId());
    }
    private static void viewAllPets() {
        List<Pet> pets = petService.findAll();
        if (pets.isEmpty()) {
           logger.info ("No pets found.");
        } else {
            for (Pet pet : pets) {
                logger.info (pet.toString());
            }
        }
    }

    private static void viewPetsByOwner() {
        long ownerId = getLongInput("Enter Owner ID: ");
        List<Pet> pets = petService.findByOwnerId(ownerId);
        if (pets.isEmpty()) {
            logger.info ("No pets found for this owner.");
        } else {
            for (Pet pet : pets) {
               logger.info (pet.toString());
            }
        }
    }

    private static void updatePet() {
        long id = getLongInput("Enter Pet ID: ");
        Pet pet = petService.findById(id);
        if (pet == null) {
            logger.info ("Pet not found!");
            return;
        }
        logger.info("Current: " + pet);
        pet.setName(getInput("New Name (or press Enter to keep): ", pet.getName()));
        pet.setType(getInput("New Type (or press Enter to keep): ", pet.getType()));
        petService.save(pet);
        logger.info("Pet updated successfully!");
    }

    private static void deletePet() {
        long id = getLongInput("Enter Pet ID to delete: ");
        petService.delete(id);
        logger.info("Pet deleted successfully!");
    }
    private static void manageVisits() {
        while (true) {
            logger.info("\n--- Visit Management ---");
            logger.info("1. Add Visit  2. View All  3. View by Pet  4. Delete  5. Back");
            int choice = getIntInput("Choice: ");

            if (choice == 5) break;
            switch (choice) {
                default:Default(); break;
                case 1: addVisit(); break;
                case 2: viewAllVisits(); break;
                case 3: viewVisitsByPet(); break;
                case 4: deleteVisit(); break;
            }
        }
    }

    private static void addVisit() {
        logger.info("\n--- Add New Visit ---");
        long petId = getLongInput("Pet ID: ");
        Pet pet = petService.findById(petId);
        if (pet == null) {
            logger.info("Pet not found!");
            return;
        }

        String date = getInput("Visit Date (YYYY-MM-DD): ");
        String description = getInput("Description: ");

        Visit visit = new Visit(date, description, petId);
        visitService.save(visit);
        logger.info("Visit added successfully! ID: " + visit.getId());
    }

    private static void viewAllVisits() {
        List<Visit> visits = visitService.findAll();
        if (visits.isEmpty()) {
            logger.info("No visits found.");
        } else {
            for (Visit visit : visits) {
                logger.info(visit.toString());
            }
        }
    }


    private static void viewVisitsByPet() {
        long petId = getLongInput("Enter Pet ID: ");
        List<Visit> visits = visitService.findByPetId(petId);
        if (visits.isEmpty()) {
            logger.info("No visits found for this pet.");
        } else {
            for (Visit visit : visits) {
                logger.info(visit.toString());
            }
        }
    }

    private static void deleteVisit() {
        long id = getLongInput("Enter Visit ID to delete: ");
        visitService.delete(id);
        logger.info("Visit deleted successfully!");
    }

    private static void viewReports() {
        logger.info("\n=== REPORTS ===");
        logger.info("Total Owners: " + ownerService.findAll().size());
        logger.info("Total Pets: " + petService.findAll().size());
        logger.info("Total Visits: " + visitService.findAll().size());
    }

    private static void saveAndExit() {
        storage.saveData(ownerService, petService, visitService);
        logger.info("Data saved. Goodbye!");
    }

    private static String getInput(String prompt) {
        logger.info(prompt);
        return scanner.nextLine().trim();
    }

    private static String getInput(String prompt, String defaultValue) {
        logger.info(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                logger.info(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                logger.info("Invalid number!");
            }
        }
    }

    private static long getLongInput(String prompt) {
        while (true) {
            try {
                logger.info(prompt);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                logger.info("Invalid number!");
            }
        }
    }


}
