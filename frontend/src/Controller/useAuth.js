import { useContext } from "react";
import { AuthContext } from "./AuthRole";

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthRole provider");
  }
  console.log("useAuth:", context);
  return context;
};
