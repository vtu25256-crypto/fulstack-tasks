document.addEventListener('DOMContentLoaded', () => {
    const feedbackForm = document.getElementById('feedbackForm');
    const nameInput = document.getElementById('name');
    const emailInput = document.getElementById('email');
    const messageInput = document.getElementById('message');
    const submitBtn = document.getElementById('submitBtn');
    const overlay = document.getElementById('confirmationOverlay');
    const closeModal = document.getElementById('closeModal');

    // 1. REUSABLE VALIDATION FUNCTIONS
    const validators = {
        name: (val) => val.trim().length >= 3,
        email: (val) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(val),
        message: (val) => val.trim().length >= 10
    };

    const updateStatus = (input, fieldName) => {
        const wrapper = input.closest('.input-wrapper');
        const isValid = validators[fieldName](input.value);
        
        if (input.value.length === 0) {
            wrapper.classList.remove('valid', 'invalid');
        } else if (isValid) {
            wrapper.classList.add('valid');
            wrapper.classList.remove('invalid');
        } else {
            wrapper.classList.add('invalid');
            wrapper.classList.remove('valid');
        }
        checkOverallForm();
    };

    const checkOverallForm = () => {
        const isNameOk = validators.name(nameInput.value);
        const isEmailOk = validators.email(emailInput.value);
        const isMsgOk = validators.message(messageInput.value);
        
        if (isNameOk && isEmailOk && isMsgOk) {
            submitBtn.disabled = false;
            submitBtn.textContent = 'Double-Click to Submit';
        } else {
            submitBtn.disabled = true;
            submitBtn.textContent = 'Ready for Submit';
        }
    };

    // 2. KEYPRESS / KEYUP VALIDATION
    nameInput.addEventListener('keyup', () => updateStatus(nameInput, 'name'));
    emailInput.addEventListener('keyup', () => updateStatus(emailInput, 'email'));
    messageInput.addEventListener('keyup', () => updateStatus(messageInput, 'message'));

    // 3. MOUSE HIGHLIGHT EVENTS
    const wrappers = document.querySelectorAll('.input-wrapper');
    wrappers.forEach(wrapper => {
        wrapper.addEventListener('mouseover', () => wrapper.classList.add('highlight'));
        wrapper.addEventListener('mouseout', () => wrapper.classList.remove('highlight'));
    });

    // 4. DOUBLE-CLICK SUBMIT
    submitBtn.addEventListener('dblclick', () => {
        overlay.classList.add('visible');
    });

    // Single click feedback (prevents confusion)
    submitBtn.addEventListener('click', (e) => {
        if (!submitBtn.disabled) {
            submitBtn.classList.add('shake');
            setTimeout(() => submitBtn.classList.remove('shake'), 400);
        }
    });

    closeModal.addEventListener('click', () => {
        overlay.classList.remove('visible');
        feedbackForm.reset();
        wrappers.forEach(w => w.classList.remove('valid', 'invalid'));
        checkOverallForm();
    });
});
