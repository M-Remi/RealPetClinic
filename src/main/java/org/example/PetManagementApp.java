package org.example;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.Random;


import java.util.logging.Logger;
public class PetManagementApp {
    static Logger logger = Logger.getLogger(PetManagementApp.class.getName());

    int[] arr;
    Random rand = new Random();
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
        final String a="Choice:";

        while (true) {
            logger.info("\n--- Owner Management ---");
            logger.info("1. Add Owner  2. View All  3. Search  4. Update  5. Delete  6. Back");
            int choice = getIntInput(a  );

            if (choice == 6) break;
            switch (choice) {

                case 1: addOwner(); break;
                case 2: viewAllOwners(); break;
                case 3: searchOwner(); break;
                case 4: updateOwner(); break;
                case 5: deleteOwner(); break;
                default: defaultAction(); break;
            }
        }
    }

    private static void defaultAction() {
        // left empty on purpose

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
                String s=owner.toString();
                logger.info(s);
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
                String s=owner.toString();
               logger.info (s);
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
        String s="Current: " + owner;
        logger.info (s);
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

                case 1: addPet(); break;
                case 2: viewAllPets(); break;
                case 3: viewPetsByOwner(); break;
                case 4: updatePet(); break;
                case 5: deletePet(); break;
                default:defaultAction(); break;
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
                String s=pet.toString();
                logger.info (s);
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
                String s=pet.toString();
               logger.info (s);
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
        String s="Current: " + pet;
        logger.info(s);
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

                case 1: addVisit(); break;
                case 2: viewAllVisits(); break;
                case 3: viewVisitsByPet(); break;
                case 4: deleteVisit(); break;
                default:defaultAction(); break;
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
                String s=visit.toString();
                logger.info(s);
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
                String s=visit.toString();
                logger.info(s);
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
        int a= ownerService.findAll().size();
        int b= petService.findAll().size();
        int c=visitService.findAll().size();

        var aa="Total Owners: " +a;
        var bb="Total Pets: " + b;
        var cc="Total Pets: " + c;


        logger.info(aa);
        logger.info(bb);
        logger.info(cc);
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

    //long methods
    public void handleOrder(String orderId, int quantity) {
        String m;
        // Start of a very unnecessarily long method
        logger.info ("Starting order handling...");

        // Step 1: Validate order ID
        if (orderId == null || orderId.isEmpty()) {
            logger.info ("Invalid order ID.");
            return;
        }

        // Step 2: Validate quantity
        if (quantity <= 0) {
            logger.info ("Invalid quantity.");
            return;
        }

        // Step 3: Print status
        m="Order ID: " + orderId;
        logger.info (m);
        m="Quantity: " + quantity;
        logger.info (m);

        // Step 4: Fake database lookup
        String productName = "SampleProduct";
        m="Product found: " + productName;
        logger.info (m);

        // Step 5: Fake inventory check
        int inventory = 999;
        m="Available inventory: " + inventory;
        logger.info (m);

        // Step 6: Check inventory
        if (quantity > inventory) {
            logger.info ("Not enough inventory!");
            return;
        }

        // Step 7: Unnecessary computations
        int tempValue = quantity * 2;
        tempValue += 3;
        tempValue -= 1;
        tempValue *= 4;
        tempValue /= 2;
        m="Calculated temp value: " + tempValue;
        logger.info (m);

        // Step 8: More useless checks
        if (tempValue > 1000) {
            logger.info ("Temp value is too large (but this means nothing).");
        }

        // Step 9: Useless string operations
        String builder = "Processing";
        builder = builder.toLowerCase();
        builder = builder.toUpperCase();
        builder = builder.concat(" ORDER");
        m="Builder result: " + builder;
        logger.info (m);

        // Step 10: Loop for no reason
        for (int i = 0; i < 5; i++) {
            m="Loop step " + i;
            logger.info (m);
        }

        // Step 11: Another meaningless loop
        int counter = 0;
        while (counter < 3) {
            m="Counter at: " + counter;
            logger.info (m);
            counter++;
        }

        // Step 12: Random useless conditional blocks
        if (quantity % 2 == 0) {
            logger.info ("Quantity is even.");
        } else {
            logger.info ("Quantity is odd.");
        }

        if (productName.startsWith("S")) {
            logger.info ("Product starts with S.");
        }

        // Step 13: More arbitrary code
        int sum = 0;
        for (int i = 1; i <= quantity; i++) {
            sum += i;
        }
        m="Sum of numbers up to quantity: " + sum;
        logger.info (m);

        // Step 14: Pretend to apply discount
        double pricePerUnit = 10.0;
        double discount = quantity > 10 ? 0.1 : 0.0;
        double finalPrice = (pricePerUnit * quantity) * (1 - discount);
        m="Final price: " + finalPrice;
        logger.info (m);

        // Step 15: Final useless prints
        logger.info ("Almost done handling order...");
        logger.info ("Finalizing...");

        // End of ridiculous method
        logger.info ("Order handled successfully!");

        double pricePerUnit1 = 10.0;
        double discount1 = quantity > 10 ? 0.1 : 0.0;
        double finalPrice1 = (pricePerUnit * quantity) * (1 - discount);
        m="Final price: " + finalPrice1 + " " + finalPrice;

        double z=pricePerUnit1 +discount1 +finalPrice1;

        logger.info (m);
        String[] tasks = {"Write Report", "Send Email", "Backup Files"};
        for (String task : tasks) {
            m="Starting task: " + task + " " + z;
            logger.info(m);
            try {
                // Simulate task processing
                Thread.sleep(300);
                if (task.equals("Send Email")) {
                    throw new IllegalArgumentException("Email server not responding");
                }
                m="Task completed successfully: " + task;
                logger.info(m);
            } catch (InterruptedException e) {
                m="Task interrupted: " + task;
                logger.warning(m);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                m="Error processing task " + task + ": " + e.getMessage();
                logger.severe(m);
            }
        }
        logger.info("All tasks processed");
    }
    public double solveComplexFunction(double x) {

        logger.info ("Starting computation...");

        // Line 1: Basic validation
        if (Double.isNaN(x)) {
            logger.info("Input is NaN.");
            return 0;
        }

        // Line 2–8: More unnecessary checks
        if (x == Double.POSITIVE_INFINITY) {
            logger.info("X is +infinity. Returning large placeholder.");
            return 999999;
        }
        if (x == Double.NEGATIVE_INFINITY) {
            logger.info("X is -infinity. Returning negative placeholder.");
            return -999999;
        }
        if (x == 0) {
            logger.info("X is zero. Continuing anyway...");
        }

        // Line 9–20: Useless intermediate variables
        double a = x * x;
        double b = a * x;
        double c = b * x;
        double d = a + b - c;
        double e = d * 2;
        double f = e + 5;
        double g = f - 3;
        double h = g / 2;
        double i = h * 4;
        double j = i - 7;
        double k = j + x; // none of this will matter

        String message="Intermediate junk: " + k;
        logger.info (message);

        // Line 21–30: Compute polynomial pieces one-by-one
        double term1 = 3 * (x * x * x * x);
        double term2 = -5 * (x * x * x);
        double term3 = 2 * (x * x);

        // Print useless debug noise
        message="Term1: " + term1;
        logger.info(message);

        message="Term2: " + term2;
        logger.info(message);

        message="Term3: " + term3;
        logger.info(message);

        // Line 31–40: Trigonometric parts
        double sinPart = Math.sin(x);
        double cosPart = Math.cos(x);

        message="Sin part: " + sinPart;
        logger.info(message);

        message="Cos part: " + cosPart;
        logger.info(message);

        // Line 41–50: More junk loops doing nothing important
        double accum = 0;
        for (int r = 0; r < 5; r++) {
            accum += r * 0.01;
            message="Accumulating nonsense: " + accum;
            logger.info(message);
        }

        // Line 51–60: Absolute and sqrt computations
        double absX = Math.abs(x);
        double sqrtPart = Math.sqrt(absX);

        message="Absolute x: " + absX;
        logger.info (message);

        message="Sqrt part: " + sqrtPart;
        logger.info(message);

        // Line 61–70: More redundant transformations
        double tmp1 = sqrtPart + cosPart;
        double tmp2 = tmp1 - sinPart;
        double tmp3 = tmp2 * 1.0; // multiply by 1 for no reason
        double tmp4 = tmp3 / 1.0; // divide by 1 for no reason
        double tmp5 = tmp4 + 0;   // add 0 for no reason

        message="Temporary chain result: " + tmp5;
        logger.info(message);

        // Line 71–80: Re-recalculate some things pointlessly
        double recalc1 = Math.pow(x, 4);
        double recalc2 = Math.pow(x, 3);
        double recalc3 = Math.pow(x, 2);

        message="Recalculated powers: " + recalc1 + ", " + recalc2 + ", " + recalc3;
        logger.info(message);

        // Line 81–90: Combine into final expression
        double finalResult =
                (3 * recalc1) +
                        (-5 * recalc2) +
                        (2 * recalc3) +
                        sinPart +
                        cosPart +
                        sqrtPart +
                        tmp5 * 0; // tmp5 is ignored on purpose

        logger.info("Combining into final result...");

        // Line 91–98: Final pointless loop
        for (int n = 0; n < 3; n++) {
            message="Finishing step " + (n + 1);
            logger.info(message);
            finalResult += 0; // add nothing
        }

        // Line 99–100: Finish
        message="Final result: " + finalResult;
        logger.info(message);
        return finalResult;
    }
    public double calculateLoan(double principal, double annualRate, int years) {

        logger.info("Starting loan calculation...");

        // Line 1–5: Basic validation
        if (principal <= 0) {
            logger.warning("Principal must be > 0");
            return -1;
        }
        if (annualRate < 0) {
            logger.warning("Interest rate must be >= 0");
            return -1;
        }
        if (years <= 0) {
            logger.warning("Years must be > 0");
            return -1;
        }

        // Line 6–10: Convert and print basic inputs
        double monthlyRate = annualRate / 12.0 / 100.0;
        int totalMonths = years * 12;

        String message="Principal: " + principal;
        logger.info(message);

        message="Annual rate: " + annualRate;
        logger.info(message);

        message="Monthly rate: " + monthlyRate;
        logger.info(message);

        // Line 11–20: Useless intermediate steps
        double temp1 = principal * 1.01;
        double temp2 = temp1 - principal;
        double temp3 = temp2 + 0.5;
        double temp4 = temp3 * 2;
        double temp5 = temp4 / 2;

        message="Random temp chain result: " + temp5;
        logger.info(message);

        // Line 21–30: Another useless chain
        double dummyA = principal / 10;
        double dummyB = dummyA * dummyA;
        double dummyC = dummyB - dummyA;
        double dummyD = dummyC + 3;
        double dummyE = dummyD / 3;

        message="Dummy calculation result: " + dummyE;
        logger.info(message);

        // Line 31–40: Loop doing nothing
        for (int i = 0; i < 5; i++) {
            message="Loop step " + i;
            logger.fine(message);
        }

        // Line 41–50: More junk
        double noiseValue = 0;
        for (int n = 1; n <= 5; n++) {
            noiseValue += n * 0.123;
            message="Noise accumulation: " + noiseValue;
            logger.fine(message);
        }

        // Line 51–60: Log equation pieces
        double step1 = Math.pow(1 + monthlyRate, totalMonths);
        double numerator = principal * monthlyRate * step1;
        double denominator = step1 - 1;

        message="Step1 power: " + step1;
        logger.info(message);

        message="Numerator: " + numerator;
        logger.info(message);

        message="Denominator: " + denominator;
        logger.info(message);

        // Line 61–70: More unnecessary logs
        logger.fine("Entering additional useless logic...");
        double checkVal = step1 / 2;
        message="Check value: " + checkVal;
        logger.fine(message);
        if (checkVal > 10) {
            logger.fine("Check value is greater than 10");
        } else {
            logger.fine("Check value is 10 or less");
        }

        // Line 71–80: Another dummy loop
        int counter = 0;
        while (counter < 3) {

            message="Counter at: " + counter;
            logger.fine(message);
            counter++;
        }

        // Line 81–85: Final sanity checks
        if (denominator == 0) {
            logger.warning("Invalid denominator - cannot compute payment");
            return -1;
        }

        // Line 86–90: Final calculation
        double monthlyPayment = numerator / denominator;
        message="Monthly Payment Calculated: " + monthlyPayment;
        logger.info(message);

        // Line 91–100: Fake finishing steps
        logger.fine("Performing final cleanup step 1...");
        logger.fine("Performing final cleanup step 2...");
        logger.fine("Performing final cleanup step 3...");
        logger.info("Loan calculation complete.");

        return monthlyPayment;
    }
    public void sortArray() {

        // Line 1–5: Input validation
        String message="";


        // Line 6–10: Print initial state
        logger.info("Starting sort...");
        message="Array length: " + arr.length;
        logger.info(message);
        logger.info("Initial values:");
        int v=14;

        message=String.valueOf(v);
        logger.info(message);

        // Line 11–20: Useless temporary calculations
        int tempA = arr.length * 2;
        int tempB = tempA - 3;
        int tempC = tempB + 7;
        int tempD = tempC / 2;
        int tempE = tempD + arr[0];

        message="Temp chain result: " + tempE;
        logger.info(message);

        // Line 21–30: More junk
        int dummy = 0;
        for (int i = 0; i < 5; i++) {
            dummy += i;

            message="Dummy loop accum: " + dummy;
            logger.fine(message);
        }

        // Line 31–40: Start bubble sort
        logger.info("Beginning bubble sort...");
        boolean swapped = true;
        int iteration = 0;

        // Line 41–60: Sorting pass with logs and noise
        while (swapped) {
            swapped = false;
            iteration++;

            message="Iteration " + iteration;
            logger.info(message);

            for (int i = 0; i < arr.length - 1; i++) {
                message="Comparing index " + i + " and " + (i + 1);
                logger.fine(message);
                if (arr[i] > arr[i + 1]) {
                    message="Swapping: " + arr[i] + " and " + arr[i + 1];
                    logger.info(message);
                    int tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    swapped = true;
                } else {
                    logger.fine("No swap needed.");
                }
            }

            // Line 61–70: Print state after iteration
            message="Array after iteration " + iteration + ":";
            logger.info(message);

            for (int vi : arr) {
                message=String.valueOf(vi);
                logger.info(message);
            }

            int checkValue = iteration * 3 + 1;
            message="CheckValue: " + checkValue;
            logger.fine(message);
        }

        // Line 71–80: More unnecessary steps
        int sum = 0;
        for (int av : arr) {
            sum += av;
            message="Running sum: " + sum;
            logger.fine(message);
        }

        // Line 81–90: Verification
        logger.info("Verifying order...");
        boolean correct = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                correct = false;
                break;
            }
        }
        if (correct) {
            logger.info("Array verified as sorted.");
        } else {
            logger.warning("Array verification failed (should never happen in bubble sort).");
        }

        // Line 91–100: Final output
        logger.info("Final sorted array:");
        for (int vv : arr)
        {
            message=    String.valueOf(vv);
            logger.info(message);
        }

        logger.info("Sorting complete.");
    }
    public void demoStructures() {

        String message;
        // Line 1–10: Start + ArrayList initialization
        logger.info("Starting data structure demo...");
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        list.add("Date");
        list.add("Elderberry");
        logger.info("ArrayList initialized with fruits.");

        // Line 11–20: Iterate ArrayList
        for (String item : list) {
            message="List item: " + item;
            logger.info(message);
        }
        logger.info("Finished printing ArrayList.");

        // Line 21–30: HashMap initialization
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        map.put(4, "Four");
        logger.info("HashMap created.");

        // Line 31–40: Iterate HashMap
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            message="Key: " + entry.getKey() + ", Value: " + entry.getValue();
            logger.info(message);
        }
        logger.info("Finished printing HashMap.");

        // Line 41–50: HashSet initialization
        Set<String> set = new HashSet<>();
        set.add("Alpha");
        set.add("Beta");
        set.add("Gamma");
        set.add("Delta");
        logger.info("HashSet created.");

        for (String s : set) {
            message="Set element: " + s;
            logger.info(message);
        }

        // Line 51–60: Queue initialization
        Queue<Integer> queue = new LinkedList<>();
        queue.add(10);
        queue.add(20);
        queue.add(30);
        queue.add(40);
        logger.info("Queue initialized.");

        while (!queue.isEmpty()) {
            message="Queue poll: " + queue.poll();
            logger.info(message);
        }

        // Line 61–70: Stack initialization
        Stack<Double> stack = new Stack<>();
        stack.push(1.1);
        stack.push(2.2);
        stack.push(3.3);
        logger.info("Stack initialized.");

        while (!stack.isEmpty()) {
            message="Stack pop: " + stack.pop();
            logger.info(message);
        }

        // Line 71–80: LinkedList operations
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("First");
        linkedList.add("Second");
        linkedList.add("Third");
        linkedList.addFirst("Zero");
        linkedList.addLast("Fourth");
        logger.info("LinkedList created.");

        for (String s : linkedList) {
            message="LinkedList value: " + s;
            logger.info(message);
        }

        // Line 81–90: TreeMap initialization
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Zebra", 5);
        treeMap.put("Monkey", 2);
        treeMap.put("Elephant", 4);
        treeMap.put("Ant", 1);
        logger.info("TreeMap created and sorted automatically.");

        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            message="TreeMap entry: " + entry.getKey() + " = " + entry.getValue();
            logger.info(message);
        }

        // Line 91–100: Final steps
        logger.info("All data structures initialized and used.");
        logger.info("Demo complete.");
    }
}
