import { Navigate } from "react-router-dom";
import { useAuth } from "../Controller/AuthRole";

const PrivateRoute = ({ element, requiredRole }) => {
  const { user } = useAuth();

  // Redirect if user is not authorized
  return user.role === requiredRole ? element : <Navigate to="/unauthorized" />;
};

export default PrivateRoute;
