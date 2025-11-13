import React, { useState } from 'react';
import { login } from './auth';
import { useNavigate } from 'react-router-dom';

export default function Login() {
  const [username,setUsername] = useState('');
  const [password,setPassword] = useState('');
  const [error,setError] = useState('');
  const nav = useNavigate();

  async function submit(e) {
    e.preventDefault();
    const ok = await login(username, password);
    if (ok) nav('/');
    else setError('Invalid credentials');
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-50">
      <form onSubmit={submit} className="bg-white p-8 rounded shadow-md w-full max-w-md">
        <h2 className="text-xl font-semibold mb-4">Sign in</h2>
        {error && <div className="text-red-600 mb-2">{error}</div>}
        <input value={username} onChange={e=>setUsername(e.target.value)} placeholder="Username" className="w-full p-2 mb-2 border rounded"/>
        <input value={password} onChange={e=>setPassword(e.target.value)} placeholder="Password" type="password" className="w-full p-2 mb-4 border rounded"/>
        <button className="w-full bg-blue-600 text-white p-2 rounded">Sign in</button>
      </form>
    </div>
  );
}
