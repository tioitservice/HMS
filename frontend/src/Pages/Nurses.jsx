import { use, useState } from "react";
import Layout from "../Components/Layout";
import toast from "react-hot-toast";
import { fetchRequest } from "../utility/apiCall";
import { useEffect } from "react";
export default function Nurses() {
  const [nurses, setNurses] = useState([
    {
      id: 1,
      name: "Nurse Jane Doe",
      position: "Senior Nurse",
       registered: true,
    },
  ]);
  const [showModal, setShowModal] = useState(false);
  const [showDetails, setShowDetails] = useState(false);
  const [editingNurse, setEditingNurse] = useState(null);
  const [selectedNurse, setSelectedNurse] = useState(null);

  const [formData, setFormData] = useState({
    name: "",
    position: "",
     registered: false,
  });

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleAddEditNurse = async() => {
    if (!formData.name || !formData.position) {
      toast.error("Name, Position are required!");
      return;
    }

    if (editingNurse) {
      let response = await fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/registered/"+editingNurse.empId+"/"+formData.registered,"PUT",)
      console.log(formData.registered)
      if(!response.success){
        toast.error(response.error)
        return
      }else{
        console.log(response.body) 
        console.log(nurses)
      setNurses((prev) =>
        prev.map((n) => (n.id === editingNurse.empId ? { ...formData, id: n.id } : n))
      )};
      window.location.reload()
    }else{
      let response = await fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/nurse","POST",formData)
      if(!response.success){
        toast.error(response.error)
        return
      }
      setNurses((prev) => [
        ...prev,
        { ...formData, id: prev.length + 1 },
      ]);
    } 

    setShowModal(false);
    setEditingNurse(null);
    setFormData({ name: "", position: "",  registered: false });
  };

  const handleEditClick = (nurse) => {
    setEditingNurse(nurse);
    setFormData(nurse);
    setShowModal(true);
  };

  const handleDelete = () => {
    if (editingNurse) {
      setNurses((prev) => prev.filter((n) => n.id !== editingNurse.id));
      setShowModal(false);
      setEditingNurse(null);
    }
  };
  useEffect(() => {
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/nurse","GET")
    .then((response) => {
      if(!response.success){
        toast.error(response.error)
        return
      }else{
        setNurses(response.data)
      }
    })
  }
  , []);

  return (
    <Layout>
      <div className="p-8">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-3xl font-semibold text-gray-900">Nurses</h2>
          <button
            className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition-all"
            onClick={() => {
              setEditingNurse(null);
              setFormData({ name: "", position: "",  registered: false });
              setShowModal(true);
            }}
          >
            + Add Nurse
          </button>
        </div>

        <div className="overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
          <table className="w-full text-left border-collapse">
            <thead className="bg-blue-600 text-white">
              <tr>
                <th className="p-4">Name</th>
                <th className="p-4">Position</th>
                <th className="p-4">Registered</th>
                <th className="p-4 text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              {nurses.map((nurse) => (
                <tr key={nurse.id} className="border-b hover:bg-gray-50 transition-all">
                  <td className="p-4">{nurse.name}</td>
                  <td className="p-4">{nurse.position}</td>
                  <td className="p-4 text-center">{nurse. registered ? "Yes" : "No"}</td>
                  <td className="p-4 flex justify-center gap-3">
                    <button
                      className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 transition-all"
                      onClick={() => {
                        setSelectedNurse(nurse);
                        setShowDetails(true);
                      }}
                    >
                      View
                    </button>
                    <button
                      className="bg-yellow-500 text-white px-4 py-2 rounded-md hover:bg-yellow-600 transition-all"
                      onClick={() => handleEditClick(nurse)}
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
              <h2 className="text-2xl font-semibold mb-4 text-center">Nurse Details</h2>
              <div className="grid grid-cols-2 gap-4">
                <p><strong>Name:</strong> {selectedNurse?.name}</p>
                <p><strong>Position:</strong> {selectedNurse?.position}</p>
                <p><strong>Registered:</strong> {selectedNurse?. registered ? "Yes" : "No"}</p>
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
                {editingNurse ? "Edit Nurse" : "Add Nurse"}
              </h2>
              <input type="text" name="name" disabled={editingNurse?true:false} placeholder="Name" value={formData.name} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
              <input type="text" name="position" disabled={editingNurse?true:false} placeholder="Position" value={formData.position} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
              <div className="flex items-center mb-3">
                <span className="mr-3">Registered:</span>
                <label className="inline-flex items-center cursor-pointer">
                  <input type="checkbox" name=" registered" checked={formData. registered} onChange={(e) => setFormData({ ...formData,  registered: e.target.checked })} className="hidden" />
                  <div className={`w-10 h-5 flex items-center bg-gray-300 rounded-full p-1 transition ${formData. registered ? "bg-green-500" : ""}`}>
                    <div className={`bg-white w-4 h-4 rounded-full shadow-md transform ${formData. registered ? "translate-x-5" : ""}`}></div>
                  </div>
                </label>
              </div>
              <button onClick={handleAddEditNurse} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
                {editingNurse ? "Update" : "Add"}
              </button>
              {/* {editingNurse && (
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
