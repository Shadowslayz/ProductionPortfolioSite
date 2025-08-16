(function(){
    const zones = document.querySelectorAll('.dropzone');
    if (!zones.length) return;

    zones.forEach(z => {
        const input = z.querySelector('.dz-file');
        const endpoint = z.dataset.upload;

        function send(file){
            if(!file) return;
            const fd = new FormData();
            fd.append('file', file);

            z.classList.add('dz-uploading');

            fetch(endpoint, { method:'POST', body: fd })
                .then(res => {
                    if (!res.ok) throw new Error('Upload failed: ' + res.status);
                    // Your controller returns redirect:/ — fetch won’t follow, so just reload.
                    window.location.reload();
                })
                .catch(err => {
                    z.classList.remove('dz-uploading');
                    alert(err.message);
                });
        }

        // Click to open picker
        z.addEventListener('click', () => input && input.click());
        if (input) input.addEventListener('change', e => send(e.target.files[0]));

        // Drag & drop
        z.addEventListener('dragover', e => { e.preventDefault(); z.classList.add('dz-over'); });
        z.addEventListener('dragleave', () => z.classList.remove('dz-over'));
        z.addEventListener('drop', e => {
            e.preventDefault(); z.classList.remove('dz-over');
            const file = e.dataTransfer.files && e.dataTransfer.files[0];
            send(file);
        });
    });
})();
