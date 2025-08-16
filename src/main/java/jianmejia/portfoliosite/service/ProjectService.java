package jianmejia.portfoliosite.service;

import jianmejia.portfoliosite.model.Project;
import jianmejia.portfoliosite.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository repo = new ProjectRepository();

    public List<Project> findAll() {
        return repo.findAll();
    }

    public Project save(Project p) {
        repo.save(p);
        return p;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void toggleStatus(Long id) {
        repo.findById(id).ifPresent(p -> {
            p.setStatus("completed".equals(p.getStatus()) ? "in-progress" : "completed");
            repo.save(p);
        });
    }

    public Optional<Project> findById(Long id) {
        return repo.findById(id);
    }

    public List<Project> findAllOrdered() {
        return repo.findAllOrdered();
    }

    public void toggleHighlight(Long id) {
        repo.findById(id).ifPresent(p -> {
            p.setHighlighted(!p.isHighlighted());
            repo.save(p);
        });
    }

    public Project update(Long id, Project form) {
        return repo.findById(id).map(p -> {
            p.setTitle(form.getTitle());
            p.setImage(form.getImage());
            p.setStatus(form.getStatus());
            p.setType(form.getType());
            p.setGroupSize("group".equals(form.getType()) ?
                    (form.getGroupSize() == null ? 1 : form.getGroupSize()) : null);
            p.setStartDate(form.getStartDate());
            p.setEndDate(form.getEndDate());
            p.setDescription(form.getDescription());
            p.setDemoUrl(form.getDemoUrl());
            p.setRepoUrl(form.getRepoUrl());
            p.setTechnologies(form.getTechnologies());
            p.setHighlighted(form.isHighlighted());
            repo.save(p);
            return p;
        }).orElseThrow();
    }
}
