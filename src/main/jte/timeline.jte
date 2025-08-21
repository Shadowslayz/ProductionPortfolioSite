@param java.util.List<jianmejia.portfoliosite.model.Experience> experiences
@param boolean isAdmin
@param java.time.format.DateTimeFormatter fmt

@template.base(
title = "Timeline",
content = @`
    <header class="header">
        <div><h1>Work & Project Timeline</h1></div>
        <nav class="header-actions">
            <a class="login-btn" href="/">Home</a>
            <div class="login-dropdown" id="loginMenu">
                @if(isAdmin)
                    <form class="logout-form" action="/logout" method="post">
                        <button class="login-btn" type="submit">Logout</button>
                    </form>
                @else
                    <button class="login-btn" id="loginToggle" aria-haspopup="true" aria-expanded="false">Login</button>
                    <form class="login-form" action="/login" method="post" aria-label="Login form">
                        <label>Username</label>
                        <input name="username" required>
                        <label>Password</label>
                        <input type="password" name="password" required>
                        <button type="submit">Sign In</button>
                    </form>
                @endif
            </div>
        </nav>
    </header>

    <section class="container">
        <div class="panel">
            <div class="timeline">
                @for(jianmejia.portfoliosite.model.Experience exp : experiences)
                    <article class="tl-item"
                             data-status="${exp.getEndDate() == null ? "current" : "done"}"
                             data-start="${exp.getStartDate() != null ? exp.getStartDate().toString() : ""}"
                             data-end="${exp.getEndDate() != null ? exp.getEndDate().toString() : ""}">
                        <div class="tl-dot"></div>
                        <div class="tl-card">
                            <div class="tl-head">
                                <span class="tl-type">${exp.getOrg() != null ? exp.getOrg() : "Experience"}</span>
                                <h3 class="tl-title">
                                    ${exp.getTitle() != null ? exp.getTitle() : "Untitled"}
                                    @if(exp.getEndDate() == null)
                                        <span class="tl-badge">Current</span>
                                    @endif
                                </h3>
                            </div>

                            <div class="tl-sub">
                                @if(exp.getLocation() != null && !exp.getLocation().isBlank())
                                    ${exp.getLocation()} <span class="sep">•</span>
                                @endif
                                <span class="tl-dates">
                                    ${exp.getStartDate() != null ? exp.getStartDate().format(fmt) : "Unknown"} —
                                    ${exp.getEndDate() != null ? exp.getEndDate().format(fmt) : "Present"}
                                </span>
                            </div>

                            <div class="tl-content-row">
                                <div class="tl-text">
                                    @if(exp.getSummary() != null)
                                        <p class="tl-desc">${exp.getSummary()}</p>
                                    @endif

                                    @if(exp.getLink() != null && !exp.getLink().isBlank())
                                        <a class="btn tl-link"
                                           href="${exp.getLink().matches("(?i)^https?://.*") ? exp.getLink() : "https://" + exp.getLink()}"
                                           target="_blank" rel="noopener">
                                            View
                                        </a>
                                    @endif
                                </div>

                                @if(exp.getPreviewImage() != null && !exp.getPreviewImage().isBlank())
                                    <div class="tl-image fade-in-right">
                                        <img src="${exp.getPreviewImage()}"
                                             alt="${exp.getTitle() != null ? exp.getTitle() : "Experience"} preview">
                                    </div>
                                @endif
                            </div>
                        </div>
                    </article>
                @endfor
            </div>
        </div>
    </section>
`
)