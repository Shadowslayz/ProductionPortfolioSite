// src/main/java/jianmejia/portfoliopage/model/SiteInfo.java
package jianmejia.portfoliosite.model;


public class SiteInfo {

    private Long id = 1L;               // single row

    private String aboutMarkdown;

    private String downloadUrl;         // .docx link
    private String profileImageUrl;     // headshot URL
    private String githubUrl;
    private String linkedinUrl;
    private String email;               // plain email string


    // getters/setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = 1L; }

    public String getAboutMarkdown() { return aboutMarkdown; }
    public void setAboutMarkdown(String aboutMarkdown) { this.aboutMarkdown = aboutMarkdown; }

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }

    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
