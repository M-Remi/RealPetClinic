package org.example;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
class VisitService {
    private Map<Long, Visit> data = new HashMap<>();
    private Long nextId = 1L;

    public Visit save(Visit visit) {
        if (visit.getId() == null) visit.setId(nextId++);
        else if (visit.getId() >= nextId) nextId = visit.getId() + 1;
        data.put(visit.getId(), visit);
        return visit;
    }

    public Visit findById(Long id) {
        return data.get(id);
    }

    public List<Visit> findAll() {
        return new ArrayList<>(data.values());
    }

    public List<Visit> findByPetId(Long petId) {
        List<Visit> result = new ArrayList<>();
        for (Visit visit : data.values()) {
            if (visit.getPetId().equals(petId)) result.add(visit);
        }
        return result;
    }

    public void delete(Long id) {
        data.remove(id);
    }

    public void clear() {
        data.clear();
        nextId = 1L;
    }
}
