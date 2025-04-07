// Función para manejar el login
// Función para manejar el login
// Función para manejar el login
async function loginUser(event) {
    event.preventDefault(); // Prevenir la recarga del formulario

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
    });

    if (response.ok) {
        const data = await response.json(); // Obtener la respuesta como JSON
        console.log('Respuesta recibida del servidor:', data);  // Verificar los datos

        const token = data.token;  // Token recibido
        const userType = data.userType;  // UserType recibido

        console.log('Login exitoso:', token, userType);

        // Guarda el token en localStorage
        localStorage.setItem('token', token);

        // Redirigir a la página correcta según el tipo de usuario
        if (userType === 'ADMIN') {
            window.location.href = '/admin.html';
        } else if (userType === 'CUSTOMER') {
            window.location.href = '/customer.html';
        } else {
            alert('Tipo de usuario desconocido');
        }
    } else {
        alert('Login failed!');
    }
}




// Función para manejar el registro
async function registerUser(event) {
    event.preventDefault(); // Prevenir la recarga del formulario

    const username = document.getElementById('username').value;  // Obtenemos el nombre de usuario
    const email = document.getElementById('email').value;
    const phone = document.getElementById('phone').value;
    const password = document.getElementById('password').value;
    const userType = document.getElementById('userType').value;  // Obtenemos el tipo de usuario

    const response = await fetch('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, email, phone, password, userType }) // Incluir los campos correctos
    });

    if (response.ok) {
        alert('Registration successful! Now you can login.');
        window.location.href = '/login.html';
    } else {
        alert('Registration failed!');
    }
}

// Función para comprobar si el usuario está autenticado
function checkAuthentication() {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = '/login.html';
    }
}

// Función para logout
function logout() {
    localStorage.removeItem('token');
    window.location.href = '/index.html';
}
