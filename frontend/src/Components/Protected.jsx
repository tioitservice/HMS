import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = () => {
  const token = localStorage.getItem("token");
  const user = localStorage.getItem("user");

  if (!token || !user) {
    return <Navigate to="/login" replace />;
  }

  const userData = JSON.parse(user);
  
  // Prevent infinite redirect by ensuring we only redirect if not already on /appointments
  if (userData.role === "USER" && window.location.pathname !== "/appointments") {
    return <Navigate to="/appointments" replace />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
