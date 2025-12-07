package org.example;

import java.util.Map;
import java.util.*;

class OwnerService {
    private Map<Long, Owner> data = new HashMap<>();
    private Long nextId = 1L;

    public Owner save(Owner owner) {
        if (owner.getId() == null) owner.setId(nextId++);
        else if (owner.getId() >= nextId) nextId = owner.getId() + 1;
        data.put(owner.getId(), owner);
        return owner;
    }

    public Owner findById(Long id) { return data.get(id); }
    public List<Owner> findAll() { return new ArrayList<>(data.values()); }

    public List<Owner> findByLastName(String lastName) {
        List<Owner> result = new ArrayList<>();
        for (Owner owner : data.values()) {
            if (owner.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
                result.add(owner);
            }
        }
        return result;
    }

    public void delete(Long id) { data.remove(id); }
    public void clear() { data.clear(); nextId = 1L; }
}