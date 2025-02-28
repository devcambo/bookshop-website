import React, { useState, useEffect } from 'react';
import { Table, Button, Pagination, Modal, Form } from 'react-bootstrap';
import { addBook, getBooks, updateBook, deleteBook } from '../api/bookAPI';
import { toast } from 'react-toastify';
import { uploadFile } from '../api/fileAPI';

const ManageBooks = () => {
  const [books, setBooks] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  const pageSize = 10;
  const fileBaseUrl = import.meta.env.VITE_FILE_URL;

  const [showModal, setShowModal] = useState(false);
  const [newBook, setNewBook] = useState({
    title: '',
    author: '',
    publisher: '',
    publicationDate: '',
    isbn: '',
    price: '',
    description: '',
    imageUrl: '',
  });
  const [newBookFile, setNewBookFile] = useState(null);

  const [showEditModal, setShowEditModal] = useState(false);
  const [editBook, setEditBook] = useState({
    id: null,
    title: '',
    author: '',
    publisher: '',
    publicationDate: '',
    isbn: '',
    price: '',
    description: '',
    imageUrl: '',
  });
  const [editBookFile, setEditBookFile] = useState(null);

  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [deleteBookId, setDeleteBookId] = useState(null);

  const fetchBooks = async (page) => {
    setLoading(true);
    try {
      const response = await getBooks(page, pageSize, 'id', 'DESC');
      const { data } = response.data;
      setBooks(data.content);
      setTotalPages(data.page.totalPages);
    } catch (error) {
      toast.error(error.response?.data?.message || error.message || 'Failed to fetch books');
    } finally {
      setLoading(false);
    }
  };

  // Add a new book
  const handleAddBook = async (e) => {
    e.preventDefault();
    try {
      let imageUrl = newBook.imageUrl;
      if (newBookFile) {
        const uploadResponse = await uploadFile(newBookFile);
        imageUrl = uploadResponse.data.data; // Adjust based on your API response
      }
      const bookData = { ...newBook, imageUrl };
      const response = await addBook(bookData);
      await fetchBooks(currentPage);
      toast.success(response.data.message || 'Book added successfully');
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to add book');
    } finally {
      setShowModal(false);
      setNewBook({
        title: '',
        author: '',
        publisher: '',
        publicationDate: '',
        isbn: '',
        price: '',
        description: '',
        imageUrl: '',
      });
      setNewBookFile(null); // Reset file
    }
  };

  // Edit an existing book
  const handleEditBook = async (e) => {
    e.preventDefault();
    try {
      let imageUrl = editBook.imageUrl;
      if (editBookFile) {
        const uploadResponse = await uploadFile(editBookFile);
        imageUrl = uploadResponse.data.data; // Adjust based on your API response
      }
      const bookData = { ...editBook, imageUrl };
      const response = await updateBook(editBook.id, bookData);
      await fetchBooks(currentPage);
      toast.success(response.data.message || 'Book updated successfully');
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to update book');
    } finally {
      setShowEditModal(false);
      setEditBook({
        id: null,
        title: '',
        author: '',
        publisher: '',
        publicationDate: '',
        isbn: '',
        price: '',
        description: '',
        imageUrl: '',
      });
      setEditBookFile(null); // Reset file
    }
  };

  // Delete a book
  const handleDeleteBook = async () => {
    try {
      const response = await deleteBook(deleteBookId);
      await fetchBooks(currentPage);
      toast.success(response.data.message || 'Book deleted successfully');
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to delete book');
    } finally {
      setShowDeleteModal(false);
      setDeleteBookId(null);
    }
  };

  // Fetch books when the page changes
  useEffect(() => {
    fetchBooks(currentPage);
  }, [currentPage]);

  // Handle pagination
  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setCurrentPage(newPage);
    }
  };

  // Handle form input changes
  const handleInputChange = (e, setBookFn) => {
    const { name, value } = e.target;
    setBookFn((prev) => ({ ...prev, [name]: value }));
  };

  // Handle file input changes
  const handleFileChange = (e, setFileFn, setBookFn) => {
    const file = e.target.files[0];
    if (file) {
      setFileFn(file);
      const previewUrl = URL.createObjectURL(file); // Preview locally
      setBookFn((prev) => ({ ...prev, imageUrl: previewUrl }));
    }
  };

  // Open edit modal with book data
  const openEditModal = (book) => {
    setEditBook({ ...book });
    setShowEditModal(true);
  };

  // Open delete confirmation modal
  const openDeleteModal = (bookId) => {
    setDeleteBookId(bookId);
    setShowDeleteModal(true);
  };

  return (
    <div>
      <h3>Manage Books</h3>
      <Button variant="primary" className="mb-3" onClick={() => setShowModal(true)}>
        Add New Book
      </Button>

      {loading ? (
        <p>Loading books...</p>
      ) : (
        <>
          <Table striped bordered hover responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Publisher</th>
                <th>Publication Date</th>
                <th>ISBN</th>
                <th>Price</th>
                <th>Description</th>
                <th>Image</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {books.length > 0 ? (
                books.map((book) => (
                  <tr key={book.id}>
                    <td>{book.id}</td>
                    <td>{book.title}</td>
                    <td>{book.author}</td>
                    <td>{book.publisher}</td>
                    <td>{book.publicationDate}</td>
                    <td>{book.isbn}</td>
                    <td>${book.price}</td>
                    <td>{book.description.substring(0, 10) + '...'}</td>
                    <td>
                      <img
                        src={`${fileBaseUrl}/${book.imageUrl}`}
                        alt={book.title}
                        style={{ width: '50px' }}
                      />
                    </td>
                    <td>
                      <Button
                        variant="outline-primary"
                        size="sm"
                        className="me-2"
                        onClick={() => openEditModal(book)}
                      >
                        Edit
                      </Button>
                      <Button
                        variant="outline-danger"
                        size="sm"
                        onClick={() => openDeleteModal(book.id)}
                      >
                        Delete
                      </Button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="10" className="text-center">
                    No books found
                  </td>
                </tr>
              )}
            </tbody>
          </Table>

          {totalPages > 1 && (
            <Pagination className="justify-content-center mt-3">
              <Pagination.First onClick={() => handlePageChange(0)} disabled={currentPage === 0} />
              <Pagination.Prev
                onClick={() => handlePageChange(currentPage - 1)}
                disabled={currentPage === 0}
              />
              {[...Array(totalPages).keys()].map((page) => (
                <Pagination.Item
                  key={page}
                  active={currentPage === page}
                  onClick={() => handlePageChange(page)}
                >
                  {page + 1}
                </Pagination.Item>
              ))}
              <Pagination.Next
                onClick={() => handlePageChange(currentPage + 1)}
                disabled={currentPage === totalPages - 1}
              />
              <Pagination.Last
                onClick={() => handlePageChange(totalPages - 1)}
                disabled={currentPage === totalPages - 1}
              />
            </Pagination>
          )}
        </>
      )}

      {/* Add Book Modal */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add New Book</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleAddBook}>
            <Form.Group className="mb-3" controlId="title">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                name="title"
                value={newBook.title}
                onChange={(e) => handleInputChange(e, setNewBook)}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="author">
              <Form.Label>Author</Form.Label>
              <Form.Control
                type="text"
                name="author"
                value={newBook.author}
                onChange={(e) => handleInputChange(e, setNewBook)}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="publisher">
              <Form.Label>Publisher</Form.Label>
              <Form.Control
                type="text"
                name="publisher"
                value={newBook.publisher}
                onChange={(e) => handleInputChange(e, setNewBook)}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="publicationDate">
              <Form.Label>Publication Date</Form.Label>
              <Form.Control
                type="date"
                name="publicationDate"
                value={newBook.publicationDate}
                onChange={(e) => handleInputChange(e, setNewBook)}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="isbn">
              <Form.Label>ISBN</Form.Label>
              <Form.Control
                type="text"
                name="isbn"
                value={newBook.isbn}
                onChange={(e) => handleInputChange(e, setNewBook)}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="price">
              <Form.Label>Price</Form.Label>
              <Form.Control
                type="number"
                step="0.01"
                name="price"
                value={newBook.price}
                onChange={(e) => handleInputChange(e, setNewBook)}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="description">
              <Form.Label>Description</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                name="description"
                value={newBook.description}
                onChange={(e) => handleInputChange(e, setNewBook)}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="imageFile">
              <Form.Label>Book Image</Form.Label>
              <Form.Control
                type="file"
                accept="image/*"
                onChange={(e) => handleFileChange(e, setNewBookFile, setNewBook)}
                required={!newBook.imageUrl} // Required if no image yet
              />
              {newBook.imageUrl && (
                <img
                  src={newBook.imageUrl}
                  alt="Preview"
                  style={{ width: '100px', marginTop: '10px' }}
                />
              )}
            </Form.Group>
            <Button variant="primary" type="submit">
              Save Book
            </Button>
          </Form>
        </Modal.Body>
      </Modal>

      {/* Edit Book Modal */}
      <Modal show={showEditModal} onHide={() => setShowEditModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Book</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {editBook && (
            <Form onSubmit={handleEditBook}>
              <Form.Group className="mb-3" controlId="edit-title">
                <Form.Label>Title</Form.Label>
                <Form.Control
                  type="text"
                  name="title"
                  value={editBook.title}
                  onChange={(e) => handleInputChange(e, setEditBook)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="edit-author">
                <Form.Label>Author</Form.Label>
                <Form.Control
                  type="text"
                  name="author"
                  value={editBook.author}
                  onChange={(e) => handleInputChange(e, setEditBook)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="edit-publisher">
                <Form.Label>Publisher</Form.Label>
                <Form.Control
                  type="text"
                  name="publisher"
                  value={editBook.publisher}
                  onChange={(e) => handleInputChange(e, setEditBook)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="edit-publicationDate">
                <Form.Label>Publication Date</Form.Label>
                <Form.Control
                  type="date"
                  name="publicationDate"
                  value={editBook.publicationDate}
                  onChange={(e) => handleInputChange(e, setEditBook)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="edit-isbn">
                <Form.Label>ISBN</Form.Label>
                <Form.Control
                  type="text"
                  name="isbn"
                  value={editBook.isbn}
                  onChange={(e) => handleInputChange(e, setEditBook)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="edit-price">
                <Form.Label>Price</Form.Label>
                <Form.Control
                  type="number"
                  step="0.01"
                  name="price"
                  value={editBook.price}
                  onChange={(e) => handleInputChange(e, setEditBook)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="edit-description">
                <Form.Label>Description</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={3}
                  name="description"
                  value={editBook.description}
                  onChange={(e) => handleInputChange(e, setEditBook)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="edit-imageFile">
                <Form.Label>Book Image</Form.Label>
                <Form.Control
                  type="file"
                  accept="image/*"
                  onChange={(e) => handleFileChange(e, setEditBookFile, setEditBook)}
                />
                {editBook.imageUrl && (
                  <img
                    src={`${fileBaseUrl}/${editBook.imageUrl}`}
                    alt="Preview"
                    style={{ width: '100px', marginTop: '10px' }}
                  />
                )}
              </Form.Group>
              <Button variant="primary" type="submit">
                Update Book
              </Button>
            </Form>
          )}
        </Modal.Body>
      </Modal>

      {/* Delete Book Confirmation Modal */}
      <Modal show={showDeleteModal} onHide={() => setShowDeleteModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Deletion</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Are you sure you want to delete this book? This action cannot be undone.
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowDeleteModal(false)}>
            Cancel
          </Button>
          <Button variant="danger" onClick={handleDeleteBook}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default ManageBooks;
