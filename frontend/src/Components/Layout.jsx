import { Link } from "react-router-dom";
import { FiHome, FiUsers, FiLogOut } from "react-icons/fi";
import { FaUserDoctor, FaUserNurse } from "react-icons/fa6";
import { RiCalendarScheduleLine } from "react-icons/ri";
export default function Layout({ children }) {
  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <aside className="w-64 bg-blue-900 text-white p-6 space-y-6 shadow-lg">
        <h2 className="text-2xl font-bold text-center tracking-wide">HMS</h2>
        <nav className="space-y-4">
          <Link
            to="/dashboard"
            className="flex items-center gap-3 py-3 px-4 rounded-lg transition-all hover:bg-blue-700"
          >
            <FiHome size={20} />
            <span className="text-lg">Dashboard</span>
          </Link>
          <Link
            to="/patients"
            className="flex items-center gap-3 py-3 px-4 rounded-lg transition-all hover:bg-blue-700"
          >
            <FiUsers size={20} />
            <span className="text-lg">Patients</span>
          </Link>
          <Link
            to="/physicians"
            className="flex items-center gap-3 py-3 px-4 rounded-lg transition-all hover:bg-blue-700"
          >
            <FaUserDoctor size={20}/>
            <span className="text-lg">Physicians</span>
          </Link>
          <Link
            to="/nurses"
            className="flex items-center gap-3 py-3 px-4 rounded-lg transition-all hover:bg-blue-700"
          >
            <FaUserNurse size={20}/>
            <span className="text-lg">Nurses</span>
          </Link>
          <Link
            to="/appointments"
            className="flex items-center gap-3 py-3 px-4 rounded-lg transition-all hover:bg-blue-700"
          >
            <RiCalendarScheduleLine size={20}/>
            <span className="text-lg">Appointments</span>
          </Link>
        </nav>
      </aside>

      {/* Main Content */}
      <div className="flex-1 flex flex-col">
        {/* Navbar */}
        <header className="bg-white shadow-md p-4 flex justify-between items-center">
          <h1 className="text-xl font-semibold text-gray-800">Dashboard</h1>
          <Link
            to="/login"
            className="flex items-center gap-2 text-red-600 hover:text-red-700 font-semibold transition-all"
          >
            <FiLogOut size={18} />
            Logout
          </Link>
        </header>

        {/* Page Content */}
        <main className="flex-1 p-8">{children}</main>
      </div>
    </div>
  );
}
