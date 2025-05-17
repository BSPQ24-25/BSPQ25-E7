console.log("administration.js cargado correctamente");

let selectedRow = null;
let selectedId = null;

document.addEventListener("DOMContentLoaded", () => {
    const rows = document.querySelectorAll("#reservation-table tbody tr");

    rows.forEach(row => {
        row.addEventListener("click", () => {
            if (selectedRow) {
                selectedRow.classList.remove("selected-row");
            }

            row.classList.add("selected-row");
            selectedRow = row;
            selectedId = row.getAttribute("data-id");

            console.log("Reserva seleccionada:", selectedId);
        });
    });

    // Sistema de notificaciones 🔔
    const notifBtn = document.getElementById('notification-btn');
    const notifPopup = document.getElementById('notification-popup');
    const notifList = document.getElementById('notification-list');

    notifBtn.addEventListener('click', () => {
        if (notifPopup.classList.contains('hidden')) {
            fetch('/admin/notifications')
                .then(res => res.json())
                .then(data => {
                    notifList.innerHTML = ''; // limpiamos la lista
                    if (data.length === 0) {
                        notifList.innerHTML = '<li>No hay notificaciones nuevas.</li>';
                    } else {
                        data.forEach(n => {
                            const li = document.createElement('li');
                            li.textContent = n.message;
                            notifList.appendChild(li);
                        });

                        // 🟢 MARCAR COMO LEÍDAS
                        return fetch('/admin/notifications/mark-as-read', {
                            method: 'POST'
                        });
                    }

                    notifPopup.classList.remove('hidden');
                })
                .catch(err => {
                    notifList.innerHTML = '<li>Error al cargar notificaciones.</li>';
                    notifPopup.classList.remove('hidden');
                    console.error("Error cargando notificaciones:", err);
                });

            notifPopup.classList.remove('hidden');
        } else {
            notifPopup.classList.add('hidden');
        }
    });
});

function confirmAction(action) {
    if (!selectedId) {
        alert("Debes seleccionar una reserva primero.");
        return;
    }

    const msg = action === 'confirm'
        ? "¿Desea confirmar la reserva seleccionada?"
        : "¿Desea cancelar la reserva seleccionada?";

    if (confirm(msg)) {
        fetch(`/admin/reservations/${selectedId}/${action}`, {
            method: "POST"
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                alert("Error al procesar la reserva.");
            }
        })
        .catch(err => {
            console.error("Error:", err);
            alert("Se produjo un error al intentar realizar la acción.");
        });
    }
}