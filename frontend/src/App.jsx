import './App.css'
import { Routes, Route, Navigate } from "react-router-dom";
import Login from './Components/Login';
import Dashboard from './Pages/Dashboard';
import Register from './Components/Register';
import Patients from './Pages/Patients';
import Physicians from './Pages/Physicians';
import Appointments from './Pages/Appointments';
import Nurses from './Pages/Nurses';
import Procedures from './Pages/Procedures';
import ProtectedRoute from './Components/Protected';
function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route element={<ProtectedRoute/>}>
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/patients" element={<Patients />} />
          <Route path="/physicians" element={<Physicians />} />
          <Route path="/nurses" element={<Nurses />} />
          <Route path="/procedures" element={<Procedures />} />
          <Route path="/appointments" element={<Appointments />} />
      </Route>
  </Routes>
  )
}

export default App
