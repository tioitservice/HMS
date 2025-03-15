import { useState, useEffect } from "react";
import Layout from "../Components/Layout";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { toast } from "react-hot-toast";

export default function Appointments() {
  const [appointments, setAppointments] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    patient: "",
    physician: "",
    nurse: "",
    startTime: new Date(),
    endTime: new Date(),
    room: "",
  });
  
  const [patients, setPatients] = useState([]);
  const [physicians, setPhysicians] = useState([]);
  const [nurses, setNurses] = useState([]);
  const availableRooms = ["Room 101", "Room 102", "Room 103", "Room 104"];

  useEffect(() => {
    // Fetch patient, physician, and nurse data
    setPatients(["John Doe", "Jane Smith"]);
    setPhysicians(["Dr. Smith", "Dr. Adams"]);
    setNurses(["Nurse Amy", "Nurse Bob"]);
  }, []);

  const handleSave = () => {
    if (isEditing) {
      setAppointments((prev) =>
        prev.map((appt) =>
          appt.id === formData.id ? { ...formData } : appt
        )
      );
      toast.success("Appointment updated successfully!");
    } else {
      setAppointments((prev) => [...prev, { ...formData, id: Date.now() }]);
      toast.success("Appointment added successfully!");
    }
    setShowModal(false);
  };

  const handleEditClick = (appointment) => {
    setFormData(appointment);
    setIsEditing(true);
    setShowModal(true);
  };

  const handleDelete = (id) => {
    setAppointments((prev) => prev.filter((appt) => appt.id !== id));
    toast.error("Appointment canceled!");
  };

  return (
    <Layout>
      <div className="p-8">
        <h2 className="text-3xl font-semibold text-gray-900 mb-6">Appointments</h2>
        <button
          className="mb-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
          onClick={() => { setIsEditing(false); setShowModal(true); }}
        >
          Add Appointment
        </button>
        <div className="overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
          <table className="w-full text-left border-collapse">
            <thead className="bg-blue-600 text-white">
              <tr>
                <th className="p-4">Patient</th>
                <th className="p-4">Physician</th>
                <th className="p-4">Nurse</th>
                <th className="p-4">Start Time</th>
                <th className="p-4">End Time</th>
                <th className="p-4">Room</th>
                <th className="p-4 text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              {appointments.map((appt) => (
                <tr key={appt.id} className="border-b hover:bg-gray-50 transition-all">
                  <td className="p-4">{appt.patient}</td>
                  <td className="p-4">{appt.physician}</td>
                  <td className="p-4">{appt.nurse}</td>
                  <td className="p-4">{appt.startTime.toLocaleString()}</td>
                  <td className="p-4">{appt.endTime.toLocaleString()}</td>
                  <td className="p-4">{appt.room}</td>
                  <td className="p-4 flex justify-center gap-3">
                    <button
                      className="bg-yellow-500 text-white px-4 py-2 rounded-md hover:bg-yellow-600"
                      onClick={() => handleEditClick(appt)}
                    >
                      Edit
                    </button>
                    <button
                      className="bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700"
                      onClick={() => handleDelete(appt.id)}
                    >
                      Cancel
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
            <h2 className="text-2xl font-semibold mb-4 text-center">{isEditing ? "Edit" : "Add"} Appointment</h2>
            <label className="block mb-2">Patient</label>
            <select className="w-full p-2 border rounded mb-3" value={formData.patient} onChange={(e) => setFormData({ ...formData, patient: e.target.value })}>
              {patients.map((p, i) => (<option key={i} value={p}>{p}</option>))}
            </select>
            <label className="block mb-2">Physician</label>
            <select className="w-full p-2 border rounded mb-3" value={formData.physician} onChange={(e) => setFormData({ ...formData, physician: e.target.value })}>
              {physicians.map((p, i) => (<option key={i} value={p}>{p}</option>))}
            </select>
            <label className="block mb-2">Nurse</label>
            <select className="w-full p-2 border rounded mb-3" value={formData.nurse} onChange={(e) => setFormData({ ...formData, nurse: e.target.value })}>
              {nurses.map((n, i) => (<option key={i} value={n}>{n}</option>))}
            </select>
            <label className="block mb-2">Start Time</label>
            <DatePicker selected={formData.startTime} onChange={(date) => setFormData({ ...formData, startTime: date })} showTimeSelect dateFormat="Pp" className="w-full p-2 border rounded mb-3" />
            <label className="block mb-2">End Time</label>
            <DatePicker selected={formData.endTime} onChange={(date) => setFormData({ ...formData, endTime: date })} showTimeSelect dateFormat="Pp" className="w-full p-2 border rounded mb-3" />
            <label className="block mb-2">Examination Room</label>
            <select className="w-full p-2 border rounded mb-3" value={formData.room} onChange={(e) => setFormData({ ...formData, room: e.target.value })}>
              {availableRooms.map((r, i) => (<option key={i} value={r}>{r}</option>))}
            </select>
            <button onClick={handleSave} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Save</button>
          </div>
        </div>
      )}
    </Layout>
  );
}
