package jianmejia.portfoliosite.service;

import jianmejia.portfoliosite.model.Experience;
import jianmejia.portfoliosite.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {
    private final ExperienceRepository repo = new ExperienceRepository();

    public List<Experience> listOrdered() {
        return repo.findAllOrdered();
    }

    public Experience save(Experience e) {
        repo.save(e);
        return e;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
