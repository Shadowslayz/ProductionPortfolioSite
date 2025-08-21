// src/main/java/jianmejia/portfoliopage/model/Experience.java
package jianmejia.portfoliosite.model;


import java.time.LocalDate;

public class Experience {
    private Long id;

    private String title;
    private String org;        // optional
    private String location;   // optional

    private LocalDate startDate;  // <-- matches repo method
    private LocalDate endDate;    // null = current

    private String summary;       // description
    private String link;          // optional
    private String previewImage; // optional

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOrg() { return org; }
    public void setOrg(String org) { this.org = org; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public String getPreviewImage() { return previewImage; }
    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }
}
