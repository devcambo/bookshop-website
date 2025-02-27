import React, { useState } from 'react';
import { Container, Form, Button, Card } from 'react-bootstrap';
import { register } from '../api/authAPI';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

const RegisterPage = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    gender: 'OTHER',
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await register(formData);
      localStorage.setItem('token', response.data.data.jwt_token);
      toast.success(response.data.message || 'Registration successful!');
      navigate('/');
    } catch (error) {
      toast.error(error.response?.data?.message || error.message || 'Registration failed');
    } finally {
      clearForm();
    }
  };

  const clearForm = () => {
    setFormData({
      username: '',
      email: '',
      password: '',
      gender: 'OTHER',
    });
  };

  return (
    <Container className="d-flex justify-content-center align-items-center min-vh-100">
      <Card style={{ width: '25rem' }} className="p-4 shadow">
        <Card.Body>
          <h2 className="text-center mb-4">Register</h2>
          <Form onSubmit={handleSubmit}>
            {/* Username Field */}
            <Form.Group className="mb-3" controlId="formUsername">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                name="username"
                value={formData.username}
                onChange={handleChange}
                placeholder="johndoe"
                required
              />
            </Form.Group>

            {/* Email Field */}
            <Form.Group className="mb-3" controlId="formEmail">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="example@example.com"
                required
              />
            </Form.Group>

            {/* Password Field */}
            <Form.Group className="mb-3" controlId="formPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="password"
                required
              />
            </Form.Group>

            {/* Gender Field */}
            <Form.Group className="mb-3" controlId="formGender">
              <Form.Label>Gender</Form.Label>
              <Form.Select name="gender" value={formData.gender} onChange={handleChange} required>
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="OTHER">Other</option>
              </Form.Select>
            </Form.Group>

            {/* Submit Button */}
            <Button variant="primary" type="submit" className="w-100">
              Register
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default RegisterPage;
