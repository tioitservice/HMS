import { useState, useEffect } from "react";
import Layout from "../Components/Layout";
import { toast } from "react-hot-toast";
import { FaPlus, FaTrash } from "react-icons/fa";
import { fetchRequest } from "../utility/apiCall";

export default function TrainedIn() {
  const [trainings, setTrainings] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({ id: 0, trainingName: "" });

  useEffect(() => {
    // Mock fetch call
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/trained_in", "GET").then((response) => {
      if (response.success) {
        setTrainings(response.data);
      }else{
        toast.error("Unable to fetch trainings");
      }
    })
  }, []);

  const handleSave = () => {
    if (!formData.trainingName.trim()) {
      toast.error("Training name is required");
      return;
    }
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/trained_in","POST",formData.trainingName).then((response) => {
      if (!response.success) {
        toast.error(response.error);
        return;
      }
      setTrainings((prev) => [...prev, { ...formData, id: Date.now() }]);
      toast.success("Training added successfully!");
    })
    setShowModal(false);
  };

  const handleDelete = (id) => {
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/trained_in/"+id,"DELETE").then((response) => {
      if (!response.success) {
        toast.error(response.error);
        return;
      }
    })
    setTrainings((prev) => prev.filter((training) => training.id !== id));
    toast.error("Training deleted!");
  };

  const handleEditClick = (training) => {
    setFormData(training);
    // setIsEditing(true);
    setShowModal(true);
  };
  return (
    <Layout>
      <div className="p-8">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-3xl font-semibold text-gray-900">Trained In</h2>
          <button
            className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            onClick={() => {
              setFormData({ id: 0, trainingName: "" });
              setShowModal(true);
            }}
          >
            <FaPlus /> Add Training
          </button>
        </div>
        <div className="overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
          <table className="w-full text-left border-collapse">
            <thead className="bg-blue-600 text-white">
              <tr>
                <th className="p-4">Training Name</th>
                <th className="p-4 text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              {trainings.map((training) => (
                <tr key={training.id} className="border-b hover:bg-gray-50 transition-all">
                  <td className="p-4">{training.trainingName}</td>
                  <td className="p-4 flex justify-center">
                    <button
                      className="bg-red-600 text-white px-4 mr-2 py-2 rounded-md hover:bg-red-700"
                      onClick={() => handleDelete(training.id)}
                    >
                      <FaTrash />
                    </button>
                    {/* <button
                      className="bg-yellow-500 text-white px-4 py-2 rounded-md hover:bg-yellow-600"
                      onClick={() => handleEditClick(training)}
                    >
                      Edit
                    </button> */}
                  </td>
                 
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      {showModal && (
        <div className="fixed inset-0 backdrop-blur-md flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg max-w-lg w-full relative">
            <button className="absolute top-2 right-2 text-gray-600 hover:text-gray-900" onClick={() => setShowModal(false)}>✖</button>
            <h2 className="text-2xl font-semibold mb-4 text-center">Add Training</h2>
            <label className="block mb-2">Training Name</label>
            <input
              type="text"
              className="w-full p-2 border rounded mb-3"
              value={formData.trainingName}
              onChange={(e) => setFormData({ ...formData, trainingName: e.target.value })}
            />
            <button onClick={handleSave} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Save</button>
          </div>
        </div>
      )}
    </Layout>
  );
}
