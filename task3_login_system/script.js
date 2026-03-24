document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const userError = document.getElementById('userError');
    const passError = document.getElementById('passError');
    const apiResponse = document.getElementById('apiResponse');
    const loginBtn = document.getElementById('loginBtn');

    const validate = () => {
        let isValid = true;
        
        // Username validation
        if (usernameInput.value.trim().length < 3) {
            userError.textContent = 'Username must be at least 3 characters';
            isValid = false;
        } else {
            userError.textContent = '';
        }

        // Password validation
        if (passwordInput.value.length < 6) {
            passError.textContent = 'Password must be at least 6 characters';
            isValid = false;
        } else {
            passError.textContent = '';
        }

        return isValid;
    };

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        if (!validate()) return;

        // Visual feedback
        loginBtn.disabled = true;
        loginBtn.querySelector('.btn-text').textContent = 'Authenticating...';
        apiResponse.className = 'response-msg';
        apiResponse.style.display = 'none';

        const data = {
            username: usernameInput.value,
            password: passwordInput.value
        };

        try {
            const response = await fetch('auth.php', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            const result = await response.json();

            if (response.ok && result.status === 'success') {
                apiResponse.textContent = result.message;
                apiResponse.className = 'response-msg success';
                loginBtn.style.background = '#10b981';
                loginBtn.querySelector('.btn-text').textContent = 'Success!';
            } else {
                apiResponse.textContent = result.message || 'Login failed';
                apiResponse.className = 'response-msg error';
                loginBtn.disabled = false;
                loginBtn.querySelector('.btn-text').textContent = 'Sign In';
            }
        } catch (error) {
            apiResponse.textContent = 'Connection error. Please try again.';
            apiResponse.className = 'response-msg error';
            loginBtn.disabled = false;
            loginBtn.querySelector('.btn-text').textContent = 'Sign In';
        }
    });

    // Real-time validation
    usernameInput.addEventListener('input', () => userError.textContent = '');
    passwordInput.addEventListener('input', () => passError.textContent = '');
});
