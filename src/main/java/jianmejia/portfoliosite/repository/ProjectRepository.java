package jianmejia.portfoliosite.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jianmejia.portfoliosite.model.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class ProjectRepository {
    private static final String FILE_PATH = "/opt/portfolio/data/projects.json";
    private final ObjectMapper objectMapper;

    public ProjectRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // support LocalDate
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // keep yyyy-MM-dd
    }

    public List<Project> findAll() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<Project> findById(Long id) {
        return findAll().stream().filter(p -> p.getId().equals(id)).findFirst();
    }
    public List<Project> findAllOrdered() {
        List<Project> list = findAll();
        list.sort(
                Comparator
                        // highlighted true comes before false
                        .comparing(Project::isHighlighted, Comparator.reverseOrder())
                        // within highlighted group, newest date first
                        .thenComparing(Project::getStartDate, Comparator.nullsLast(Comparator.reverseOrder()))
                        // fallback: newest id first
                        .thenComparing(Project::getId, Comparator.reverseOrder())
        );
        return list;
    }


    public void save(Project p) {
        List<Project> list = findAll();
        if (p.getId() == null) {
            long nextId = list.stream().mapToLong(Project::getId).max().orElse(0) + 1;
            p.setId(nextId);
        } else {
            list.removeIf(existing -> existing.getId().equals(p.getId()));
        }
        list.add(p);
        writeList(list);
    }

    public void deleteById(Long id) {
        List<Project> list = findAll();
        list.removeIf(p -> p.getId().equals(id));
        writeList(list);
    }

    private void writeList(List<Project> list) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
