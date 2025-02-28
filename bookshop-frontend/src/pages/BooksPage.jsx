import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Pagination } from 'react-bootstrap';
import { getBooks } from '../api/bookAPI';
import { toast } from 'react-toastify';
import BookCard from '../components/BookCard';

const BookPage = () => {
  const [books, setBooks] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  const pageSize = 10;
  const fileBaseUrl = import.meta.env.VITE_FILE_URL;

  // Fetch books from API
  const fetchBooks = async (page) => {
    setLoading(true);
    try {
      const response = await getBooks(page, pageSize, 'id', 'DESC');
      const { data } = response.data;
      setBooks(data.content);
      setTotalPages(data.page.totalPages);
    } catch (error) {
      toast.error('Failed to load books');
    } finally {
      setLoading(false);
    }
  };

  // Load books when page changes
  useEffect(() => {
    fetchBooks(currentPage);
  }, [currentPage]);

  // Handle pagination
  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setCurrentPage(newPage);
    }
  };

  return (
    <Container className="my-4">
      <h2>Available Books</h2>
      {loading ? (
        <p>Loading books...</p>
      ) : (
        <>
          <Row xs={1} md={2} lg={3} className="g-4">
            {books.length > 0 ? (
              books.map((book) => (
                <Col key={book.id}>
                  <BookCard book={book} fileBaseUrl={fileBaseUrl} />
                </Col>
              ))
            ) : (
              <Col>
                <p className="text-center">No books available</p>
              </Col>
            )}
          </Row>

          {totalPages > 1 && (
            <Pagination className="justify-content-center mt-4">
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
    </Container>
  );
};

export default BookPage;
