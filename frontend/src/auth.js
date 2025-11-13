/**
 * Simple auth helper: stores token in localStorage.
 * Replace with secure storage and refresh token flow in production.
 */
import axios from 'axios';
const API = (import.meta.env.VITE_API_BASE_URL) ? import.meta.env.VITE_API_BASE_URL : '/api';

export async function login(username, password) {
  const res = await axios.post(`${API}/auth/login`, { username, password });
  if (res.data && res.data.token) {
    localStorage.setItem('jwt_token', res.data.token);
    return true;
  }
  return false;
}

export function logout() {
  localStorage.removeItem('jwt_token');
}

export function getToken() {
  return localStorage.getItem('jwt_token');
}

export function authHeader() {
  const t = getToken();
  return t ? { Authorization: `Bearer ${t}` } : {};
}
