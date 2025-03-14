// src/pages/Dashboard.js
import Layout from "../Components/Layout";

export default function Dashboard() {
  const stats = [
    { title: "Patients", total: 10, color: "bg-blue-500" },
    { title: "Nurses", total: 5, color: "bg-green-500" },
    { title: "Physicians", total: 15, color: "bg-purple-500" },
  ];
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
            <h3 className="text-xl font-semibold">{item.title}</h3>
            <p className="text-3xl font-bold mt-2">{item.total}</p>
          </div>
        ))}
      </div>
    </Layout>
  );
}
