import { BrowserRouter, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import NotFoundPage from './pages/NotFoundPage';
import PrivateRoute from './components/PrivateRoute';
import MainLayout from './components/MainLayout';
import AuthLayout from './components/AuthLayout';
import DashboardPage from './pages/DashboardPage';
import BooksPage from './pages/BooksPage';
import BookPage from './pages/BookPage';
import AboutPage from './pages/AboutPage';
import ManageUsers from './components/ManageUsers';
import ManageBooks from './components/ManageBooks';
import Settings from './components/Settings';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AppRoutes = () => {
  return (
    <>
      <BrowserRouter>
        <Routes>
          {/* MainLayout */}
          <Route element={<MainLayout />}>
            <Route index element={<HomePage />} />
            <Route path="/books" element={<BooksPage />} />
            <Route path="/books/:id" element={<BookPage />} />
            <Route path="/about" element={<AboutPage />} />
          </Route>

          {/* AuthLayout */}
          <Route element={<AuthLayout />}>
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/login" element={<LoginPage />} />
          </Route>

          {/* DashboardLayout && PrivateRoute */}
          <Route element={<PrivateRoute />}>
            <Route path="/dashboard" element={<DashboardPage />}>
              <Route index element={<ManageUsers />} />
              <Route path="users" element={<ManageUsers />} />
              <Route path="books" element={<ManageBooks />} />
              <Route path="settings" element={<Settings />} />
            </Route>
          </Route>

          {/* Page Not Found 404 */}
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </BrowserRouter>
      <ToastContainer />
    </>
  );
};

export default AppRoutes;
