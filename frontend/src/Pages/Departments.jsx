import { useState, useEffect } from "react";
import Layout from "../Components/Layout";
import { toast } from "react-hot-toast";
import { fetchRequest } from "../utility/apiCall";
import { FaPlus } from "react-icons/fa";
export default function Departments() {
  const [departments, setDepartments] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    deptId: "",
    deptName: "",
    headCertification: "",
    headName: "",
  });

  useEffect(() => {
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI + "user/department", "GET")
      .then((response) => {
        if (response.success) {
          setDepartments(response.data);
        } else {
          toast.error("Unable to fetch departments");
        }
      });
  }, []);

  const handleSave = () => {
    if (!formData.deptName || !formData.headCertification || !formData.headName) {
      toast.error("All fields are required");
      return;
    }
    if (isEditing) {
      let response = fetchRequest(
        import.meta.env.VITE_APP_SERVER_URI + "user/department/" + formData.deptId+ "/"+ formData.deptName,
        "PUT"
      ).then((response) => {
        if (!response.success) {
          toast.error(response.error);
          return;
        }else{
            setDepartments((prev) =>
              prev.map((dept) => (dept.deptId === formData.deptId ? formData : dept))
            );
            toast.success("Department updated successfully!");
        }
      })
    } else {
      let response = fetchRequest(
        import.meta.env.VITE_APP_SERVER_URI + "user/department","POST",formData
      ).then((response) => {
        if (!response.success) {
          toast.error(response.error);
          return;
        }else{
            setDepartments((prev) => [...prev, { ...formData, deptId: Date.now() }]);
            toast.success("Department added successfully!");
        }
      })
    }
    setShowModal(false);
  };

  const handleEditClick = (department) => {
    setFormData(department);
    setIsEditing(true);
    setShowModal(true);
  };

  return (
    <Layout>
      <div className="p-8">
      <div className="flex justify-between items-center mb-6">
          <h2 className="text-3xl font-semibold text-gray-900">Departments</h2>
          <button
            className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            onClick={() => {
              setIsEditing(false);
              setFormData({ deptId: "", deptName: "", headCertification: "", headName: "" });
              setShowModal(true);
            }}
          >
            <FaPlus /> Add Department
          </button>
        </div>
        <div className="overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
          <table className="w-full text-left border-collapse">
            <thead className="bg-blue-600 text-white">
              <tr>
                <th className="p-4">Department Name</th>
                <th className="p-4">Head Certification</th>
                <th className="p-4">Head Name</th>
                <th className="p-4 text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              {departments.map((dept) => (
                <tr key={dept.deptId} className="border-b hover:bg-gray-50 transition-all">
                  <td className="p-4">{dept.deptName}</td>
                  <td className="p-4">{dept.headCertification}</td>
                  <td className="p-4">{dept.headName}</td>
                  <td className="p-4 flex justify-center gap-3">
                    <button
                      className="bg-yellow-500 text-white px-4 py-2 rounded-md hover:bg-yellow-600"
                      onClick={() => handleEditClick(dept)}
                    >
                      Edit
                    </button>
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
            <h2 className="text-2xl font-semibold mb-4 text-center">{isEditing ? "Edit" : "Add"} Department</h2>
            <label className="block mb-2">Department Name</label>
            <input
              type="text"
              className="w-full p-2 border rounded mb-3"
              value={formData.deptName}
              onChange={(e) => setFormData({ ...formData, deptName: e.target.value })}
            />
            {/* <label className="block mb-2">Head Certification</label>
            <input
              type="text"
              className="w-full p-2 border rounded mb-3"
              value={formData.headCertification}
              onChange={(e) => setFormData({ ...formData, headCertification: e.target.value })}
            />
            <label className="block mb-2">Head Name</label>
            <input
              type="text"
              className="w-full p-2 border rounded mb-3"
              value={formData.headName}
              onChange={(e) => setFormData({ ...formData, headName: e.target.value })}
            /> */}
            <button onClick={handleSave} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Save</button>
          </div>
        </div>
      )}
    </Layout>
  );
}
