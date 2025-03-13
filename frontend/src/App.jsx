import './App.css'
import { Routes, Route } from "react-router-dom";
import Login from './Components/Login';
import Dashboard from './Components/Dashboard';
import Register from './Components/Register';
function App() {
  return (
    <Routes>
      <Route path="/" element={<Dashboard />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

  </Routes>
  )
}

export default App
