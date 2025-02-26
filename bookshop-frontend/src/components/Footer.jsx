import React from 'react';
import { Navbar, Container } from 'react-bootstrap';

const Footer = () => {
  return (
    <Navbar bg="light" variant="light" className="mt-4">
      <Container>
        <div className="text-center w-100">
          © {new Date().getFullYear()} Book Shop. All rights reserved.
        </div>
      </Container>
    </Navbar>
  );
};

export default Footer;