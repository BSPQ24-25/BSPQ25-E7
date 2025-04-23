document.addEventListener('DOMContentLoaded', function() {
    // Validación del formulario de reserva
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
});