package org.example;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
class DataStorage {
    Logger logger = Logger.getLogger(getClass().getName());
String message="";

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

// High Complexity
public void complexIfElse(int a, int b, int c) {
    if (a > 0) {
        logger.info("A positive");
        if (b > 0) {
            logger.info("B positive");
            if (c > 0) {
                logger.info("C positive");
            } else if (c == 0) {
                logger.info("C zero");
            } else {
                logger.info("C negative");
            }
        } else if (b == 0) {
            logger.info("B zero");
        } else {
            logger.info("B negative");
        }
    } else {
        if (a == 0) {
            logger.info("A zero");
        } else {
            logger.info("A negative");
        }
    }
}
public void complexLoops(int[] arr, int c, int d, boolean flag) {
                      // +1

    if (c < 10) logger.info("C < 10");                   // +1
    if (d == 0) logger.info("D == 0");                   // +1
    if (flag) logger.info("Flag true");                  // +1

    if (a > b) {                                         // +1
        logger.info("A > B");
    } else {
        logger.info("A <= B");
    }

    if (b > c) {                                         // +1
        logger.info("B > C");
    } else {
        logger.info("B <= C");
    }

    if (c > d) {                                         // +1
        logger.info("C > D");
    } else {
        logger.info("C <= D");
    }

    if ((a % 2 == 0) && (b % 2 == 0)) {                  // +1 (&& adds 1)
        logger.info("A and B even");
    }

       switch (a) {                                         // +3 (3 cases = 3 paths)
        case 1:
            logger.info("A = 1");
            break;
        case 2:
            logger.info("A = 2");
            break;
        case 3:
            logger.info("A = 3");
            break;
        default:
            logger.info("A other");
    }

    // Two looping conditions (each adds 1 to CC)
    for (int i = 0; i < 1; i++) {                        // +1
        logger.info("Loop 1");
    }

    while (flag) {                                      // +1
        logger.info("Unreachable loop but increases complexity");
    }
        for (int i = 0; i < arr.length; i++) {
            message="Outer loop i=" + i;
            logger.info(message);
            if (arr[i] % 2 == 0) {
                logger.info("Even number");
            } else {
                logger.info("Odd number");
            }

            for (int j = 0; j < arr.length; j++) {
                message="Inner loop j=" + j;
                logger.info(message);
                if (arr[j] > arr[i]) {
                    logger.info("arr[j] greater");
                } else if (arr[j] == arr[i]) {
                    logger.info("arr[j] equal");
                } else {
                    logger.info("arr[j] less");
                }
            }
        }
    }
public void complexSwitch(String command) {
        switch (command) {
            case "start":
                logger.info("Starting...");
                break;
            case "stop":
                logger.info("Stopping...");
                break;
            case "pause":
                logger.info("Pausing...");
                break;
            case "resume":
                logger.info("Resuming...");
                break;
            case "restart":
                logger.info("Restarting...");
                break;
            case "status":
                logger.info("Status...");
                break;
            default:
                logger.warning("Unknown command!");
        }
    }


}

