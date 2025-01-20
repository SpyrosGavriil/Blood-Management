document.addEventListener('DOMContentLoaded', () => {
    const loginSection = document.getElementById('login');
    const registerSection = document.getElementById('register');
    const showRegisterButton = document.getElementById('showRegister');
    const showLoginButton = document.getElementById('showLogin');

    // Toggle between login and register forms
    showRegisterButton.addEventListener('click', () => {
        loginSection.classList.add('hidden');
        registerSection.classList.remove('hidden');
    });

    showLoginButton.addEventListener('click', () => {
        registerSection.classList.add('hidden');
        loginSection.classList.remove('hidden');
    });

    // Handle Login
    document.getElementById('loginForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const username = document.getElementById('loginUsername').value;
        const password = document.getElementById('loginPassword').value;

        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (!response.ok) {
                throw new Error('Invalid login credentials');
            }

            const data = await response.json();
            
            if (!data.token) {
                throw new Error('Authentication failed');
            }

            const tokenPayload = parseJwt(data.token);
            if (tokenPayload && tokenPayload.role) {
                handleRedirect(tokenPayload.role);
            } else {
                throw new Error('Invalid user role');
            }
        } catch (error) {
            alert(error.message);
            document.getElementById('loginForm').reset();
        }
    });

    // Function to decode JWT token
    function parseJwt(token) {
        try {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            return JSON.parse(window.atob(base64));
        } catch (error) {
            return null;
        }
    }

    // Function to handle redirect based on role
    function handleRedirect(role) {
        switch (role) {
            case "ROLE_USER":
                window.location.href = '/user-dashboard.html';
                break;
            case "ROLE_ADMIN":
                window.location.href = '/admin-dashboard.html';
                break;
            default:
                throw new Error('Invalid user role');
        }
    }

    // Handle Registration
    document.getElementById('registerForm').addEventListener('submit', (event) => {
        event.preventDefault();

        const firstName = document.getElementById('registerFirstName').value;
        const lastName = document.getElementById('registerLastName').value;
        const username = document.getElementById('registerUsername').value;
        const password = document.getElementById('registerPassword').value;

        fetch('http://localhost:8080/api/auth/register/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ firstName, lastName, username, password }),
        })
        .then(response => {
            if (response.ok) {
                alert('Registration successful! You can now log in.');
                document.getElementById('registerForm').reset();
                // Switch back to login form
                registerSection.classList.add('hidden');
                loginSection.classList.remove('hidden');
            } else {
                throw new Error('Registration failed. Please try again.');
            }
        })
        .catch(error => {
            alert(error.message);
            document.getElementById('registerForm').reset();
        });
    });
});