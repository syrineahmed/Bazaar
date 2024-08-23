
import axios from 'axios';

const API_URL = '/api/v1/user/profile';

export const getUserProfile = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error('Error fetching user profile:', error);
        throw error;
    }
};