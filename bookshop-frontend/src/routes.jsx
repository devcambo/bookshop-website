import { BrowserRouter, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import NotFoundPage from './pages/NotFoundPage';
import PrivateRoute from './components/PrivateRoute';
import MainLayout from './components/MainLayout';
import AuthLayout from './components/AuthLayout';
import DashboardLayout from './components/DashboardLayout';
import DashboardPage from './pages/DashboardPage';
import BooksPage from './pages/BooksPage';
import BookPage from './pages/BookPage';
import AboutPage from './pages/AboutPage';

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
            <Route element={<DashboardLayout />}>
              <Route path="/dashboard" element={<DashboardPage />} />
            </Route>
          </Route>

          {/* Page Not Found 404 */}
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default AppRoutes;
