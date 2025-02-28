import api from './axiosInstance';

export const getUsers = (page = 0, pageSize = 10, sortBy = 'userId', sortDir = 'DESC') => {
  return api.get('/users', {
    params: {
      offset: page,
      pageSize,
      sortBy,
      sortDir,
    },
  });
};
export const getUser = (userId) => api.get(`/users/${userId}`);
export const addUser = (userData) => api.post('/users', userData);
export const updateUser = (userId, userData) => api.put(`/users/${userId}`, userData);
export const deleteUser = (userId) => api.delete(`/users/${userId}`);
export const getProfile = () => api.get('/users/profile');
