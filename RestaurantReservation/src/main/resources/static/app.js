async function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const response = await fetch(`/users/${username}`);
    const user = await response.json();

    if (user && user.password === password) {
        window.location.href = "dashboard.html";
    } else {
        document.getElementById("message").innerText = "Usuario o contraseña incorrectos";
    }
}