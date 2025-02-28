import api from './axiosInstance';

export const getBooks = (page = 0, pageSize = 10, sortBy = 'id', sortDir = 'DESC') => {
  return api.get('/books', {
    params: {
      offset: page,
      pageSize,
      sortBy,
      sortDir,
    },
  });
};
export const getBook = (bookId) => api.get(`/books/${bookId}`);
export const addBook = (bookData) => api.post('/books', bookData);
export const updateBook = (bookId, bookData) => api.put(`/books/${bookId}`, bookData);
export const deleteBook = (bookId) => api.delete(`/books/${bookId}`);
