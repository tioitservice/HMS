import { use, useState, useEffect } from "react";
import Layout from "../Components/Layout";
import { FaEye as Eye } from "react-icons/fa";
import { fetchRequest } from "../utility/apiCall";
import toast from "react-hot-toast";
export default function Patients() {
  const [patients, setPatients] = useState([
    {
      id: 1,
      name: "John Doe",
      email: "john.doe@example.com",
      address: "1234 Elm Street, Springfield, USA",
      phone: "+1 234 567 8901",
    },
    {
      id: 2,
      name: "Jane Smith",
      email: "jane.smith@example.com",
      address: "5678 Oak Avenue, Metropolis, USA",
      phone: "+1 987 654 3210",
    },
  ]);

  const [showDetails, setShowDetails] = useState(false);
  const [selectedPatient, setSelectedPatient] = useState(null);

  useEffect(() => {
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/getAll","GET").then((response) => {
      console.log(response.data)
      if (response.success) {
        const filteredPatients = response.data.filter((patient) => patient.username !== "admin");
        setPatients(filteredPatients);
      }else{
        toast.error("Unable to fetch patients")
        setPatients([
          {
            "id": "1",
            "patientId": "John Doe",
            "email": "john.doe@example.com",
            "address": "1234 Elm Street, Springfield, USA",
            "phone": "+1 234 567 8901",
            "name": "John Doe"
          }
        ])
      }
    })
  }
  ,[])
  return (
    <Layout>
      <div className="p-8">
        <h2 className="text-3xl font-semibold text-gray-900 mb-6">Patients List</h2>
        <div className="overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
          <table className="w-full text-left border-collapse">
            <thead className="bg-blue-600 text-white">
              <tr>
              <th className="p-4">Id</th>
              <th className="p-4">Username</th>
                <th className="p-4">Name</th>
                <th className="p-4">Email</th>
                <th className="p-4">Phone Number</th> 
                 <th className="p-4">Address</th>
                 <th className="p-4 text-center">Actions</th> 
              </tr>
            </thead>
            <tbody>
              {patients.map((patient) => (
                <tr key={patient.id} className="border-b hover:bg-gray-50 transition-all">
                  <td className="p-4">{patient.id}</td>
                  <td className="p-4">{patient.name}</td>
                  <td className="p-4">{patient.username}</td>
                  <td className="p-4">{patient.email}</td>
                  <td className="p-4">{patient.phoneNumber}</td> 
                  <td className="p-4">{patient.address}</td>
                  <td className="p-4 flex justify-center gap-3">
                    <button
                      className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 transition-all flex items-center gap-2"
                      onClick={() => {
                        setSelectedPatient(patient);
                        setShowDetails(true);
                      }}
                    >
                      <Eye size={18} /> View
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {showDetails && selectedPatient && (
        <div className="fixed inset-0 backdrop-blur-md flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg max-w-lg w-full relative">
            <button
              className="absolute top-2 right-2 text-gray-600 hover:text-gray-900"
              onClick={() => setShowDetails(false)}
            >
              ✖
            </button>
            <h2 className="text-2xl font-semibold mb-4 text-center">Patient Details</h2>
            <div className="grid grid-cols-2 gap-4">
              <p><strong>Id:</strong> {selectedPatient.id}</p>
              <p><strong>Username:</strong> {selectedPatient.username}</p>
              <p><strong>Name:</strong> {selectedPatient.name}</p>
              <p><strong>Email:</strong> {selectedPatient.email}</p>
              <p><strong>Phone Number:</strong> {selectedPatient.phoneNumber}</p>
              <p><strong>Address:</strong> {selectedPatient.address}</p>
            </div>
          </div>
        </div>
      )}
    </Layout>
  );
}
