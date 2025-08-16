// /src/main/resources/static/js/timeline.js
(function(){
    const items = document.querySelectorAll('.tl-item');
    if (!items.length) return;

    const now = new Date();
    const monthDiff = (a,b)=> (b.getFullYear()-a.getFullYear())*12 + (b.getMonth()-a.getMonth());

    // Reveal on scroll
    const io = new IntersectionObserver(entries=>{
        entries.forEach(e => { if (e.isIntersecting) e.target.classList.add('visible'); });
    }, { threshold: 0.15 });
    items.forEach(it => io.observe(it));

    // Progress width
    items.forEach(it=>{
        const bar = it.querySelector('.tl-progress .bar');
        if (!bar) return;
        const sd = it.dataset.start ? new Date(it.dataset.start) : null; // yyyy-MM-dd
        const ed = it.dataset.end ? new Date(it.dataset.end) : null;
        if (sd) {
            const end = ed || now;
            const m = Math.max(0, monthDiff(sd, end));
            bar.style.width = Math.max(10, Math.min(100, (m/24)*100)) + '%';
        } else {
            bar.style.width = '10%';
        }
    });
})();
