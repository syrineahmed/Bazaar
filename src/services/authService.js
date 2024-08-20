// authService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/auth/signup';

const signup = (userData) => {
    return axios.post(API_URL, userData);
};

export default {
    signup
};
