import { useState, useEffect } from "react";
import Layout from "../Components/Layout";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { toast } from "react-hot-toast";
import { fetchRequest } from "../utility/apiCall";

export default function Appointments() {
  const [appointments, setAppointments] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const user = JSON.parse(localStorage.getItem("user"));
  const [physicians, setPhysicians] = useState([]);
  const [patients, setPatients] = useState([]);
  const [nurses, setNurses] = useState([]);
  const [rooms, setRooms] = useState([]);
  const [formData, setFormData] = useState({
    appointmentId: 0,
    physicianId: "",
    nurseId: "0",
    patientId: user.role === "USER" ? user?.id || "" : "",
    startTime: new Date(),
    examinationRoomId: "0",
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const apptResponse = await fetchRequest(
          `${import.meta.env.VITE_APP_SERVER_URI}user/appointment`,
          "GET"
        );
        if (apptResponse.success && apptResponse.data) {
          setAppointments(
            user.role === "USER"
              ? apptResponse.data.filter((appt) => appt.patientId === user.id)
              : apptResponse.data
          );
        }

        const physResponse = await fetchRequest(
          `${import.meta.env.VITE_APP_SERVER_URI}user/physicians`,
          "GET"
        );
        if (physResponse.success && physResponse.data) {
          setPhysicians(physResponse.data);
          if (physResponse.data.length > 0 && !isEditing) {
            setFormData((prev) => ({
              ...prev,
              physicianId: physResponse.data[0].employeeId,
            }));
          }
        }

        const patientsResponse = await fetchRequest(
          `${import.meta.env.VITE_APP_SERVER_URI}user/getAll`,
          "GET"
        );
        if (patientsResponse.success && patientsResponse.data) {
          setPatients(patientsResponse.data);
        }

        const nursesResponse = await fetchRequest(
          `${import.meta.env.VITE_APP_SERVER_URI}user/nurse`,
          "GET"
        );
        if (nursesResponse.success && nursesResponse.data) {
          setNurses(nursesResponse.data);
        }

        const roomsResponse = await fetchRequest(
          `${import.meta.env.VITE_APP_SERVER_URI}user/room/all`,
          "GET"
        );
        if (roomsResponse.success && roomsResponse.data) {
          setRooms(roomsResponse.data.filter((room) => room.booked === false));
        }
      } catch (error) {
        toast.error("Failed to load initial data");
        console.error("Fetch error:", error);
      }
    };

    fetchData();
  }, [user?.id, user?.role]);

  const handleSave = async () => {
    if (!formData.physicianId) {
      toast.error("Please select a physician");
      return;
    }
    if (!formData.patientId) {
      toast.error("Please select a patient");
      return;
    }
    if (!formData.startTime) {
      toast.error("Please select a start time");
      return;
    }

    const token = localStorage.getItem("token");
    if (!token) {
      toast.error("You are not authorized. Please login.");
      return;
    }

    setIsLoading(true);
    try {
      const newFormData = {
        appointmentId: isEditing ? formData.appointmentId : 0,
        physicianId: parseInt(formData.physicianId),
        nurseId: parseInt(formData.nurseId),
        patientId: isEditing ? formData.patientId : user.role === "USER" ? user.id : formData.patientId,
        startDate: formData.startTime.toISOString(),
        examinationRoomId: parseInt(formData.examinationRoomId),
      };

      const method = isEditing ? "PUT" : "POST";
      const url = isEditing
        ? `${import.meta.env.VITE_APP_SERVER_URI}user/appointment/${formData.appointmentId}`
        : `${import.meta.env.VITE_APP_SERVER_URI}user/appointment`;

      const response = await fetchRequest(url, method, newFormData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (response.success) {
        if (isEditing) {
          setAppointments((prev) =>
            prev.map((appt) =>
              appt.appointmentId === newFormData.appointmentId ? { ...appt, ...newFormData } : appt
            )
          );
          toast.success("Appointment updated successfully!");
        } else {
          setAppointments((prev) => [
            ...prev,
            { ...newFormData, appointmentId: response.data?.appointmentId || Date.now() },
          ]);
          toast.success("Appointment added successfully!");
        }
        setShowModal(false);
      } else {
        throw new Error(response.message || "Operation failed");
      }
    } catch (error) {
      console.error("Save error:", {
        message: error.message,
        stack: error.stack,
      });
      toast.error(error.message || `Failed to ${isEditing ? "update" : "add"} appointment`);
    } finally {
      setIsLoading(false);
    }
  };

  const handleEditClick = (appointment) => {
    setFormData({
      appointmentId: appointment.appointmentId,
      physicianId: appointment.physicianId.toString(),
      nurseId: appointment.nurseId?.toString() || "0",
      patientId: appointment.patientId,
      startTime: new Date(appointment.startDate),
      examinationRoomId: appointment.examinationRoomId?.toString() || "0",
    });
    setIsEditing(true);
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to cancel this appointment?")) return;

    const token = localStorage.getItem("token");
    if (!token) {
      toast.error("You are not authorized. Please login.");
      return;
    }

    setIsLoading(true);
    try {
      const response = await fetchRequest(
        `${import.meta.env.VITE_APP_SERVER_URI}user/appointment/${id}`,
        "DELETE",
        null,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.success) {
        setAppointments((prev) => prev.filter((appt) => appt.appointmentId !== id));
        toast.success("Appointment canceled successfully!");
      } else {
        throw new Error(response.message || "Failed to cancel appointment");
      }
    } catch (error) {
      console.error("Delete error:", error);
      toast.error(error.message || "Failed to cancel appointment");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Layout>
      <div className="p-8">
        <h2 className="text-3xl font-semibold text-gray-900 mb-6">Appointments</h2>
        {user.role=="USER" && <button
          className="mb-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 disabled:opacity-50"
          onClick={() => {
            setIsEditing(false);
            setFormData({
              appointmentId: 0,
              physicianId: physicians[0]?.employeeId || "",
              nurseId: "0",
              patientId: user.role === "USER" ? user.id : "",
              startTime: new Date(),
              examinationRoomId: "0",
            });
            setShowModal(true);
          }}
          disabled={isLoading || !physicians.length}
        >
          Add Appointment
        </button>}
        <div className="overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
          <table className="w-full text-left border-collapse">
            <thead className="bg-blue-600 text-white">
              <tr>
                <th className="p-4">Appointment ID</th>
                <th className="p-4">Patient</th>
                <th className="p-4">Physician</th>
                <th className="p-4">Nurse</th>
                <th className="p-4">Start Time</th>
                <th className="p-4">Room</th>
                {user.role === "ADMIN" && <th className="p-4 text-center">Actions</th>}
              </tr>
            </thead>
            <tbody>
              {appointments.map((appt) => (
                <tr key={appt.appointmentId} className="border-b hover:bg-gray-50 transition-all">
                  <td className="p-4">{appt.appointmentId}</td>
                  <td className="p-4">
                    {patients.find((p) => p.id === appt.patientId)?.username || "N/A"}
                  </td>
                  <td className="p-4">
                    {physicians.find((p) => p.employeeId === appt.physicianId)?.name || "N/A"}
                  </td>
                  <td className="p-4">
                    {nurses.find((n) => n.empId === appt.nurseId)?.name || "N/A"}
                  </td>
                  <td className="p-4">
                    {appt.startDate
                      ? new Date(appt.startDate).toLocaleString("en-US", {
                          weekday: "short",
                          year: "numeric",
                          month: "short",
                          day: "numeric",
                          hour: "numeric",
                          minute: "numeric",
                          hour12: true,
                        })
                      : "N/A"}
                  </td>
                  <td className="p-4">
                    {rooms.find((r) => r.roomNo === appt.examinationRoomId)?.roomNo || "N/A"}
                  </td>
                  {user.role === "ADMIN" && (
                    <td className="p-4 flex justify-center gap-3">
                      <button
                        className="bg-yellow-500 text-white px-4 py-2 rounded-md hover:bg-yellow-600 disabled:opacity-50"
                        onClick={() => handleEditClick(appt)}
                        disabled={isLoading}
                      >
                        Edit
                      </button>
                      <button
                        className="bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700 disabled:opacity-50"
                        onClick={() => handleDelete(appt.appointmentId)}
                        disabled={isLoading}
                      >
                        Cancel
                      </button>
                    </td>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {showModal && (
        <div className="fixed inset-0 backdrop-blur-md flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg max-w-lg w-full relative">
            <button
              className="absolute top-2 right-2 text-gray-600 hover:text-gray-900"
              onClick={() => setShowModal(false)}
              disabled={isLoading}
            >
              ✖
            </button>
            <h2 className="text-2xl font-semibold mb-4 text-center">
              {isEditing ? "Edit" : "Add"} Appointment
            </h2>

            {/* {user.role === "ADMIN" && (
              <>
                <label className="block mb-2">Patient</label>
                <select
                  className="w-full p-2 border rounded mb-3"
                  value={formData.patientId}
                  onChange={(e) => setFormData({ ...formData, patientId: e.target.value })}
                  disabled={isLoading}
                >
                  <option value="" disabled>
                    Select a Patient
                  </option>
                  {patients.map((p) => (
                    <option key={p.id} value={p.id}>
                      {p.username}
                    </option>
                  ))}
                </select>
              </>
            )} */}

            <label className="block mb-2">Physician</label>
            <select
              className="w-full p-2 border rounded mb-3"
              value={formData.physicianId}
              onChange={(e) => setFormData({ ...formData, physicianId: e.target.value })}
              disabled={isLoading}
            >
              <option value="" disabled>
                Select a Physician
              </option>
              {physicians.map((p) => (
                <option key={p.employeeId} value={p.employeeId}>
                  {p.name}
                </option>
              ))}
            </select>

            {user.role=="ADMIN" && <label className="block mb-2">Nurse</label>}
            {user.role=="ADMIN" && <select
              className="w-full p-2 border rounded mb-3"
              value={formData.nurseId}
              onChange={(e) => setFormData({ ...formData, nurseId: e.target.value })}
              disabled={isLoading}
            >
              <option value="0">No Nurse</option>
              {nurses.map((n) => (
                <option key={n.empId} value={n.empId}>
                  {n.name}
                </option>
              ))}
            </select>}

            {user.role=="ADMIN" &&<label className="block mb-2">Room</label>}
        {user.role=="ADMIN" &&<select
          className="w-full p-2 border rounded mb-3"
          value={formData.examinationRoomId}
          onChange={(e) => setFormData({ ...formData, examinationRoomId: e.target.value })}
          disabled={isLoading}
        >
          <option value="0">No Room</option>
          {rooms.map((r) => (
            <option key={r.roomNo} value={r.roomNo}>
              {r.roomNo}
            </option>
          ))}
        </select>}

            <label className="block mb-2">Start Time</label>
            <DatePicker
              selected={formData.startTime}
              onChange={(date) => setFormData({ ...formData, startTime: date })}
              showTimeSelect
              dateFormat="Pp"
              className="w-full p-2 border rounded mb-3"
              disabled={isLoading}
            />

            <button
              onClick={handleSave}
              disabled={isLoading}
              className={`bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 w-full ${
                isLoading ? "opacity-50 cursor-not-allowed" : ""
              }`}
            >
              {isLoading ? "Saving..." : "Save"}
            </button>
          </div>
        </div>
      )}
    </Layout>
  );
}