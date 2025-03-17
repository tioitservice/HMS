import { use, useState } from "react";
import Layout from "../Components/Layout";
import toast from "react-hot-toast";
import {useEffect}  from "react";
import { fetchRequest } from "../utility/apiCall";
export default function TrainedIn() {
  const [physicians, setPhysicians] = useState([
    {
      id: 1,
      name: "Dr. John Doe",
      position: "Cardiologist",
      // ssn: "123-45-6789",
      // department: "Cardiology",
      // specialization: "Heart Diseases",
      // certificationDate: "2022-05-10",
      // certificationExpires: "2027-05-10",
      // primaryAffiliation: true,
    },
  ]);
  const [showModal, setShowModal] = useState(false);
  const [showDetails, setShowDetails] = useState(false);
  const [editingPhysician, setEditingPhysician] = useState(null);
  const [selectedPhysician, setSelectedPhysician] = useState(null);

  const [formData, setFormData] = useState({
    name: "",
    position: "",
    // ssn: "",
    // department: "",
    // specialization: "",
    // certificationDate: "",
    // certificationExpires: "",
    // primaryAffiliation: false,
  });

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleAddEditPhysician = async() => {
    if (!formData.name || !formData.position ) {
      
      toast.error("Name, Position are required!");
      return;
    }

    if (editingPhysician) {
      console.log(editingPhysician)
      const updatedPhysician = {
        employeeId: editingPhysician.employeeId,
        name: formData.name,
        position: formData.position,
      };
  
      let response = await fetchRequest(
        `${import.meta.env.VITE_APP_SERVER_URI}user/physician/update/${editingPhysician.employeeId}`,
        "PUT",
        updatedPhysician // Send the updated physician object in the request body
      );
  
      if (!response.success) {
        toast.error(response.error || "Failed to update physician");
        return;
      } else {
        console.log("Physician updated successfully:", response.data);
        setPhysicians((prev) =>
          prev.map((p) =>
            p.employeeId === editingPhysician.employeeId
              ? { ...p, position: formData.position, name: formData.name } // Update both position and name
              : p
          )
        );
        toast.success("Physician updated successfully");
      }
      
    } else {
      let response = await fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/physician","POST",formData)
      console.log(formData)
      if(!response.success){
        toast.error(response.error)
        return
      }else{
        console.log(response.data)
      setPhysicians((prev) => [...prev, { ...formData, id: prev.length + 1 }]);
      }
    }

    setShowModal(false);
    setEditingPhysician(null);
    setFormData({
      name: "",
      position: "",
      // ssn: "",
      // department: "",
      // specialization: "",
      // certificationDate: "",
      // certificationExpires: "",
      // primaryAffiliation: false,
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
  useEffect(() => {
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI+"user/physicians","GET")
    .then((response) => {
      if(!response.success){
        toast.error(response.error)
        return
      }else{
        console.log(response.data)
        // console.log(response.body)
        setPhysicians(response.data)
      }
    })
  },[]);

  return (
    <Layout>
      <div className="p-8">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-3xl font-semibold text-gray-900">Trained In</h2>
          <button
            className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition-all"
            onClick={() => {
              setEditingPhysician(null);
              setFormData({
                name: "",
                position: "",
         
              });
              setShowModal(true);
            }}
          >
            + Add Trained In Data
          </button>
        </div>

        <div className="overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
          <table className="w-full text-left border-collapse">
            <thead className="bg-blue-600 text-white">
              <tr>
                <th className="p-4">Traning ID</th>
                <th className="p-4">Traning Name</th>
                {/* <th className="p-4">Department</th> */}
                <th className="p-4 text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              {physicians.map((physician) => (
                <tr key={physician.employeeId} className="border-b hover:bg-gray-50 transition-all">
                  <td className="p-4">{physician.name.replace(/[^a-zA-Z\s]/g, '')}</td>
                  <td className="p-4">{physician.position}</td>
                  {/* <td className="p-4">{physician.department}</td> */}
                  <td className="p-4 flex justify-center gap-3">
                    
                    <button onClick={handleDelete} className="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700 ml-2">
                      Delete
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
              <h2 className="text-2xl font-semibold mb-4 text-center">Trained In Details</h2>
              <div className="grid grid-cols-2 gap-4">
                <p><strong>Traning Id:</strong> {selectedPhysician?.name}</p>
                <p><strong>Traning Name:</strong> {selectedPhysician?.position}</p>

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
        {editingPhysician ? "Edit Trained In" : "Add Trained In"}
      </h2>
      <input type="text" name="name" placeholder="Traning Name" value={formData.name} onChange={handleInputChange} className="w-full p-2 border rounded mb-3" />
      <button onClick={handleAddEditPhysician} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
        {editingPhysician ? "Update" : "Add"}
      </button>
    
    </div>
  </div>
)}

      </div>
    </Layout>
  );
}
