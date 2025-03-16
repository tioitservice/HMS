// src/pages/Dashboard.js
import { useEffect, useState } from "react";
import Layout from "../Components/Layout";
import {Link} from "react-router-dom";
import { fetchRequest } from "../utility/apiCall";
export default function Dashboard() {
  const [stats, setStats] = useState([
    { title: "Registered Patients", total: 10, color: "bg-blue-500" },
    { title: "Prep Nurses", total: 5, color: "bg-green-500" },
    { title: "Available Physicians", total: 15, color: "bg-purple-500" },
  ]);

  const fetchPatientsNursesPhysicians = async () => {
      let reponse = await fetchRequest()
  }
  useEffect(() => {
    // on page load we will fetch the registered patients, prep nurses, and available physicians

  },[])
  return (
    <Layout>
      <h2 className="text-2xl font-bold">Welcome to the Dashboard</h2>
      <p className="mt-2 text-gray-600">Manage your hospital system efficiently.</p>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 mt-6">
        {stats.map((item, index) => (
          <div
            key={index}
            className={`p-6 ${item.color} text-white rounded-xl shadow-lg flex flex-col items-center`}
          >
            <Link to={`/${item.title.toLowerCase()}`}>
            <h3 className="text-xl font-semibold">{item.title}</h3>
            <p className="text-3xl font-bold mt-2">{item.total}</p>
            </Link>
          </div>
        ))}
      </div>
    </Layout>
  );
}
