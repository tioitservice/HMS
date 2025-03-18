import { use, useEffect, useState } from "react";
import Layout from "../Components/Layout";
import { fetchRequest } from "../utility/apiCall";

export default function Procedures() {
  const [procedures, setProcedures] = useState([
    { id: 1, name: "MRI Scan", cost: 500 },
    { id: 2, name: "Blood Test", cost: 50 },
  ]);
  const [showModal, setShowModal] = useState(false);
  const [editingProcedure, setEditingProcedure] = useState(null);
  const [formData, setFormData] = useState({ name: "", cost: "" });

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleAddEditProcedure = async() => {
    if (!formData.name || !formData.cost) {
      alert("Name and Cost are required!");
      return;
    }

    if (editingProcedure) {
      let response = await fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/procedure/cost/"+editingProcedure.id,"PUT",formData.cost)
      if(!response.success){
        toast.error(response.error)
        return
      }else{
        console.log(response.data)
      }
      let response2 = await fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/procedure/name/"+editingProcedure.id,"PUT",formData.name)
      console.log(formData.name)
      if(!response2.success){
        toast.error(response2.error)
        return
      }else{
        console.log(response2.data)
      }
      setProcedures((prev) =>
        prev.map((p) => (p.id === editingProcedure.id ? { ...formData, id: p.id } : p))
      );
    } else {
      let response = await fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/procedure","POST",formData)
      if(!response.success){
        toast.error(response.error)
        return
      }else{
        console.log(response.data)
        setProcedures((prev) => [...prev, { ...response.data, id: prev.length + 1 }]);
      }
    }

    setShowModal(false);
    setEditingProcedure(null);
    setFormData({ name: "", cost: "" });
  };

  const handleEditClick = (procedure) => {
    setEditingProcedure(procedure);
    setFormData(procedure);
    setShowModal(true);
  };

  const handleDelete = () => {
    if (editingProcedure) {
      setProcedures((prev) => prev.filter((p) => p.id !== editingProcedure.id));
      setShowModal(false);
      setEditingProcedure(null);
    }
  };
  useEffect(() => {
    async function fetchProcedures() {
      let response = await fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/procedure","GET")
      if(!response.success){
        toast.error(response.error)
        return
      }else{
        console.log(response.data)
        setProcedures(response.data)
      }
    }
    fetchProcedures();
  }, []);



  return (
    <Layout>
      <div className="p-8">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-3xl font-semibold text-gray-900">Procedures</h2>
          <button
            className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition-all"
            onClick={() => {
              setEditingProcedure(null);
              setFormData({ name: "", cost: "" });
              setShowModal(true);
            }}
          >
            + Add Procedure
          </button>
        </div>

        <div className="overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
          <table className="w-full text-left border-collapse">
            <thead className="bg-blue-600 text-white">
              <tr>
                <th className="p-4">Name</th>
                <th className="p-4">Cost (₹)</th>
                <th className="p-4 text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              {procedures.map((procedure) => (
                <tr key={procedure.id} className="border-b hover:bg-gray-50 transition-all">
                  <td className="p-4">{procedure.name}</td>
                  <td className="p-4">{procedure.cost}</td>
                  <td className="p-4 flex justify-center gap-3">
                    <button
                      className="bg-yellow-500 text-white px-4 py-2 rounded-md hover:bg-yellow-600 transition-all"
                      onClick={() => handleEditClick(procedure)}
                    >
                      Edit
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

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
                {editingProcedure ? "Edit Procedure" : "Add Procedure"}
              </h2>
              <input type="text" name="name" placeholder="Procedure Name" value={formData.name} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
              <input type="number" name="cost" placeholder="Cost" value={formData.cost} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
              <button onClick={handleAddEditProcedure} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
                {editingProcedure ? "Update" : "Add"}
              </button>
              {/* {editingProcedure && (
                <button onClick={handleDelete} className="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700 ml-2">
                  Delete
                </button>
              )} */}
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
}
