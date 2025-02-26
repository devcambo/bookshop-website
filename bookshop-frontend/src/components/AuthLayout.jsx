import React from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { Container, Button } from 'react-bootstrap';

const AuthLayout = () => {
  const navigate = useNavigate();

  const handleBackClick = () => {
    navigate('/');
  };

  return (
    <Container className="min-vh-100 d-flex flex-column justify-content-center py-4">
      <div>
        <Button variant="outline-primary" onClick={handleBackClick}>
          ← Back to Homepage
        </Button>
      </div>
      <Outlet />
    </Container>
  );
};

export default AuthLayout;
