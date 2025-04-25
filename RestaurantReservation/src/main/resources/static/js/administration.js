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
