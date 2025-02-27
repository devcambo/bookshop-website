import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const Header = () => {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem('token');
    toast.success('Logout successful!');
    navigate('/');
  };

  const isLoggedIn = () => {
    const token = localStorage.getItem('token');
    return token !== null;
  };

  return (
    <Navbar bg="light" expand="lg" className="mb-4">
      <Container>
        <Navbar.Brand as={Link} to={'/'}>
          Book Shop
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto" />
          <Nav className="me-auto">
            <Nav.Link as={Link} to={'/'}>
              Home
            </Nav.Link>
            <Nav.Link as={Link} to={'/books'}>
              Book
            </Nav.Link>
            <Nav.Link as={Link} to={'/about'}>
              About
            </Nav.Link>
          </Nav>
          <Nav>
            {isLoggedIn() ? (
              <>
                <Nav.Link as={Link} to={'/dashboard'}>
                  Dashboard
                </Nav.Link>
                <Nav.Link onClick={logout}>Logout</Nav.Link>
              </>
            ) : (
              <>
                <Nav.Link as={Link} to={'/login'}>
                  Login
                </Nav.Link>

                <Nav.Link as={Link} to={'/register'}>
                  Register
                </Nav.Link>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
