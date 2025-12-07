package org.example;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.List;

public class PetManagementTest {

    // ===== OWNER SERVICE TESTS =====

    @Test
    public void testOwnerServiceSave() {
        OwnerService service = new OwnerService();
        Owner owner = new Owner("John", "Doe", "123 Main St", "555-1234");

        Owner saved = service.save(owner);

        assertNotNull(saved.getId());
        assertEquals("John", saved.getFirstName());
        assertEquals("Doe", saved.getLastName());
    }

    @Test
    public void testOwnerServiceFindById() {
        OwnerService service = new OwnerService();
        Owner owner = new Owner("Jane", "Smith", "456 Oak Ave", "555-5678");
        service.save(owner);

        Owner found = service.findById(owner.getId());

        assertNotNull(found);
        assertEquals("Jane", found.getFirstName());
        assertEquals("Smith", found.getLastName());
    }

    @Test
    public void testOwnerServiceFindByIdNotFound() {
        OwnerService service = new OwnerService();

        Owner found = service.findById(999L);

        assertNull(found);
    }

    @Test
    public void testOwnerServiceFindAll() {
        OwnerService service = new OwnerService();
        service.save(new Owner("John", "Doe", "123 Main St", "555-1234"));
        service.save(new Owner("Jane", "Smith", "456 Oak Ave", "555-5678"));

        List<Owner> owners = service.findAll();

        assertEquals(2, owners.size());
    }

    @Test
    public void testOwnerServiceFindByLastName() {
        OwnerService service = new OwnerService();
        service.save(new Owner("John", "Doe", "123 Main St", "555-1234"));
        service.save(new Owner("Jane", "Doe", "456 Oak Ave", "555-5678"));
        service.save(new Owner("Bob", "Smith", "789 Pine St", "555-9999"));

        List<Owner> owners = service.findByLastName("Doe");

        assertEquals(2, owners.size());
        assertTrue(owners.stream().allMatch(o -> o.getLastName().equals("Doe")));
    }

    @Test
    public void testOwnerServiceFindByLastNameCaseInsensitive() {
        OwnerService service = new OwnerService();
        service.save(new Owner("John", "Doe", "123 Main St", "555-1234"));

        List<Owner> owners = service.findByLastName("doe");

        assertEquals(1, owners.size());
    }

    @Test
    public void testOwnerServiceDelete() {
        OwnerService service = new OwnerService();
        Owner owner = new Owner("John", "Doe", "123 Main St", "555-1234");
        service.save(owner);

        service.delete(owner.getId());

        assertNull(service.findById(owner.getId()));
        assertEquals(0, service.findAll().size());
    }

    @Test
    public void testOwnerServiceAutoIncrementId() {
        OwnerService service = new OwnerService();
        Owner owner1 = service.save(new Owner("John", "Doe", "123 Main St", "555-1234"));
        Owner owner2 = service.save(new Owner("Jane", "Smith", "456 Oak Ave", "555-5678"));

        assertNotEquals(owner1.getId(), owner2.getId());
        assertTrue(owner2.getId() > owner1.getId());
    }

    // ===== PET SERVICE TESTS =====

    @Test
    public void testPetServiceSave() {
        PetService service = new PetService();
        Pet pet = new Pet("Buddy", "2020-05-15", "Dog", 1L);

        Pet saved = service.save(pet);

        assertNotNull(saved.getId());
        assertEquals("Buddy", saved.getName());
        assertEquals("Dog", saved.getType());
        assertEquals(Long.valueOf(1L), saved.getOwnerId());
    }

    @Test
    public void testPetServiceFindById() {
        PetService service = new PetService();
        Pet pet = new Pet("Whiskers", "2019-03-20", "Cat", 2L);
        service.save(pet);

        Pet found = service.findById(pet.getId());

        assertNotNull(found);
        assertEquals("Whiskers", found.getName());
    }

    @Test
    public void testPetServiceFindAll() {
        PetService service = new PetService();
        service.save(new Pet("Buddy", "2020-05-15", "Dog", 1L));
        service.save(new Pet("Whiskers", "2019-03-20", "Cat", 2L));
        service.save(new Pet("Tweety", "2021-01-10", "Bird", 1L));

        List<Pet> pets = service.findAll();

        assertEquals(3, pets.size());
    }

