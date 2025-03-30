const API_BASE_URL = window.location.hostname === 'localhost' 
    ? 'http://localhost:8080/api/auth' 
    : '/api/auth';
const PASSWORD_MIN_LENGTH = 8;

// Función para mostrar errores en los campos del formulario
const showError = (elementId, message) => {
    const element = document.getElementById(elementId);
    if (!element) return;

    let errorElement = document.getElementById(`${elementId}-error`);
    if (!errorElement) {
        errorElement = document.createElement('div');
        errorElement.id = `${elementId}-error`;
        errorElement.className = 'error-message';
        element.parentNode.insertBefore(errorElement, element.nextSibling);
    }

    element.classList.add('error');
    errorElement.textContent = message;
    errorElement.style.display = 'block';
};

// Función para limpiar errores
const clearError = (elementId) => {
    const element = document.getElementById(elementId);
    if (element) {
        element.classList.remove('error');
        const errorElement = document.getElementById(`${elementId}-error`);
        if (errorElement) {
            errorElement.style.display = 'none';
        }
    }
};

// Validaciones
const validateEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
const validatePassword = (password) => password.length >= PASSWORD_MIN_LENGTH && /\d/.test(password);
const validatePhone = (phone) => /^\+?[\d\s-]+$/.test(phone);

// Manejo del formulario de registro
const registerForm = document.getElementById('registerForm');
if (registerForm) {
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const formData = {
            name: document.getElementById('name').value.trim(),
            email: document.getElementById('email').value.trim(),
            phone: document.getElementById('phone').value.trim(),
            password: document.getElementById('password').value
        };
        
        // Validación de campos
        let isValid = true;
        
        if (!formData.name) {
            showError('name', 'Full name is required');
            isValid = false;
        } else {
            clearError('name');
        }
        
        if (!formData.email) {
            showError('email', 'Email is required');
            isValid = false;
        } else if (!validateEmail(formData.email)) {
            showError('email', 'Invalid email format');
            isValid = false;
        } else {
            clearError('email');
        }
        
        if (!formData.phone) {
            showError('phone', 'Phone number is required');
            isValid = false;
        } else if (!validatePhone(formData.phone)) {
            showError('phone', 'Invalid phone number');
            isValid = false;
        } else {
            clearError('phone');
        }
        
        if (!formData.password) {
            showError('password', 'Password is required');
            isValid = false;
        } else if (!validatePassword(formData.password)) {
            showError('password', `Password must be at least ${PASSWORD_MIN_LENGTH} characters and contain numbers`);
            isValid = false;
        } else {
            clearError('password');
        }
        
        if (!isValid) return;
        
        // Estado de carga del botón
        const submitBtn = registerForm.querySelector('button[type="submit"]');
        const originalText = submitBtn.innerHTML;
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Registering...';
        
        try {
            // Modo desarrollo (mock)
            if (API_BASE_URL.includes('localhost')) {
                console.log('[DEV] Mock registration request:', formData);
                await new Promise(resolve => setTimeout(resolve, 1500));
                
                // Simular respuesta exitosa
                alert('Registration successful! You can now login.');
                window.location.href = 'login.html';
                return;
            }
            
            // Enviar datos al backend
            const response = await fetch(`${API_BASE_URL}/register`, {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });
            
            if (response.ok) {
                // Registro exitoso
                alert('Registration successful! You can now login.');
                window.location.href = 'login.html';
            } else {
                // Manejo de errores del servidor
                const errorData = await response.json();
                const errorMessage = errorData.message || 'Registration failed';
                
                if (response.status === 409) { // Conflicto (usuario ya existe)
                    showError('email', errorMessage);
                } else {
                    showError('email', errorMessage);
                }
            }
        } catch (error) {
            // Error de conexión
            console.error('Registration error:', error);
            showError('email', 'Unable to connect to server. Please try again later.');
        } finally {
            // Restaurar estado del botón
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalText;
        }
    });
}

// Validación en tiempo real
document.getElementById('email')?.addEventListener('blur', (e) => {
    const email = e.target.value.trim();
    if (email && !validateEmail(email)) {
        showError('email', 'Enter a valid email');
    } else {
        clearError('email');
    }
});

document.getElementById('phone')?.addEventListener('blur', (e) => {
    const phone = e.target.value.trim();
    if (phone && !validatePhone(phone)) {
        showError('phone', 'Enter a valid phone number');
    } else {
        clearError('phone');
    }
});

document.getElementById('password')?.addEventListener('blur', (e) => {
    const password = e.target.value;
    if (password && !validatePassword(password)) {
        showError('password', `Password must be at least ${PASSWORD_MIN_LENGTH} characters and contain numbers`);
    } else {
        clearError('password');
    }
});