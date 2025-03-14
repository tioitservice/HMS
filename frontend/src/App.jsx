import './App.css'
import { Routes, Route } from "react-router-dom";
import Login from './Components/Login';
import Dashboard from './Pages/Dashboard';
import Register from './Components/Register';
import Patients from './Pages/Patients';
import Physicians from './Pages/Physicians';
import Appointments from './Pages/Appointments';
import Nurses from './Pages/Nurses';
function App() {
  return (
    <Routes>
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/patients" element={<Patients />} />
      <Route path="/physicians" element={<Physicians />} />
      <Route path="/nurses" element={<Nurses />} />
      <Route path="/appointments" element={<Appointments />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

  </Routes>
  )
}

export default App
