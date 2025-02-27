import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { Outlet } from 'react-router-dom';
import DashboardNav from '../components/DashboardNav';
import Sidebar from '../components/Sidebar';

const DashboardPage = () => {
  return (
    <Container fluid className="p-0">
      {/* Navbar */}
      <DashboardNav />

      {/* Main Layout */}
      <Row className="m-0">
        {/* Sidebar */}
        <Sidebar />

        {/* Content Area */}
        <Col md={9} lg={10} className="p-4">
          <Outlet /> {/* Renders nested routes */}
        </Col>
      </Row>
    </Container>
  );
};

export default DashboardPage;
