import React, { useEffect, useState } from 'react';
import { Container, Card, ListGroup, Button, Spinner } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { getProfile } from '../api/userAPI';

const Settings = () => {
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const fetchProfile = async () => {
    const token = localStorage.getItem('token');
    if (!token) {
      toast.error('Please log in to view settings');
      navigate('/login');
      return;
    }

    try {
      const response = await getProfile();
      setProfile(response.data.data);
    } catch (error) {
      toast.error(error.response?.data?.message || error.message || 'Failed to fetch profile');
      if (error.response?.status === 401 || error.response?.status === 403) {
        localStorage.removeItem('token');
        navigate('/login');
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProfile();
  }, []);

  const handleEditProfile = () => {
    // Placeholder for future edit functionality
    toast.info('Edit profile feature coming soon!');
  };

  if (loading) {
    return (
      <Container className="d-flex justify-content-center align-items-center min-vh-100">
        <Spinner animation="border" variant="primary" />
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <h3 className="mb-4">Settings</h3>
      {profile ? (
        <Card className="shadow">
          <Card.Header>
            <h4>User Profile</h4>
          </Card.Header>
          <Card.Body>
            <ListGroup variant="flush">
              <ListGroup.Item>
                <strong>User ID:</strong> {profile.userId}
              </ListGroup.Item>
              <ListGroup.Item>
                <strong>Username:</strong> {profile.username}
              </ListGroup.Item>
              <ListGroup.Item>
                <strong>Email:</strong> {profile.email}
              </ListGroup.Item>
              <ListGroup.Item>
                <strong>Role:</strong> {profile.roles}
              </ListGroup.Item>
              <ListGroup.Item>
                <strong>Gender:</strong> {profile.gender}
              </ListGroup.Item>
            </ListGroup>
          </Card.Body>
          <Card.Footer className="text-end">
            <Button variant="primary" onClick={handleEditProfile}>
              Edit Profile
            </Button>
          </Card.Footer>
        </Card>
      ) : (
        <p>No profile data available. Please try again later.</p>
      )}
    </Container>
  );
};

export default Settings;
