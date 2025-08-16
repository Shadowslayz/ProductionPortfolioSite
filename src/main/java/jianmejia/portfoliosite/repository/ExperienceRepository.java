package jianmejia.portfoliosite.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jianmejia.portfoliosite.model.Experience;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ExperienceRepository {
    private static final String FILE_PATH = "/opt/portfolio/data/experiences.json";
    private final ObjectMapper objectMapper;

    public ExperienceRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<Experience> findAll() {
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

    public List<Experience> findAllOrdered() {
        List<Experience> list = findAll();
        list.sort(Comparator
                .comparing(Experience::getStartDate).reversed()
                .thenComparing(Experience::getId).reversed());
        return list;
    }

    public void save(Experience e) {
        List<Experience> list = findAll();
        // Auto-generate ID if null
        if (e.getId() == null) {
            long nextId = list.stream()
                    .mapToLong(Experience::getId)
                    .max().orElse(0) + 1;
            e.setId(nextId);
        }
        list.add(e);
        writeList(list);
    }

    public void deleteById(Long id) {
        List<Experience> list = findAll();
        list.removeIf(exp -> exp.getId().equals(id));
        writeList(list);
    }

    private void writeList(List<Experience> list) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