    @Test
    public void testPetServiceFindByOwnerId() {
        PetService service = new PetService();
        service.save(new Pet("Buddy", "2020-05-15", "Dog", 1L));
        service.save(new Pet("Max", "2018-08-22", "Dog", 1L));
        service.save(new Pet("Whiskers", "2019-03-20", "Cat", 2L));

        List<Pet> pets = service.findByOwnerId(1L);

        assertEquals(2, pets.size());
        assertTrue(pets.stream().allMatch(p -> p.getOwnerId().equals(1L)));
    }

    @Test
    public void testPetServiceDelete() {
        PetService service = new PetService();
        Pet pet = new Pet("Buddy", "2020-05-15", "Dog", 1L);
        service.save(pet);

        service.delete(pet.getId());

        assertNull(service.findById(pet.getId()));
    }

    // ===== VISIT SERVICE TESTS =====

    @Test
    public void testVisitServiceSave() {
        VisitService service = new VisitService();
        Visit visit = new Visit("2024-12-01", "Annual checkup", 1L);

        Visit saved = service.save(visit);

        assertNotNull(saved.getId());
        assertEquals("2024-12-01", saved.getDate());
        assertEquals("Annual checkup", saved.getDescription());
    }

    @Test
    public void testVisitServiceFindById() {
        VisitService service = new VisitService();
        Visit visit = new Visit("2024-12-01", "Vaccination", 1L);
        service.save(visit);

        Visit found = service.findById(visit.getId());

        assertNotNull(found);
        assertEquals("Vaccination", found.getDescription());
    }

    @Test
    public void testVisitServiceFindAll() {
        VisitService service = new VisitService();
        service.save(new Visit("2024-12-01", "Checkup", 1L));
        service.save(new Visit("2024-12-02", "Vaccination", 2L));

        List<Visit> visits = service.findAll();

        assertEquals(2, visits.size());
    }

    @Test
    public void testVisitServiceFindByPetId() {
        VisitService service = new VisitService();
        service.save(new Visit("2024-12-01", "Checkup", 1L));
        service.save(new Visit("2024-12-05", "Follow-up", 1L));
        service.save(new Visit("2024-12-02", "Vaccination", 2L));

        List<Visit> visits = service.findByPetId(1L);

        assertEquals(2, visits.size());
        assertTrue(visits.stream().allMatch(v -> v.getPetId().equals(1L)));
    }

    @Test
    public void testVisitServiceDelete() {
        VisitService service = new VisitService();
        Visit visit = new Visit("2024-12-01", "Checkup", 1L);
        service.save(visit);

        service.delete(visit.getId());

        assertNull(service.findById(visit.getId()));
    }

    // ===== MODEL TESTS =====

    @Test
    public void testOwnerToFileString() {
        Owner owner = new Owner("John", "Doe", "123 Main St", "555-1234");
        owner.setId(1L);

        String fileString = owner.toFileString();

        assertEquals("1|John|Doe|123 Main St|555-1234", fileString);
    }

    @Test
    public void testOwnerFromFileString() {
        String fileString = "1|John|Doe|123 Main St|555-1234";

        Owner owner = Owner.fromFileString(fileString);

        assertEquals(Long.valueOf(1L), owner.getId());
        assertEquals("John", owner.getFirstName());
        assertEquals("Doe", owner.getLastName());
        assertEquals("123 Main St", owner.getAddress());
        assertEquals("555-1234", owner.getPhone());
    }

    @Test
    public void testPetToFileString() {
        Pet pet = new Pet("Buddy", "2020-05-15", "Dog", 1L);
        pet.setId(1L);

        String fileString = pet.toFileString();

        assertEquals("1|Buddy|2020-05-15|Dog|1", fileString);
    }

    @Test
    public void testPetFromFileString() {
        String fileString = "1|Buddy|2020-05-15|Dog|1";

        Pet pet = Pet.fromFileString(fileString);

        assertEquals(Long.valueOf(1L), pet.getId());
        assertEquals("Buddy", pet.getName());
        assertEquals("2020-05-15", pet.getBirthDate());
        assertEquals("Dog", pet.getType());
        assertEquals(Long.valueOf(1L), pet.getOwnerId());
    }

    @Test
    public void testVisitToFileString() {
        Visit visit = new Visit("2024-12-01", "Annual checkup", 1L);
        visit.setId(1L);

        String fileString = visit.toFileString();

        assertEquals("1|2024-12-01|Annual checkup|1", fileString);
    }

