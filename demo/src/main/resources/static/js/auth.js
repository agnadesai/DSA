// Check if user is authenticated
function isAuthenticated() {
    return localStorage.getItem('jwtToken') !== null;
}

// Get the JWT token
function getToken() {
    return localStorage.getItem('jwtToken');
}

// Add JWT token to request headers
function getAuthHeaders() {
    const token = getToken();
    return {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };
}

// Handle API calls with authentication
async function authenticatedFetch(url, options = {}) {
    if (!isAuthenticated()) {
        window.location.href = '/login';
        return;
    }

    const headers = getAuthHeaders();
    const response = await fetch(url, {
        ...options,
        headers: {
            ...headers,
            ...(options.headers || {})
        }
    });

    if (response.status === 401 || response.status === 403) {
        // Token might be expired or invalid
        localStorage.clear();
        window.location.href = '/login';
        return;
    }

    return response;
}

// Logout function
function logout() {
    localStorage.clear();
    window.location.href = '/login';
}
