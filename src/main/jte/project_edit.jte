@param jianmejia.portfoliosite.model.Project project
@param boolean isAdmin
@param jianmejia.portfoliosite.model.SiteInfo siteInfo

@template.base(
title = "Edit Project",
content = @`
    <header class="header">
        <div>
            <h1>Edit Project</h1>
            <p>Update details and save</p>
        </div>

        <div class="login-dropdown" id="loginMenu">
            <form class="logout-form" action="/logout" method="post">
                <button class="login-btn" type="submit">Logout</button>
            </form>
        </div>
    </header>

    <section class="container">
        <div class="panel">
            <h3>${project.getTitle() != null ? project.getTitle() : "Project"}</h3>

            <!-- Edit form -->
            <form class="form-grid" action="/projects/${project.getId()}/edit" method="post" enctype="multipart/form-data">
                <div>
                    <label>Title</label>
                    <input name="title" value="${project.getTitle()}" required>
                </div>

                <div>
                    <label>Image URL</label>
                    <input name="image" value="${project.getImage() != null ? project.getImage() : ""}">
                </div>

                <div>
                    <!-- Status -->
                    <label>Status</label>
                    <select name="status">
                        @if("completed".equals(project.getStatus()))
                            <option value="completed" selected="selected">Completed</option>
                            <option value="in-progress">In Progress</option>
                        @else
                            <option value="completed">Completed</option>
                            <option value="in-progress" selected="selected">In Progress</option>
                        @endif
                    </select>
                </div>

                <div>
                    <!-- Type -->
                    <label>Type</label>
                    <select name="type" id="typeSelect">
                        @if("solo".equals(project.getType()))
                            <option value="solo" selected="selected">Solo</option>
                            <option value="group">Group</option>
                        @else
                            <option value="solo">Solo</option>
                            <option value="group" selected="selected">Group</option>
                        @endif
                    </select>
                </div>
                <label style="display:flex;gap:.5rem;align-items:center;">
                    @if(project.isProfessional())
                        <input type="checkbox" name="professional" checked="checked">
                    @else
                        <input type="checkbox" name="professional">
                    @endif

                    Professional (client/paid project)
                </label>

                <div id="groupSizeWrap">
                    <label>Group Size (if Group)</label>
                    <input name="groupSize" type="number" min="1" value="${project.getGroupSize() != null ? project.getGroupSize() : 1}">
                </div>

                <div style="grid-column:1/-1;">
                    <label>Technologies (comma-separated)</label>
                    <input name="technologies"
                           value="${project.getTechnologies() != null ? project.getTechnologies() : ""}"
                           placeholder="Java, Spring Boot, MySQL">
                </div>

                <label style="display:flex;gap:.5rem;align-items:center;">
                    @if(project.isHighlighted())
                        <input type="checkbox" name="highlighted" checked="checked">
                    @else
                        <input type="checkbox" name="highlighted">
                    @endif

                    Highlight (feature on top)
                </label>

                <div class="row">
                    <div>
                        <label>Start Date</label>
                        <input name="startDate" type="date"
                               value="${project.getStartDate() != null ? project.getStartDate().toString() : ""}">
                    </div>
                    <div>
                        <label>Completed / ETA</label>
                        <input name="endDate" type="date"
                               value="${project.getEndDate() != null ? project.getEndDate().toString() : ""}">
                    </div>
                </div>

                <div style="grid-column:1/-1;">
                    <label>Description</label>
                    <textarea name="description">${project.getDescription() != null ? project.getDescription() : ""}</textarea>
                </div>

                <div class="row">
                    <div>
                        <label>Live Demo URL</label>
                        <input name="demoUrl" value="${project.getDemoUrl() != null ? project.getDemoUrl() : ""}">
                    </div>
                    <div>
                        <label>GitHub URL</label>
                        <input name="repoUrl" value="${project.getRepoUrl() != null ? project.getRepoUrl() : ""}">
                    </div>
                </div>
                <!-- Static HTML Upload -->
                <div style="grid-column:1/-1;">
                    <label>Upload Static HTML</label>
                    <input type="file" name="staticFile" accept=".html,.htm">
                </div>
                <div class="actions">
                    <button class="btn btn-accent" type="submit">Save</button>
                    <a class="btn" href="/">Cancel</a>
                </div>
            </form>
        </div>


    </section>
`
)
