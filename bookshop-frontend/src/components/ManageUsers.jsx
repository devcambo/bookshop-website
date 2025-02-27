import React, { useState, useEffect } from 'react';
import { Table, Button, Pagination, Modal, Form } from 'react-bootstrap';
import { addUser, getUsers, updateUser, deleteUser } from '../api/userAPI';
import { toast } from 'react-toastify';

const ManageUsers = () => {
  const [users, setUsers] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  const pageSize = 10;
  const [showModal, setShowModal] = useState(false);
  const [newUser, setNewUser] = useState({ username: '', email: '', password: '', gender: '' });
  const [showEditModal, setShowEditModal] = useState(false);
  const [editUser, setEditUser] = useState({ username: '', email: '', gender: '' });
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [deleteUserId, setDeleteUserId] = useState(null);

  const fetchUsers = async (page) => {
    setLoading(true);
    try {
      const response = await getUsers(page, pageSize, 'userId', 'DESC');
      const { data } = response.data;
      setUsers(data.content);
      setTotalPages(data.page.totalPages);
    } catch (error) {
      toast.error(error.response?.data?.message || error.message || 'Failed to fetch users');
    } finally {
      setLoading(false);
    }
  };

  const handleAddUser = async (e) => {
    e.preventDefault();
    try {
      const response = await addUser(newUser);
      await fetchUsers(currentPage);
      toast.success(response.data.message || 'User added successfully');
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to add user');
    } finally {
      setShowModal(false);
      setNewUser({ username: '', email: '', password: '', gender: '' });
    }
  };

  const handleEditUser = async (e) => {
    e.preventDefault();
    try {
      const resposne = await updateUser(editUser.userId, editUser);
      await fetchUsers(currentPage);
      toast.success(resposne.data.message || 'User updated successfully');
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to update user');
    } finally {
      setShowEditModal(false);
      setEditUser({ username: '', email: '', gender: '' });
    }
  };

  const handleDeleteUser = async () => {
    try {
      const resposne = await deleteUser(deleteUserId);
      await fetchUsers(currentPage);
      toast.success(resposne.data.message || 'User deleted successfully');
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to delete user');
    } finally {
      setShowDeleteModal(false);
      setDeleteUserId(null);
    }
  };

  useEffect(() => {
    fetchUsers(currentPage);
  }, [currentPage]);

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setCurrentPage(newPage);
    }
  };

  const handleInputChange = (e, setUserFn) => {
    const { name, value } = e.target;
    setUserFn((prev) => ({ ...prev, [name]: value }));
  };

  const openEditModal = (user) => {
    setEditUser({ ...user });
    setShowEditModal(true);
  };

  const openDeleteModal = (userId) => {
    setDeleteUserId(userId);
    setShowDeleteModal(true);
  };

  return (
    <div>
      <h3>Manage Users</h3>
      <Button variant="primary" className="mb-3" onClick={() => setShowModal(true)}>
        Add New User
      </Button>
      {loading ? (
        <p>Loading users...</p>
      ) : (
        <>
          <Table striped bordered hover responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
                <th>Gender</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {users.length > 0 ? (
                users.map((user) => (
                  <tr key={user.userId}>
                    <td>{user.userId}</td>
                    <td>{user.username}</td>
                    <td>{user.email}</td>
                    <td>{user.roles}</td>
                    <td>{user.gender}</td>
                    <td>
                      <Button
                        variant="outline-primary"
                        size="sm"
                        className="me-2"
                        onClick={() => openEditModal(user)}
                      >
                        Edit
                      </Button>
                      <Button
                        variant="outline-danger"
                        size="sm"
                        onClick={() => openDeleteModal(user.userId)}
                      >
                        Delete
                      </Button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5" className="text-center">
                    No users found
                  </td>
                </tr>
              )}
            </tbody>
          </Table>

          {/* Pagination Controls */}
          {totalPages > 1 && (
            <Pagination className="justify-content-center mt-3">
              <Pagination.First onClick={() => handlePageChange(0)} disabled={currentPage === 0} />
              <Pagination.Prev
                onClick={() => handlePageChange(currentPage - 1)}
                disabled={currentPage === 0}
              />
              {[...Array(totalPages).keys()].map((page) => (
                <Pagination.Item
                  key={page}
                  active={currentPage === page}
                  onClick={() => handlePageChange(page)}
                >
                  {page + 1} {/* Display as 1, 2 for users */}
                </Pagination.Item>
              ))}
              <Pagination.Next
                onClick={() => handlePageChange(currentPage + 1)}
                disabled={currentPage === totalPages - 1}
              />
              <Pagination.Last
                onClick={() => handlePageChange(totalPages - 1)}
                disabled={currentPage === totalPages - 1}
              />
            </Pagination>
          )}
        </>
      )}

      {/* Add User Modal */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add New User</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleAddUser}>
            <Form.Group className="mb-3" controlId="username">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                name="username"
                value={newUser.username}
                onChange={(e) => handleInputChange(e, setNewUser)}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="email">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                name="email"
                value={newUser.email}
                onChange={(e) => handleInputChange(e, setNewUser)}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="password">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                name="password"
                value={newUser.password}
                onChange={(e) => handleInputChange(e, setNewUser)}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="gender">
              <Form.Label>Gender</Form.Label>
              <Form.Select
                name="gender"
                value={newUser.gender}
                onChange={(e) => handleInputChange(e, setNewUser)}
                required
              >
                <option value="">Select Gender</option>
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="OTHER">Other</option>
              </Form.Select>
            </Form.Group>
            <Button variant="primary" type="submit">
              Save User
            </Button>
          </Form>
        </Modal.Body>
      </Modal>

      {/* Edit User Modal */}
      <Modal show={showEditModal} onHide={() => setShowEditModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Edit User</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {editUser && (
            <Form onSubmit={handleEditUser}>
              <Form.Group className="mb-3" controlId="edit-username">
                <Form.Label>Username</Form.Label>
                <Form.Control
                  type="text"
                  name="username"
                  value={editUser.username}
                  onChange={(e) => handleInputChange(e, setEditUser)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="edit-email">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  name="email"
                  value={editUser.email}
                  onChange={(e) => handleInputChange(e, setEditUser)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="edit-gender">
                <Form.Label>Gender</Form.Label>
                <Form.Select
                  name="gender"
                  value={editUser.gender}
                  onChange={(e) => handleInputChange(e, setEditUser)}
                  required
                >
                  <option value="">Select Gender</option>
                  <option value="MALE">Male</option>
                  <option value="FEMALE">Female</option>
                  <option value="OTHER">Other</option>
                </Form.Select>
              </Form.Group>
              <Button variant="primary" type="submit">
                Update User
              </Button>
            </Form>
          )}
        </Modal.Body>
      </Modal>
      
      {/* Delete User Confirmation Modal */}
      <Modal show={showDeleteModal} onHide={() => setShowDeleteModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Deletion</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Are you sure you want to delete this user? This action cannot be undone.
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowDeleteModal(false)}>
            Cancel
          </Button>
          <Button variant="danger" onClick={handleDeleteUser}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default ManageUsers;
