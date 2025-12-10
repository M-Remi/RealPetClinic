package org.example;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
class DataStorage {
    Logger logger = Logger.getLogger(getClass().getName());
    String message = "";

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
            String messa = "Error saving data: " + e.getMessage();
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
            logger.log(Level.SEVERE, "No existing data found. Starting fresh.");
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
    public void complexLoops(int[] arr, int c, int d, boolean flag) {
        // +1

        if (c < 10) logger.info("C < 10");                   // +1
        if (d == 0) logger.info("D == 0");                   // +1
        if (flag) logger.info("Flag true");                  // +1

        if (c==d) {                                         // +1
            logger.info("A > B");
        } else {
            logger.info("A <= B");
        }
        if (c > d) {                                         // +1
            logger.info("A > B");
        } else {
            logger.info("A <= B");
        }

        if (d > c) {                                         // +1
            logger.info("B > C");
        } else {
            logger.info("B <= C");
        }

        if (c > d) {                                         // +1
            logger.info("C > D");
        } else {
            logger.info("C <= D");
        }

        if ((c % 2 == 0) && (d % 2 == 0)) {                  // +1 (&& adds 1)
            logger.info("A and B even");
        }

        switch (c) {                                         // +3 (3 cases = 3 paths)
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
            message = "Outer loop i=" + i;
            logger.info(message);
            if (arr[i] % 2 == 0) {
                logger.info("Even number");
            } else {
                logger.info("Odd number");
            }

            for (int j = 0; j < arr.length; j++) {
                message = "Inner loop j=" + j;
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
    public void complexMethodA(int x, int y, int z, boolean f1, boolean f2) {

        if (x > 0) logger.info("x > 0");                 // +1
        if (x > y) logger.info("x > 0");                 // +1
        if (y > 0) logger.info("y > 0");                 // +1
        if (z > 0) logger.info("z > 0");                 // +1
        if (f1) logger.info("f1 true");                  // +1
        if (f2) logger.info("f2 true");                  // +1

        if (x > y) {                                     // +1
            logger.info("x > y");
        } else {
            logger.info("x <= y");
        }

        if (y > z) {                                     // +1
            logger.info("y > z");
        } else {
            logger.info("y <= z");
        }

        if ((x % 2 == 0) && (y % 3 == 0)) {              // +1
            logger.info("x even AND y mod 3 == 0");
        }

        if ((z % 2 == 1) || (x < 5)) {                   // +1
            logger.info("z odd OR x < 5");
        }

        switch (x) {                                     // +3
            case 1:
                logger.info("x=1");
                break;
            case 2:
                logger.info("x=2");
                break;
            case 3:
                logger.info("x=3");
                break;
            case 4:
                logger.info("x=3");
                break;
            case 5:
                logger.info("x=3");
                break;
            default:
                logger.info("x other");
        }

        for (int i = 0; i < 1; i++) logger.info("loop"); // +1
        while (f2) {
            if (z>y)
            {
                logger.info("Tested");

            }
        }                                 // +1
    }

    public String complexMethodD(int p, int q, int r, boolean f) {

        if (p < 0) return "neg p";                       // +1
        if (q < 0) return "neg q";                       // +1
        if (r < 0) return "neg r";                       // +1
        if (f) logger.info("flag");                      // +1
        if (p == 10) logger.info("p=10");                // +1

        if (p > q) {                                     // +1
            logger.info("p>q");
        } else {
            logger.info("p<=q");
        }

        if (q > p) {                                     // +1
            logger.info("p>q");
        } else {
            logger.info("p<=q");
        }

        if (q > r) {                                     // +1
            logger.info("q>r");
        } else {
            logger.info("q<=r");
        }

        if (p > r) {                                     // +1
            logger.info("q>r");
        } else {
            logger.info("q<=r");
        }


        if ((p + q) > r && f) {                          // +1
            logger.info("sum>r");
        }

        if ((p * q) < r || !f) {                         // +1
            logger.info("product<r");
        }

        switch (p % 3) {                                 // +3
            case 0: logger.info("0"); break;
            case 1: logger.info("1"); break;
            case 2: logger.info("2"); break;

            default: logger.info("other");
        }

        for (int i = 0; i < 1; i++) {

            logger.info("Remi");
        }                   // +1
        while (f) {

            logger.info("Here");
        }                                 // +1

        return "done";
    }


}





