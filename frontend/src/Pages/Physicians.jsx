import { useState } from "react";
import Layout from "../Components/Layout";
import toast from "react-hot-toast";

export default function Physicians() {
  const [physicians, setPhysicians] = useState([
    {
      id: 1,
      name: "Dr. John Doe",
      position: "Cardiologist",
      ssn: "123-45-6789",
      department: "Cardiology",
      specialization: "Heart Diseases",
      certificationDate: "2022-05-10",
      certificationExpires: "2027-05-10",
      primaryAffiliation: true,
    },
  ]);
  const [showModal, setShowModal] = useState(false);
  const [showDetails, setShowDetails] = useState(false);
  const [editingPhysician, setEditingPhysician] = useState(null);
  const [selectedPhysician, setSelectedPhysician] = useState(null);

  const [formData, setFormData] = useState({
    name: "",
    position: "",
    ssn: "",
    department: "",
    specialization: "",
    certificationDate: "",
    certificationExpires: "",
    primaryAffiliation: false,
  });

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleAddEditPhysician = () => {
    if (!formData.name || !formData.position || !formData.ssn) {
      toast.error("Name, Position, and SSN are required!");
      return;
    }

    if (editingPhysician) {
      setPhysicians((prev) =>
        prev.map((p) => (p.id === editingPhysician.id ? { ...formData, id: p.id } : p))
      );
    } else {
      setPhysicians((prev) => [...prev, { ...formData, id: prev.length + 1 }]);
    }

    setShowModal(false);
    setEditingPhysician(null);
    setFormData({
      name: "",
      position: "",
      ssn: "",
      department: "",
      specialization: "",
      certificationDate: "",
      certificationExpires: "",
      primaryAffiliation: false,
    });
  };

  const handleEditClick = (physician) => {
    setEditingPhysician(physician);
    setFormData(physician);
    setShowModal(true);
  };

  const handleDelete = () => {
    if (editingPhysician) {
      setPhysicians((prev) => prev.filter((p) => p.id !== editingPhysician.id));
      setShowModal(false);
      setEditingPhysician(null);
    }
  };

  return (
    <Layout>
      <div className="p-8">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-3xl font-semibold text-gray-900">Physicians</h2>
          <button
            className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition-all"
            onClick={() => {
              setEditingPhysician(null);
              setFormData({
                name: "",
                position: "",
                ssn: "",
                department: "",
                specialization: "",
                certificationDate: "",
                certificationExpires: "",
                primaryAffiliation: false,
              });
              setShowModal(true);
            }}
          >
            + Add Physician
          </button>
        </div>

        <div className="overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
          <table className="w-full text-left border-collapse">
            <thead className="bg-blue-600 text-white">
              <tr>
                <th className="p-4">Name</th>
                <th className="p-4">Position</th>
                <th className="p-4">Department</th>
                <th className="p-4 text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              {physicians.map((physician) => (
                <tr key={physician.id} className="border-b hover:bg-gray-50 transition-all">
                  <td className="p-4">{physician.name}</td>
                  <td className="p-4">{physician.position}</td>
                  <td className="p-4">{physician.department}</td>
                  <td className="p-4 flex justify-center gap-3">
                    <button
                      className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 transition-all"
                      onClick={() => {
                        setSelectedPhysician(physician);
                        setShowDetails(true);
                      }}
                    >
                      View
                    </button>
                    <button
                      className="bg-yellow-500 text-white px-4 py-2 rounded-md hover:bg-yellow-600 transition-all"
                      onClick={() => handleEditClick(physician)}
                    >
                      Edit
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {showDetails && (
          <div className="fixed inset-0 backdrop-blur-md flex justify-center items-center">
            <div className="bg-white p-6 rounded-lg shadow-lg max-w-lg w-full relative">
              <button
                className="absolute top-2 right-2 text-gray-600 hover:text-gray-900"
                onClick={() => setShowDetails(false)}
              >
                ✖
              </button>
              <h2 className="text-2xl font-semibold mb-4 text-center">Physician Details</h2>
              <div className="grid grid-cols-2 gap-4">
                <p><strong>Name:</strong> {selectedPhysician?.name}</p>
                <p><strong>Position:</strong> {selectedPhysician?.position}</p>
                <p><strong>SSN:</strong> {selectedPhysician?.ssn}</p>
                <p><strong>Department:</strong> {selectedPhysician?.department}</p>
                <p><strong>Specialization:</strong> {selectedPhysician?.specialization}</p>
                <p><strong>Certification Date:</strong> {selectedPhysician?.certificationDate}</p>
                <p><strong>Certification Expires:</strong> {selectedPhysician?.certificationExpires}</p>
                <p><strong>Primary Affiliation:</strong> {selectedPhysician?.primaryAffiliation ? "Yes" : "No"}</p>
              </div>
            </div>
          </div>
        )}

{showModal && (
  <div className="fixed inset-0 backdrop-blur-sm flex justify-center items-center">
    <div className="bg-white p-6 rounded-lg shadow-lg max-w-lg w-full relative">
      <button
        className="absolute top-2 right-2 text-gray-600 hover:text-gray-900"
        onClick={() => setShowModal(false)}
      >
        ✖
      </button>
      <h2 className="text-2xl font-semibold mb-4">
        {editingPhysician ? "Edit Physician" : "Add Physician"}
      </h2>
      <input type="text" name="name" placeholder="Name" value={formData.name} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
      <input type="text" name="position" placeholder="Position" value={formData.position} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
      <input type="text" name="ssn" placeholder="SSN" value={formData.ssn} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
      <input type="text" name="department" placeholder="Department" value={formData.department} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
      <input type="text" name="specialization" placeholder="Specialization" value={formData.specialization} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
      <input type="date" name="certificationDate" value={formData.certificationDate} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
      <input type="date" name="certificationExpires" value={formData.certificationExpires} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
      <div className="flex items-center mb-3">
        <span className="mr-3">Primary Affiliation:</span>
        <label className="inline-flex items-center cursor-pointer">
          <input type="checkbox" name="primaryAffiliation" checked={formData.primaryAffiliation} onChange={(e) => setFormData({ ...formData, primaryAffiliation: e.target.checked })} className="hidden" />
          <div className={`w-10 h-5 flex items-center bg-gray-300 rounded-full p-1 transition ${formData.primaryAffiliation ? "bg-green-500" : ""}`}>
            <div className={`bg-white w-4 h-4 rounded-full shadow-md transform ${formData.primaryAffiliation ? "translate-x-5" : ""}`}></div>
          </div>
        </label>
      </div>
      <button onClick={handleAddEditPhysician} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
        {editingPhysician ? "Update" : "Add"}
      </button>
      {editingPhysician && (
        <button onClick={handleDelete} className="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700 ml-2">
          Delete
        </button>
      )}
    </div>
  </div>
)}

      </div>
    </Layout>
  );
}
