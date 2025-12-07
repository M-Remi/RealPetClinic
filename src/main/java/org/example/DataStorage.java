package org.example;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
class DataStorage {
    Logger logger = Logger.getLogger(getClass().getName());

    private static final String OWNERS_FILE = "owners.txt";
    private static final String PETS_FILE = "pets.txt";
    private static final String VISITS_FILE = "visits.txt";

    public void saveData(OwnerService ownerService, PetService petService, VisitService visitService) {
        try {
            saveOwners(ownerService);
            savePets(petService);
            saveVisits(visitService);
            logger.info("Data saved to files.");
        } catch (IOException e) {
            String messa="Error saving data: " + e.getMessage();
            logger.log(Level.SEVERE, messa);
        }
    }

    public void loadData(OwnerService ownerService, PetService petService, VisitService visitService) {
        try {
            loadOwners(ownerService);
            loadPets(petService);
            loadVisits(visitService);
            logger.info("Data loaded from files.");
        } catch (IOException e) {
            logger.log( Level.SEVERE, "No existing data found. Starting fresh.");
        }
    }

    private void saveOwners(OwnerService service) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OWNERS_FILE))) {
            for (Owner owner : service.findAll()) {
                writer.write(owner.toFileString());
                writer.newLine();
            }

        }
    }

    private void savePets(PetService service) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PETS_FILE))) {
            for (Pet pet : service.findAll()) {
                writer.write(pet.toFileString());
                writer.newLine();
            }

        }
    }

    private void saveVisits(VisitService service) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(VISITS_FILE))) {
            for (Visit visit : service.findAll()) {
                writer.write(visit.toFileString());
                writer.newLine();
            }

        }
    }

    private void loadOwners(OwnerService service) throws IOException {
        service.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(OWNERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    service.save(Owner.fromFileString(line));
                }
            }

        }
    }

    private void loadPets(PetService service) throws IOException {
        service.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(PETS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    service.save(Pet.fromFileString(line));
                }
            }

        }
    }

    private void loadVisits(VisitService service) throws IOException {
        service.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(VISITS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    service.save(Visit.fromFileString(line));
                }
            }

        }
    }
}

