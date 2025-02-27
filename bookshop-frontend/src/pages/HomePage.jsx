import React from 'react';
import { Container, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const HomePage = () => {
  return (
    <div>
      {/* Hero Section */}
      <Container className="text-center py-5">
        <h1>Welcome to BookShop</h1>
        <p className="lead">Discover your next favorite book with us</p>
        <Button as={Link} to={'/books'} variant="primary" size="lg" className="mt-3">
          Shop Now
        </Button>
      </Container>
    </div>
  );
};

export default HomePage;
