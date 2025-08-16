@import jianmejia.portfoliosite.view.Tech
@param java.util.List<jianmejia.portfoliosite.model.Project> projects
@param jianmejia.portfoliosite.model.SiteInfo siteInfo
@param boolean isAdmin
@param long total
@param long completed
@param long inProgress
@param long solo
@param long group
@param java.util.List<jianmejia.portfoliosite.model.Experience> experiencesRecent
@template.base(
title = "Jian Mejia's Portfolio",
content = @`
    <header class="header">
        <div>
            <h1>Jian Mejia's Portfolio</h1>
            <p>A showcase of my solo and group work</p>
        </div>

        <!-- Login dropdown (same page) -->
        <nav class="header-actions">
            <a class="login-btn removeUnderline" href="/about">About Me</a>
            <a class="login-btn removeUnderline" href="/timeline">Experience Timeline</a>
            @if(siteInfo != null && siteInfo.getDownloadUrl() != null && !siteInfo.getDownloadUrl().isBlank())
                <a class="login-btn removeUnderline" href="${siteInfo.getDownloadUrl()}" target="_blank">Download Portfolio</a>
            @endif
            <!-- Login dropdown -->
            <div class="login-dropdown" id="loginMenu">
                @if(isAdmin)
                    <form class="logout-form" action="/logout" method="post">
                        <button class="login-btn" type="submit">Logout</button>
                    </form>
                @else
                    <button class="login-btn" id="loginToggle" aria-haspopup="true" aria-expanded="false">
                        Login
                    </button>
                    <form class="login-form" action="/login" method="post" aria-label="Login form">
                        <label>Username</label>
                        <input name="username" required autocomplete="username">
                        <label>Password</label>
                        <input type="password" name="password" required autocomplete="current-password">
                        <button type="submit">Sign In</button>
                    </form>
                @endif
            </div>
        </nav>

    </header>

    @if(isAdmin)
        <section class="container">
            <div class="stats">
                <div class="stat"><div class="label">Total</div><div class="value">${total}</div></div>
                <div class="stat"><div class="label">Completed</div><div class="value">${completed}</div></div>
                <div class="stat"><div class="label">In Progress</div><div class="value">${inProgress}</div></div>
                <div class="stat"><div class="label">Solo</div><div class="value">${solo}</div></div>
                <div class="stat"><div class="label">Group</div><div class="value">${group}</div></div>
            </div>

            <div class="panel">
                <h3>Add Project</h3>
                <form action="/projects" method="post" class="form-grid" enctype="multipart/form-data">
                    <div>
                        <label>Title</label>
                        <input name="title" required>
                    </div>
                    <div>
                        <label>Image URL</label>
                        <input name="image">
                    </div>
                    <div>
                        <label>Status</label>
                        <select name="status">
                            <option value="completed">Completed</option>
                            <option value="in-progress">In Progress</option>
                        </select>
                    </div>
                    <div>
                        <label>Type</label>
                        <select name="type" id="typeSelect">
                            <option value="solo">Solo</option>
                            <option value="group">Group</option>
                        </select>
                    </div>
                    <div>
                        <label>Group Size</label>
                        <input type="number" name="groupSize" min="1">
                    </div>
                    <div style="grid-column:1/-1;">
                        <label>Technologies (comma-separated)</label>
                        <input name="technologies" placeholder="Java, Spring Boot, MySQL">
                    </div>
                    <div>
                        <label style="display:flex;gap:.5rem;align-items:center;">
                            <input type="checkbox" name="highlighted"> Highlight (feature on top)
                        </label>
                    </div>
                    <div>
                        <label>Start Date</label>
                        <input type="date" name="startDate">
                    </div>
                    <div>
                        <label>End Date / ETA</label>
                        <input type="date" name="endDate">
                    </div>
                    <div style="grid-column:1/-1;">
                        <label>Description</label>
                        <textarea name="description"></textarea>
                    </div>
                    <div>
                        <label>Live Demo URL</label>
                        <input name="demoUrl">
                    </div>
                    <div>
                        <label>GitHub URL</label>
                        <input name="repoUrl">
                    </div>

                    <!-- Static HTML/ZIP Upload -->
                    <div style="grid-column:1/-1;">
                        <label>Upload Static HTML</label>
                        <div class="dropzone" title="Drop .html here">
                            <div class="dz-body">
                                <strong>Drop HTML</strong>
                                <span class="dz-help">or click to choose…</span>
                            </div>
                            <input class="dz-file" type="file" accept=".html,.htm" name="staticFile" hidden>
                        </div>
                    </div>

                    <div class="actions" style="grid-column:1/-1;">
                        <button type="submit" class="btn btn-accent">Add Project</button>
                    </div>
                </form>
            </div>
            <div class="panel" style="margin-top:1rem;">
                <h3>About Me & Links</h3>
                <form action="/siteinfo" method="post" class="form-grid">
                    <div style="grid-column:1/-1;">
                        <label>About Me (Markdown / plain text)</label>
                        <textarea name="aboutMarkdown" rows="6">${siteInfo != null && siteInfo.getAboutMarkdown() != null ? siteInfo.getAboutMarkdown() : ""}</textarea>
                    </div>

                    <div>
                        <label>Profile Image URL</label>
                        <input name="profileImageUrl" value="${siteInfo != null && siteInfo.getProfileImageUrl() != null ? siteInfo.getProfileImageUrl() : ""}" placeholder="https://.../me.jpg">
                    </div>

                    <div>
                        <label>Portfolio Download Link (.docx)</label>
                        <input type="url" name="downloadUrl" value="${siteInfo != null && siteInfo.getDownloadUrl() != null ? siteInfo.getDownloadUrl() : ""}" placeholder="https://example.com/MyPortfolio.docx">
                    </div>

                    <div>
                        <label>GitHub URL</label>
                        <input type="url" name="githubUrl" value="${siteInfo != null && siteInfo.getGithubUrl() != null ? siteInfo.getGithubUrl() : ""}" placeholder="https://github.com/yourname">
                    </div>

                    <div>
                        <label>LinkedIn URL</label>
                        <input type="url" name="linkedinUrl" value="${siteInfo != null && siteInfo.getLinkedinUrl() != null ? siteInfo.getLinkedinUrl() : ""}" placeholder="https://linkedin.com/in/yourname">
                    </div>

                    <div>
                        <label>Email</label>
                        <input type="email" name="email" value="${siteInfo != null && siteInfo.getEmail() != null ? siteInfo.getEmail() : ""}" placeholder="you@example.com">
                    </div>

                    <div class="actions" style="grid-column:1/-1;">
                        <button class="btn btn-accent" type="submit">Save About & Links</button>
                    </div>
                </form>
            </div>
            <div class="panel" style="margin-bottom:1rem;">
                <h3>Add Experience</h3>
                <form action="/experience" method="post" class="form-grid">
                    <div>
                        <label>Title</label>
                        <input name="title" required placeholder="Software Engineer">
                    </div>
                    <div>
                        <label>Organization</label>
                        <input name="org" placeholder="Acme Inc.">
                    </div>
                    <div>
                        <label>Location</label>
                        <input name="location" placeholder="Atlanta, GA">
                    </div>
                    <div>
                        <label>Start Date</label>
                        <input name="startDate" type="date" required>
                    </div>
                    <div>
                        <label>End Date (blank = Present)</label>
                        <input name="endDate" type="date">
                    </div>
                    <div style="grid-column:1/-1;">
                        <label>Summary</label>
                        <textarea name="summary" rows="3" placeholder="Built X, improved Y by Z%..."></textarea>
                    </div>
                    <div style="grid-column:1/-1;">
                        <label>Link (company/project)</label>
                        <input name="link" placeholder="https://…">
                    </div>
                    <div class="actions" style="grid-column:1/-1;">
                        <button class="btn btn-accent" type="submit">Add to Timeline</button>
                    </div>
                </form>
            </div>
            <div class="panel">
                <h3>Recent Timeline Entries</h3>
                @if(experiencesRecent == null || experiencesRecent.isEmpty())
                    <p class="description">No experiences yet.</p>
                @else
                    <ul style="list-style:none; padding:0; margin:0;">
                        @for(jianmejia.portfoliosite.model.Experience e : experiencesRecent)
                            <li style="display:flex; justify-content:space-between; align-items:center; padding:.4rem 0; border-bottom:1px solid #1b1b1b;">
                                <div>
                                    <b>${e.getTitle() != null ? e.getTitle() : "Untitled"}</b>
                                    @if(e.getOrg() != null && !e.getOrg().isBlank())
                                        <span class="sep">•</span>${e.getOrg()}
                                    @endif
                                    <span class="sep">•</span>
                                    ${e.getStartDate() != null
                              ? e.getStartDate().format(java.time.format.DateTimeFormatter.ofPattern("MMM yyyy")) : "?"}
                                    —
                                    ${e.getEndDate() != null
                              ? e.getEndDate().format(java.time.format.DateTimeFormatter.ofPattern("MMM yyyy")) : "Present"}
                                </div>
                                <form action="/experience/${e.getId()}/delete" method="post">
                                    <button class="btn" type="submit">Delete</button>
                                </form>
                            </li>
                        @endfor
                    </ul>
                    <div class="actions" style="margin-top:.5rem;">
                        <a class="btn" href="/timeline">Open full timeline</a>
                    </div>
                @endif
            </div>
        </section>
    @endif
    <div class="grid container">
        @for(jianmejia.portfoliosite.model.Project p : projects)
            <div class="project ${p.isHighlighted() ? "highlight-project" : ""}">
                <img src="${p.getImage() != null ? p.getImage() : "https://picsum.photos/seed/placeholder/800/400"}" alt="Project Image">
                <div class="project-content">

                    <!-- Title + Tech hover menu -->
                    <div class="card-head">
                        <h2 class="title">
                            @if(p.getStaticEntry() != null && !p.getStaticEntry().isBlank())
                                <a class="project-title clickable" href="${p.getStaticEntry()}" target="_blank" rel="noopener">
                                    ${p.getTitle()} <i class="bi bi-info-circle info-icon"></i>
                                </a>
                            @else
                                <span class="project-title non-clickable">
                                    ${p.getTitle()}
                                </span>
                            @endif
                            @if(p.isHighlighted()) <span class="badge-featured" title="Featured">★</span> @endif
                        </h2>
                        @if(!Tech.list(p.getTechnologies()).isEmpty())
                            <div class="tech-menu" aria-haspopup="true">
                                <button type="button" class="tech-btn" title="Technologies" aria-label="Technologies">⚙️</button>
                                <div class="tech-panel">
                                    <div class="tech-title">Technologies</div>
                                    <div class="tech-list">
                                        @for(String t : Tech.list(p.getTechnologies()))
                                            <span class="tech-pill">${t}</span>
                                        @endfor
                                    </div>
                                </div>
                            </div>
                        @endif
                    </div>

                    <div class="tags">
                        <span class="tag ${p.getStatus()}">${"completed".equals(p.getStatus()) ? "Completed" : "In Progress"}</span>
                        <span class="tag ${p.getType()}">${"group".equals(p.getType()) ? "Group" : "Solo"}</span>
                        @if("group".equals(p.getType()))
                            <span class="tag group-size">${p.getGroupSize() != null ? p.getGroupSize() : 1} Members</span>
                        @endif
                    </div>

                    <div class="meta">
                        <div>
                            <b>Started:</b>
                            ${p.getStartDate() == null
                              ? "—"
                              : p.getStartDate().format(java.time.format.DateTimeFormatter.ofPattern("MMM d, yyyy"))}
                        </div>
                        <span class="sep">•</span>
                        <div>
                            <b>${"completed".equals(p.getStatus()) ? "Completed:" : "ETA:"}</b>
                            ${p.getEndDate() == null
                              ? "TBD"
                              : p.getEndDate().format(java.time.format.DateTimeFormatter.ofPattern("MMM d, yyyy"))}

                            @if(p.getStartDate() != null && p.getEndDate() != null)
                                <span class="sep">•</span>
                                <b>Total Time:</b>
                                @if(java.time.Period.between(p.getStartDate(), p.getEndDate()).getYears() != 0)
                                    ${java.time.Period.between(p.getStartDate(), p.getEndDate()).getYears()}y
                                @endif
                                @if(java.time.Period.between(p.getStartDate(), p.getEndDate()).getMonths() != 0)
                                    ${java.time.Period.between(p.getStartDate(), p.getEndDate()).getMonths()}m
                                @endif
                                @if(java.time.Period.between(p.getStartDate(), p.getEndDate()).getDays() != 0)
                                    ${java.time.Period.between(p.getStartDate(), p.getEndDate()).getDays()}d
                                @endif
                            @endif
                        </div>
                    </div>

                    @if(p.getDescription() != null)
                        <p class="description">${p.getDescription()}</p>
                    @endif

                    <div class="links">
                        @if(p.getDemoUrl() != null && !p.getDemoUrl().isBlank())
                            <a href="${p.getDemoUrl()}">Live Demo</a>
                        @endif
                        @if(p.getRepoUrl() != null && !p.getRepoUrl().isBlank())
                            <a href="${p.getRepoUrl()}">GitHub</a>
                        @endif
                    </div>
                </div>

                @if(isAdmin)
                    <div class="card-footer">
                        <a class="btn" href="/projects/${p.getId()}/edit">Edit</a>
                        <form action="/projects/${p.getId()}/highlight" method="post" style="display:inline;">
                            <button class="btn" type="submit">
                                ${p.isHighlighted() ? "Unfeature" : "Feature"}
                            </button>
                        </form>
                        <form action="/projects/${p.getId()}/toggle" method="post" style="display:inline;">
                            <button class="btn" type="submit">Toggle Status</button>
                        </form>
                        <form action="/projects/${p.getId()}/delete" method="post" style="display:inline;">
                            <button class="btn" type="submit">Delete</button>
                        </form>
                    </div>
                @endif
            </div>
        @endfor
    </div>
`
)
