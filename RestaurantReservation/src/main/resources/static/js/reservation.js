document.addEventListener('DOMContentLoaded', function() {
    // Validación de cantidad de personas (ya estaba)
    const form = document.querySelector('form');
    if(form) {
        form.addEventListener('submit', function(e) {
            const people = document.getElementById('nPeople').value;
            if(people < 1) {
                e.preventDefault();
                alert('Number of people must be at least 1');
            }
        });
    }

    // Mostrar error desde backend si lo hay
    const errorMessage = document.getElementById('error-message-content');
    if (errorMessage && errorMessage.textContent.trim() !== '') {
        const popup = document.getElementById('error-popup');
        popup.style.display = 'block';
    }
});