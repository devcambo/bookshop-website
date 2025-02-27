import React from 'react';
import { Col, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Sidebar = () => {
  return (
    <Col md={3} lg={2} className="bg-light p-0" style={{ minHeight: 'calc(100vh - 56px)' }}>
      <Nav className="flex-column p-3">
        <Nav.Link as={Link} to="/dashboard/users" className="mb-2">
          Manage Users
        </Nav.Link>
        <Nav.Link as={Link} to="/dashboard/books" className="mb-2">
          Manage Books
        </Nav.Link>
        <Nav.Link as={Link} to="/dashboard/settings" className="mb-2">
          Settings
        </Nav.Link>
      </Nav>
    </Col>
  );
};

export default Sidebar;
