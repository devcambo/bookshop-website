// BookDetailPage.jsx
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { getBook } from '../api/bookAPI'; // Adjust path
import { toast } from 'react-toastify';

const BookPage = () => {
  const { id } = useParams(); // Get book ID from URL
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);

  // Fetch book details
  const fetchBook = async () => {
    setLoading(true);
    try {
      const response = await getBook(id);
      setBook(response.data.data); // Adjust based on API response structure
    } catch (error) {
      console.error('Failed to fetch book:', error.response?.data || error.message);
      toast.error('Failed to load book details');
    } finally {
      setLoading(false);
    }
  };

  // Load book on mount or ID change
  useEffect(() => {
    fetchBook();
  }, [id]);

  if (loading) {
    return (
      <Container className="my-4">
        <p>Loading book details...</p>
      </Container>
    );
  }

  if (!book) {
    return (
      <Container className="my-4">
        <p>Book not found</p>
      </Container>
    );
  }

  return (
    <Container className="my-4">
      <Row>
        <Col md={4}>
          <Card>
            <Card.Img
              variant="top"
              src={`${import.meta.env.VITE_FILE_URL}/${book.imageUrl}`}
              alt={book.title}
              style={{ height: '300px', objectFit: 'cover' }}
            />
          </Card>
        </Col>
        <Col md={8}>
          <h2>{book.title}</h2>
          <h4 className="text-muted">by {book.author}</h4>
          <p>
            <strong>Publisher:</strong> {book.publisher}
          </p>
          <p>
            <strong>Publication Date:</strong> {book.publicationDate}
          </p>
          <p>
            <strong>ISBN:</strong> {book.isbn}
          </p>
          <p>
            <strong>Price:</strong> ${parseFloat(book.price).toFixed(2)}
          </p>
          <p>
            <strong>Description:</strong> {book.description}
          </p>
          <Button variant="primary">Add to Cart</Button> {/* Placeholder */}
        </Col>
      </Row>
    </Container>
  );
};

export default BookPage;
