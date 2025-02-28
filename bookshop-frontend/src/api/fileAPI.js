import api from './axiosInstance';

export const uploadFile = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/files', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
      accept: 'application/json',
    },
  });
};
