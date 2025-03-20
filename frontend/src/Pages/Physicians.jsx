import { useState, useEffect } from "react";
import Layout from "../Components/Layout";
import toast from "react-hot-toast";
import { fetchRequest } from "../utility/apiCall";

export default function Physicians() {
  const [physicians, setPhysicians] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [trainedInOptions, setTrainedInOptions] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [showDetails, setShowDetails] = useState(false);
  const [editingPhysician, setEditingPhysician] = useState(null);
  const [selectedPhysician, setSelectedPhysician] = useState(null);

  const [formData, setFormData] = useState({
    name: "",
    position: "",
    deptId: 0,
    trainId: 0,
    ssn: "",
  });

  // Fetch departments and trained_in options on component mount
  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        // Fetch departments
        const deptResponse = await fetchRequest(
          `${import.meta.env.VITE_APP_SERVER_URI}user/department`,
          "GET"
        );
        if (deptResponse.success) {
          setDepartments(deptResponse.data);
        } else {
          toast.error("Failed to fetch departments");
        }

        // Fetch trained_in options
        const trainResponse = await fetchRequest(
          `${import.meta.env.VITE_APP_SERVER_URI}user/trained_in`,
          "GET"
        );
        if (trainResponse.success) {
          setTrainedInOptions(trainResponse.data);
        } else {
          toast.error("Failed to fetch trained_in options");
        }

        // Fetch physicians
        const physResponse = await fetchRequest(
          `${import.meta.env.VITE_APP_SERVER_URI}user/physicians`,
          "GET"
        );
        if (physResponse.success) {
          setPhysicians(physResponse.data);
        } else {
          toast.error("Failed to fetch physicians");
        }
      } catch (error) {
        toast.error("Error fetching initial data");
        console.error(error);
      }
    };

    fetchInitialData();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: name === 'deptId' || name === 'trainId'
        ? parseInt(value) || 0
        : value
    });
  };

  const handleAddEditPhysician = async () => {
    if (!formData.name || !formData.position || !formData.deptId || !formData.trainId) {
      toast.error("All fields are required!");
      return;
    }

    const physicianData = {
      name: formData.name,
      position: formData.position,
      deptId: formData.deptId,
      trainId: formData.trainId,
      ssn: formData.ssn
    };

    try {
      if (editingPhysician) {
        const response = await fetchRequest(
          `${import.meta.env.VITE_APP_SERVER_URI}user/physician/update/${editingPhysician.employeeId}`,
          "PUT",
          physicianData
        );

        if (!response.success) {
          toast.error(response.error || "Failed to update physician");
          return;
        }
        
        setPhysicians((prev) =>
          prev.map((p) =>
            p.employeeId === editingPhysician.employeeId
              ? { ...p, ...physicianData }
              : p
          )
        );
        toast.success("Physician updated successfully");
      } else {
        const response = await fetchRequest(
          `${import.meta.env.VITE_APP_SERVER_URI}user/physician`,
          "POST",
          physicianData
        );

        if (!response.success) {
          toast.error(response.error || "Failed to add physician");
          return;
        }

        setPhysicians((prev) => [...prev, response.data]);
        toast.success("Physician added successfully");
      }

      setShowModal(false);
      setEditingPhysician(null);
      setFormData({
        name: "",
        position: "",
        deptId: 0,
        trainId: 0,
        ssn: ""
      });
    } catch (error) {
      toast.error("Error processing request");
      console.error(error);
    }
  };

  const handleEditClick = (physician) => {
    setEditingPhysician(physician);
    setFormData({
      name: physician.name || "",
      position: physician.position || "",
      deptId: physician.deptId || 0,
      trainId: physician.trainId || 0,
      ssn: physician.ssn || ""
    });
    setShowModal(true);
  };

  const modalForm = (
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
        
        <input
          type="text"
          name="name"
          placeholder="Physician Name"
          value={formData.name}
          onChange={handleInputChange}
          className="w-full p-2 border rounded mb-3"
        />

        <input
          type="text"
          name="position"
          placeholder="Position"
          value={formData.position}
          onChange={handleInputChange}
          className="w-full p-2 border rounded mb-3"
        />

        <input
          type="text"
          name="ssn"
          placeholder="SSN"
          value={formData.ssn}
          onChange={handleInputChange}
          className="w-full p-2 border rounded mb-3"
        />

        <select
          name="deptId"
          value={formData.deptId}
          onChange={handleInputChange}
          className="w-full p-2 border rounded mb-3"
        >
          <option value={0}>Select Department</option>
          {departments.map((dept) => (
            <option key={dept.id} value={dept.deptId}>
              {dept.deptName}
            </option>
          ))}
        </select>

        <select
          name="trainId"
          value={formData.trainId}
          onChange={handleInputChange}
          className="w-full p-2 border rounded mb-3"
        >
          <option value={0}>Select Training</option>
          {trainedInOptions.map((train) => (
            <option key={train.id} value={train.trainId}>
              {train.trainingName}
            </option>
          ))}
        </select>

        <button
          onClick={handleAddEditPhysician}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          {editingPhysician ? "Update" : "Add"}
        </button>
      </div>
    </div>
  );

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
                deptId: 0,
                trainId: 0,
                ssn: ""
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
                <th className="p-4">ID</th>
                <th className="p-4">Name</th>
                <th className="p-4">Position</th>
                <th className="p-4 text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              {physicians.map((physician) => (
                <tr key={physician.employeeId} className="border-b hover:bg-gray-50 transition-all">
                  <td className="p-4">{physician.employeeId}</td>
                  <td className="p-4">{physician.name}</td>
                  <td className="p-4">{physician.position}</td>
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
                <p><strong>Department ID:</strong> {selectedPhysician?.deptId}</p>
                <p><strong>Train ID:</strong> {selectedPhysician?.trainId}</p>
                <p><strong>SSN:</strong> {selectedPhysician?.ssn}</p>
              </div>
            </div>
          </div>
        )}

        {showModal && modalForm}
      </div>
    </Layout>
  );
}