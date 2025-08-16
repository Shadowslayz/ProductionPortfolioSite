@param jianmejia.portfoliosite.model.SiteInfo siteInfo
@param boolean isAdmin

@template.base(title="About Me", content=@`
    <header class="header">
        <div><h1>About Me</h1></div>
        <nav class="header-actions">
            <a class="login-btn" href="/">Home</a>
            @if(siteInfo != null && siteInfo.getDownloadUrl() != null && !siteInfo.getDownloadUrl().isBlank())
                <a class="login-btn" href="${siteInfo.getDownloadUrl()}" target="_blank">Download Portfolio</a>
            @endif
            <!-- Login dropdown same as main page -->
        </nav>
    </header>

    <section class="container about-me">
        <div class="about-left">
            <img src="${siteInfo != null && siteInfo.getProfileImageUrl() != null && !siteInfo.getProfileImageUrl().isBlank()
                ? siteInfo.getProfileImageUrl()
                : "/assets/default-profile.jpg"}" alt="Profile Picture" class="profile-pic">
            <div class="about-right">
                <h3>Contact Me</h3>
                <p>Links to learn more about me</p>
                <div class="social-links">
                    <a href="${siteInfo != null && siteInfo.getGithubUrl() != null ? siteInfo.getGithubUrl() : ""}" target="_blank" class="social-btn github">GitHub</a>
                    <a href="${siteInfo != null && siteInfo.getLinkedinUrl() != null ? siteInfo.getLinkedinUrl() : ""}" target="_blank" class="social-btn linkedin">LinkedIn</a>
                    <a href="mailto:${siteInfo != null && siteInfo.getEmail() != null ? siteInfo.getEmail() : ""}" class="social-btn email">Email Me</a>
                </div>
            </div>
        </div>

        <div class="about-center">
            <h2>About Me</h2>
            <p style="white-space: pre-wrap;">${siteInfo != null && siteInfo.getAboutMarkdown() != null
        ? siteInfo.getAboutMarkdown()
        : "No bio yet."}
            </p>
        </div>


    </section>
`)
