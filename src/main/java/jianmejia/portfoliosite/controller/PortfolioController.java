package jianmejia.portfoliosite.controller;

import jakarta.servlet.http.HttpSession;
import jianmejia.portfoliosite.model.Experience;
import jianmejia.portfoliosite.model.Project;
import jianmejia.portfoliosite.service.ExperienceService;
import jianmejia.portfoliosite.service.ProjectService;
import jianmejia.portfoliosite.service.SiteInfoService;
import jianmejia.portfoliosite.password.PasswordStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.LinkedHashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class PortfolioController {

    private final ProjectService service;
    private final SiteInfoService siteInfo;
    private final ExperienceService experiences;

    public PortfolioController(ProjectService service, SiteInfoService siteInfo, ExperienceService experiences) {
        this.service = service;
        this.siteInfo = siteInfo;
        this.experiences = experiences;
    }

    @GetMapping("/")
    public String portfolio(Model model, HttpSession session) {
        var projects = service.findAllOrdered();
        var info = siteInfo.get();
        boolean isAdmin = Boolean.TRUE.equals(session.getAttribute("ADMIN"));
        var experiencesRecent = experiences.listOrdered().stream().limit(5).toList();

        long total = projects.size();
        long completed = projects.stream().filter(p -> "completed".equals(p.getStatus())).count();
        long inProgress = projects.stream().filter(p -> "in-progress".equals(p.getStatus())).count();
        long solo = projects.stream().filter(p -> "solo".equals(p.getType())).count();
        long group = projects.stream().filter(p -> "group".equals(p.getType())).count();

        // Tech aggregation
        Map<String, Long> techCounts = projects.stream()
                .flatMap(p -> (p.getTechnologies() == null ? Stream.empty() :
                        Arrays.stream(p.getTechnologies().split(","))))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.groupingBy(t -> t, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new // preserve sorted order
                ));

        model.addAttribute("projects", projects);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("total", total);
        model.addAttribute("completed", completed);
        model.addAttribute("inProgress", inProgress);
        model.addAttribute("solo", solo);
        model.addAttribute("group", group);
        model.addAttribute("siteInfo", info);
        model.addAttribute("experiencesRecent", experiencesRecent);
        model.addAttribute("techCounts", techCounts);
        return "portfolio";
    }

    @PostMapping("/projects/{id}/highlight")
    public String toggleHighlight(@PathVariable Long id, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("ADMIN"))) return "redirect:/";
        service.toggleHighlight(id);
        return "redirect:/";
    }

    // Simple session auth
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        if (PasswordStorage.getUsername().equals(username) && PasswordStorage.getPassword().equals(password)) {
            session.setAttribute("ADMIN", true);
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/about")
    public String about(Model model, HttpSession session) {
        model.addAttribute("isAdmin", Boolean.TRUE.equals(session.getAttribute("ADMIN")));
        model.addAttribute("siteInfo", siteInfo.get());
        return "about";
    }

    @PostMapping("/projects/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) throws IOException {
        if (!Boolean.TRUE.equals(session.getAttribute("ADMIN"))) return "redirect:/";

        // 1. Delete project from DB
        service.delete(id);

        // 2. Delete its static uploads folder if exists
        Path base = Paths.get("/opt/portfolio/uploads/projects", String.valueOf(id)).normalize();
        if (Files.exists(base)) {
            FileSystemUtils.deleteRecursively(base);
        }

        return "redirect:/";
    }


    @PostMapping("/projects/{id}/toggle")
    public String toggle(@PathVariable Long id, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("ADMIN"))) return "redirect:/";
        service.toggleStatus(id);
        return "redirect:/";
    }

    @PostMapping("/siteinfo")
    public String updateSiteInfo(@RequestParam(required = false) String aboutMarkdown,
                                 @RequestParam(required = false) String downloadUrl,
                                 @RequestParam(required = false) String profileImageUrl,
                                 @RequestParam(required = false) String githubUrl,
                                 @RequestParam(required = false) String linkedinUrl,
                                 @RequestParam(required = false) String email,
                                 HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("ADMIN"))) return "redirect:/";
        var s = siteInfo.get();
        s.setAboutMarkdown(aboutMarkdown);
        s.setDownloadUrl(downloadUrl);
        s.setProfileImageUrl(profileImageUrl);
        s.setGithubUrl(githubUrl);
        s.setLinkedinUrl(linkedinUrl);
        s.setEmail(email);
        siteInfo.save(s);
        return "redirect:/about";
    }

    @GetMapping("/projects/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("ADMIN"))) return "redirect:/";
        var p = service.findById(id).orElse(null);
        if (p == null) return "redirect:/";
        model.addAttribute("project", p);
        model.addAttribute("isAdmin", true);
        model.addAttribute("siteInfo", siteInfo.get());
        return "project_edit";
    }

    @PostMapping("/projects/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute Project form,
                         @RequestParam(value="staticFile", required=false) MultipartFile staticFile,
                         HttpSession session) throws IOException {
        if (!Boolean.TRUE.equals(session.getAttribute("ADMIN"))) return "redirect:/";

        Project updated = service.update(id, form);

        if (staticFile != null && !staticFile.isEmpty()) {
            saveSingleHtmlForProject(id, staticFile);
            updated.setStaticEntry("/static-uploads/projects/" + id + "/index.html");
            service.save(updated);
        }

        return "redirect:/";
    }

    // ===== Timeline =====
    @GetMapping("/timeline")
    public String timeline(Model model, HttpSession session) {
        List<Experience> list = experiences.listOrdered();
        boolean isAdmin = Boolean.TRUE.equals(session.getAttribute("ADMIN"));

        model.addAttribute("experiences", list);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("fmt", DateTimeFormatter.ofPattern("MMM yyyy"));
        return "timeline";
    }

    @PostMapping("/experience")
    public String createExperience(@ModelAttribute Experience form, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("ADMIN"))) return "redirect:/";
        experiences.save(form);
        return "redirect:/";
    }

    @PostMapping("/experience/{id}/delete")
    public String deleteExperience(@PathVariable Long id, HttpSession session) {
        if (!Boolean.TRUE.equals(session.getAttribute("ADMIN"))) return "redirect:/";
        experiences.delete(id);
        return "redirect:/";
    }

    // ---- Upload a single HTML to this project ----
    @PostMapping("/projects/{id}/upload-html")
    public String uploadSingleHtml(@PathVariable Long id,
                                   @RequestParam("file") MultipartFile file,
                                   HttpSession session) throws IOException {
        if (!Boolean.TRUE.equals(session.getAttribute("ADMIN"))) return "redirect:/";

        var project = service.findById(id).orElse(null);
        if (project == null) return "redirect:/";

        saveSingleHtmlForProject(id, file);

        project.setStaticEntry("/static-uploads/projects/" + id + "/index.html");
        service.save(project);

        return "redirect:/projects/" + id + "/edit";
    }

    // ---- Add-project with optional single HTML (same idea) ----
    @PostMapping("/projects")
    public String addProject(@ModelAttribute Project p,
                             @RequestParam(value="staticFile", required=false) MultipartFile staticFile,
                             HttpSession session) throws IOException {
        if (!Boolean.TRUE.equals(session.getAttribute("ADMIN"))) return "redirect:/";

        if (p.getType() != null && !"group".equals(p.getType())) p.setGroupSize(null);
        else if (p.getGroupSize() == null) p.setGroupSize(1);
        if (p.getStatus() == null || p.getStatus().isBlank()) p.setStatus("in-progress");

        Project saved = service.save(p);

        if (staticFile != null && !staticFile.isEmpty()) {
            saveSingleHtmlForProject(saved.getId(), staticFile);
            saved.setStaticEntry("/static-uploads/projects/" + saved.getId() + "/index.html");
            service.save(saved);
        }
        return "redirect:/";
    }

    // ---- Helper used by both endpoints ----
    private void saveSingleHtmlForProject(Long id, MultipartFile file) throws IOException {
        String name = (file.getOriginalFilename() != null) ? file.getOriginalFilename().toLowerCase() : "index.html";
        if (!(name.endsWith(".html") || name.endsWith(".htm"))) {
            throw new IOException("Only .html/.htm files are allowed");
        }

        // Save to /opt/portfolio/uploads/projects/{id}
        Path base = Paths.get("/opt/portfolio/uploads/projects", String.valueOf(id)).normalize();
        if (Files.exists(base)) FileSystemUtils.deleteRecursively(base);
        Files.createDirectories(base);

        Path out = base.resolve("index.html");
        Files.copy(file.getInputStream(), out, StandardCopyOption.REPLACE_EXISTING);
    }


}
