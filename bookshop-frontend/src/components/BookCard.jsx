import React from 'react';
import { Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const BookCard = ({ book, fileBaseUrl }) => {
  return (
    <Card className="h-100">
      <Card.Img
        variant="top"
        src={`${fileBaseUrl}/${book.imageUrl}`}
        alt={book.title}
        style={{ height: '200px', objectFit: 'cover' }}
      />
      <Card.Body>
        <Card.Title>{book.title}</Card.Title>
        <Card.Subtitle className="mb-2 text-muted">by {book.author}</Card.Subtitle>
        <Card.Text>
          <strong>Price:</strong> ${parseFloat(book.price).toFixed(2)}
        </Card.Text>
        <Card.Text>
          {book.description.length > 100
            ? `${book.description.substring(0, 100)}...`
            : book.description}
        </Card.Text>
      </Card.Body>
      <Card.Footer className="text-center">
        <Button variant="primary" size="sm" as={Link} to={`/books/${book.id}`}>
          View Details
        </Button>
      </Card.Footer>
    </Card>
  );
};

export default BookCard;
