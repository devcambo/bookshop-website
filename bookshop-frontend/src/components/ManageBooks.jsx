import React from 'react';

const ManageBooks = () => {
  const books = [
    { id: 1, title: 'Book One', author: 'Author A', price: 10.99 },
    { id: 2, title: 'Book Two', author: 'Author B', price: 15.99 },
  ];

  return (
    <div>
      <h3>Manage Books</h3>
      <table className="table table-striped table-bordered table-hover">
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {books.map((book) => (
            <tr key={book.id}>
              <td>{book.id}</td>
              <td>{book.title}</td>
              <td>{book.author}</td>
              <td>${book.price.toFixed(2)}</td>
              <td>
                <button className="btn btn-outline-primary btn-sm me-2">Edit</button>
                <button className="btn btn-outline-danger btn-sm">Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ManageBooks;
