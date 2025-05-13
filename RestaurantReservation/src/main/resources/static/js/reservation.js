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

function openReviewModal(reservationId) {
    console.log("Opening Review Modal for reservation: " + reservationId);
    document.getElementById('reservationId').value = reservationId;
    document.getElementById('reviewModal').style.display = 'block';
}

function closeReviewModal() {
    document.getElementById('reviewModal').style.display = 'none';
    document.getElementById('reviewMessage').innerText = '';
    document.getElementById('reviewForm').reset();
}

document.getElementById('reviewForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const reservationId = document.getElementById('reservationId').value;
    const rating = document.getElementById('rating').value;
    const comment = document.getElementById('comment').value;

    const reviewData = {
        customerId: CURRENT_USER_ID, // Fills with Thymeleaf or JS the ID of the current user
        reservationId: reservationId,
        rating: rating,
        comment: comment
    };

    fetch('/reviews', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reviewData)
    })
    .then(response => response.ok ? response.json() : Promise.reject(response))
    .then(data => {
        document.getElementById('reviewMessage').innerText = "Review submitted successfully!";
        setTimeout(() => location.reload(), 1000);
    })
    .catch(err => {
        document.getElementById('reviewMessage').innerText = "Error submitting review.";
    });
});

function toggleReviewForm(reservationId) {
    const form = document.getElementById('review-form-' + reservationId);
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

function submitReview(reservationId) {
    const form = document.getElementById('reviewForm-' + reservationId);
    const rating = form.querySelector('[name="rating"]').value;
    const comment = form.querySelector('[name="comment"]').value;
    const successDiv = document.getElementById('reviewSuccess-' + reservationId);
    const errorDiv = document.getElementById('reviewError-' + reservationId);
    const errorMsg = document.getElementById('reviewErrorMessage-' + reservationId);

    if (rating < 1 || rating > 5) {
        errorMsg.innerText = "Invalid rating. Please enter a value between 1 and 5.";
        errorDiv.style.display = 'block';
        successDiv.style.display = 'none';
        return;
    } else {
        errorDiv.style.display = 'none';
    }

    const reviewData = {
        customerId: CURRENT_USER_ID,
        reservationId: reservationId,
        rating: rating,
        comment: comment
    };

    fetch('/reviews', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reviewData)
    })
    .then(response => response.ok ? response.json() : Promise.reject(response))
    .then(data => {
        successDiv.style.display = 'block';
        // Oculta el popup y recarga tras 2 segundos
        setTimeout(() => {
            successDiv.style.display = 'none';
            location.reload();
        }, 2000);
    })
    .catch(err => {
        errorMsg.innerText = "Error submitting review. Try again later.";
        errorDiv.style.display = 'block';
        successDiv.style.display = 'none';
    });
}