    @Test
    public void testVisitFromFileString() {
        String fileString = "1|2024-12-01|Annual checkup|1";

        Visit visit = Visit.fromFileString(fileString);

        assertEquals(Long.valueOf(1L), visit.getId());
        assertEquals("2024-12-01", visit.getDate());
        assertEquals("Annual checkup", visit.getDescription());
        assertEquals(Long.valueOf(1L), visit.getPetId());
    }

    // ===== DATA STORAGE TESTS =====

    @Test
    public void testDataStorageSaveAndLoad() throws IOException {
        // Setup services with test data
        OwnerService ownerService = new OwnerService();
        PetService petService = new PetService();
        VisitService visitService = new VisitService();

        Owner owner = ownerService.save(new Owner("John", "Doe", "123 Main St", "555-1234"));
        Pet pet = petService.save(new Pet("Buddy", "2020-05-15", "Dog", owner.getId()));
        visitService.save(new Visit("2024-12-01", "Checkup", pet.getId()));

        // Save data
        DataStorage storage = new DataStorage();
        storage.saveData(ownerService, petService, visitService);

        // Create new services and load data
        OwnerService newOwnerService = new OwnerService();
        PetService newPetService = new PetService();
        VisitService newVisitService = new VisitService();

        storage.loadData(newOwnerService, newPetService, newVisitService);

        // Verify data was loaded correctly
        assertEquals(1, newOwnerService.findAll().size());
        assertEquals(1, newPetService.findAll().size());
        assertEquals(1, newVisitService.findAll().size());

        Owner loadedOwner = newOwnerService.findById(owner.getId());
        assertNotNull(loadedOwner);
        assertEquals("John", loadedOwner.getFirstName());

        Pet loadedPet = newPetService.findById(pet.getId());
        assertNotNull(loadedPet);
        assertEquals("Buddy", loadedPet.getName());

        // Cleanup test files
        new File("owners.txt").delete();
        new File("pets.txt").delete();
        new File("visits.txt").delete();
    }

    @Test
    public void testOwnerServiceClear() {
        OwnerService service = new OwnerService();
        service.save(new Owner("John", "Doe", "123 Main St", "555-1234"));
        service.save(new Owner("Jane", "Smith", "456 Oak Ave", "555-5678"));

        service.clear();

        assertEquals(0, service.findAll().size());
    }

    @Test
    public void testPetServiceClear() {
        PetService service = new PetService();
        service.save(new Pet("Buddy", "2020-05-15", "Dog", 1L));
        service.save(new Pet("Whiskers", "2019-03-20", "Cat", 2L));

        service.clear();

        assertEquals(0, service.findAll().size());
    }

    @Test
    public void testVisitServiceClear() {
        VisitService service = new VisitService();
        service.save(new Visit("2024-12-01", "Checkup", 1L));
        service.save(new Visit("2024-12-02", "Vaccination", 2L));

        service.clear();

        assertEquals(0, service.findAll().size());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    public void testCompleteWorkflow() {
        // Create services
        OwnerService ownerService = new OwnerService();
        PetService petService = new PetService();
        VisitService visitService = new VisitService();

        // Create owner
        Owner owner = ownerService.save(new Owner("John", "Doe", "123 Main St", "555-1234"));
        assertNotNull(owner.getId());

        // Create pet for owner
        Pet pet = petService.save(new Pet("Buddy", "2020-05-15", "Dog", owner.getId()));
        assertNotNull(pet.getId());

        // Create visit for pet
        Visit visit = visitService.save(new Visit("2024-12-01", "Annual checkup", pet.getId()));
        assertNotNull(visit.getId());

        // Verify relationships
        List<Pet> ownerPets = petService.findByOwnerId(owner.getId());
        assertEquals(1, ownerPets.size());
        assertEquals("Buddy", ownerPets.get(0).getName());

        List<Visit> petVisits = visitService.findByPetId(pet.getId());
        assertEquals(1, petVisits.size());
        assertEquals("Annual checkup", petVisits.get(0).getDescription());
    }

    @Test
    public void testMultiplePetsPerOwner() {
        OwnerService ownerService = new OwnerService();
        PetService petService = new PetService();

        Owner owner = ownerService.save(new Owner("John", "Doe", "123 Main St", "555-1234"));
        petService.save(new Pet("Buddy", "2020-05-15", "Dog", owner.getId()));
        petService.save(new Pet("Max", "2018-08-22", "Dog", owner.getId()));
        petService.save(new Pet("Whiskers", "2019-03-20", "Cat", owner.getId()));

        List<Pet> pets = petService.findByOwnerId(owner.getId());

        assertEquals(3, pets.size());
    }
}
