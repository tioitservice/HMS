import { createContext, useState } from "react";

const AuthContext = createContext();


export const AuthRole = ({ children }) => {
  const [user, setUser] = useState({
    role: "admin",
  });

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthRole;