import React, { useEffect, useState } from 'react';
import { Container, Nav, Navbar, Dropdown } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { getProfile } from '../api/userAPI';

const DashboardNav = () => {
  const [profile, setProfile] = useState({});
  const navigate = useNavigate();

  const fetchProfile = async () => {
    try {
      const response = await getProfile();
      setProfile(response.data.data);
    } catch (error) {
      toast.error(error.response?.data?.message || error.message || 'Failed to fetch profile');
    }
  };

  useEffect(() => {
    fetchProfile();
  }, []);

  const logout = () => {
    localStorage.removeItem('token');
    toast.success('Logout successful!');
    navigate('/');
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg" className="mb-0">
      <Container>
        <Navbar.Brand as={Link} to="/dashboard">
          Admin Dashboard
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto">
            <Dropdown align="end">
              <Dropdown.Toggle variant="dark" id="dropdown-basic">
                {profile?.username || 'No User'}
              </Dropdown.Toggle>
              <Dropdown.Menu>
                <Dropdown.Item disabled>Admin</Dropdown.Item>
                <Dropdown.Divider />
                <Dropdown.Item onClick={logout}>Logout</Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default DashboardNav;
