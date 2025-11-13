import React from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Login from './Login';
import PrivateRoute from './PrivateRoute';
import { logout, getToken } from './auth';

/**
 * App with protected home route and login flow.
 * Replace content and styling as needed.
 */
function Home() {
  const token = getToken();
  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold">Welcome to your app</h1>
      <p className="mt-4">You are authenticated. Token preview (truncated): {token ? token.slice(0,20) + '...' : 'none'}</p>
      <button className="mt-4 bg-red-500 text-white p-2 rounded" onClick={() => { logout(); window.location.href = '/login'; }}>Logout</button>
    </div>
  )
}

export default function App() {
  return (
    <BrowserRouter>
      <nav className="p-4 bg-white shadow">
        <div className="max-w-6xl mx-auto flex justify-between">
          <div className="font-bold">MyApp</div>
          <div>
            <Link to="/" className="mr-4">Home</Link>
            <Link to="/login">Login</Link>
          </div>
        </div>
      </nav>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<PrivateRoute><Home/></PrivateRoute>} />
      </Routes>
    </BrowserRouter>
  );
}
