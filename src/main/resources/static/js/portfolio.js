// Login dropdown: accessible + robust
(function () {
    const menu = document.getElementById('loginMenu');
    if (!menu) return;

    const toggle = document.getElementById('loginToggle') || menu.querySelector('.login-btn');
    const form   = menu.querySelector('.login-form');

    const openMenu = () => {
        menu.classList.add('open');
        if (toggle) toggle.setAttribute('aria-expanded', 'true');
        const first = form ? form.querySelector('input,select,textarea,button') : null;
        if (first) first.focus();
    };

    const closeMenu = () => {
        if (!menu.classList.contains('open')) return;
        menu.classList.remove('open');
        if (toggle) toggle.setAttribute('aria-expanded', 'false');
    };

    const isEventInsideMenu = (e) => menu.contains(e.target);

    // Toggle on click
    if (toggle) {
        toggle.addEventListener('click', (e) => {
            e.stopPropagation();
            menu.classList.contains('open') ? closeMenu() : openMenu();
        });

        // Toggle with keyboard (Enter/Space)
        toggle.addEventListener('keydown', (e) => {
            if (e.key === 'Enter' || e.key === ' ') {
                e.preventDefault();
                menu.classList.contains('open') ? closeMenu() : openMenu();
            }
        });
    }
    document.querySelectorAll('.dropzone').forEach(zone => {
        const fileInput = zone.querySelector('.dz-file');
        zone.addEventListener('click', () => fileInput.click());
    });
    // Close on outside click
    document.addEventListener('click', (e) => {
        if (!isEventInsideMenu(e)) closeMenu();
    });

    // Close on Esc
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') closeMenu();
    });

    // Close when focus leaves the dropdown entirely
    document.addEventListener('focusin', (e) => {
        // If focus moved somewhere not inside the menu and it's open, close it
        if (menu.classList.contains('open') && !isEventInsideMenu(e)) {
            closeMenu();
        }
    });

    // Keep clicks inside from bubbling out
    if (form) {
        form.addEventListener('click', (e) => e.stopPropagation());
        // Close after submit (optional)
        form.addEventListener('submit', () => closeMenu());
    }

    // Defensive: close on resize/orientation changes
    window.addEventListener('resize', closeMenu);
    window.addEventListener('orientationchange', closeMenu);

    document.addEventListener("DOMContentLoaded", () => {
        document.getElementById("toggleTechSummary").addEventListener("click", () => {
            document.querySelector(".tech-stats-container").classList.toggle("open");
        });
    });

})();
