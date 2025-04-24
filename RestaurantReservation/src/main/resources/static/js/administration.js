let selectedRow = null;
let selectedId = null;

function selectReservation(row) {
    // Deselecciona la anterior
    if (selectedRow) {
        selectedRow.classList.remove("selected-row");
    }

    // Marca la nueva
    selectedRow = row;
    selectedRow.classList.add("selected-row");

    selectedId = row.getAttribute("data-id");
    console.log("Reserva seleccionada:", selectedId);
}

function confirmAction(action) {
    if (!selectedId) {
        alert("Debes seleccionar una reserva primero.");
        return;
    }

    const msg = action === 'confirm'
        ? "¿Desea confirmar la reserva seleccionada?"
        : "¿Desea cancelar la reserva seleccionada?";

    if (confirm(msg)) {
        // Redirige a los endpoints de backend
        window.location.href = `/admin/reservations/${selectedId}/${action}`;
    }
}
