import { Link, Navigate, useNavigate } from "react-router-dom";
import { FiHome, FiUsers, FiLogOut } from "react-icons/fi";
import { FaUserDoctor, FaUserNurse } from "react-icons/fa6";
import { RiCalendarScheduleLine } from "react-icons/ri";
import { FaSitemap } from "react-icons/fa";

export default function Layout({ children }) {
  const user = JSON.parse(localStorage.getItem("user")); // Assume user object has a role field
  const navigate = useNavigate();
  const menuItems = [
    { name: "Dashboard", path: "/dashboard", icon: <FiHome size={20} />, roles: ["ADMIN"] },
    { name: "Appointments", path: "/appointments", icon: <RiCalendarScheduleLine size={20} />, roles: ["ADMIN", "USER"] },
    { name: "Patients", path: "/patients", icon: <FiUsers size={20} />, roles: ["ADMIN"] },
    { name: "Physicians", path: "/physicians", icon: <FaUserDoctor size={20} />, roles: ["ADMIN"] },
    { name: "Nurses", path: "/nurses", icon: <FaUserNurse size={20} />, roles: ["ADMIN"] },
    { name: "Trained In", path: "/trained_in", icon: <FaUserNurse size={20} />, roles: ["ADMIN"] },
    { name: "Procedures", path: "/procedures", icon: <FaSitemap size={20} />, roles: ["ADMIN"] },
  ];

  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <aside className="w-64 bg-blue-900 text-white p-6 space-y-6 shadow-lg">
        <h2 className="text-2xl font-bold text-center tracking-wide">HMS</h2>
        <nav className="space-y-4">
          {menuItems.filter(item => item.roles.includes(user?.role)).map((item) => (
            <Link
              key={item.path}
              to={item.path}
              className="flex items-center gap-3 py-3 px-4 rounded-lg transition-all hover:bg-blue-700"
            >
              {item.icon}
              <span className="text-lg">{item.name}</span>
            </Link>
          ))}
        </nav>
      </aside>

      {/* Main Content */}
      <div className="flex-1 flex flex-col">
        {/* Navbar */}
        <header className="bg-white shadow-md p-4 flex justify-between items-center">
          <h1 className="text-xl font-semibold text-gray-800">Dashboard</h1>
          <button
            onClick={() => {
              localStorage.removeItem("token");
              localStorage.removeItem("user");
              navigate("/login");
            }}
            className="flex items-center gap-2 text-red-600 hover:text-red-700 font-semibold transition-all"
          >
            <FiLogOut size={18} />
            Logout
          </button>
        </header>

        {/* Page Content */}
        <main className="flex-1 p-8">{children}</main>
      </div>
    </div>
  );
}
