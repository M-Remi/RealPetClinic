package org.example;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


class PetService {
    private Map<Long, Pet> data = new HashMap<>();
    private Long nextId = 1L;

    public Pet save(Pet pet) {
        if (pet.getId() == null) pet.setId(nextId++);
        else if (pet.getId() >= nextId) nextId = pet.getId() + 1;
        data.put(pet.getId(), pet);
        return pet;
    }

    public Pet findById(Long id) { return data.get(id); }
    public List<Pet> findAll() { return new ArrayList<>(data.values()); }

    public List<Pet> findByOwnerId(Long ownerId) {
        List<Pet> result = new ArrayList<>();
        for (Pet pet : data.values()) {
            if (pet.getOwnerId().equals(ownerId)) result.add(pet);
        }
        return result;
    }

    public void delete(Long id) { data.remove(id); }
    public void clear() { data.clear(); nextId = 1L; }
}
