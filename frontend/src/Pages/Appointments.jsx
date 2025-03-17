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
  const user = JSON.parse(localStorage.getItem("user"));
  const [patients, setPatients] = useState([]);
  const [physicians, setPhysicians] = useState([]);
  const [nurses, setNurses] = useState([]);
  const [availableRooms, setAvailableRooms] = useState([]);
  const [formData, setFormData] = useState({
    patientId: "",
    physicianId: physicians.length > 0 ? physicians[0].id : "",
    nurseId: "",
    startTime: new Date(),
    endTime: new Date(),
    examinationRoomId: "",
  });

  useEffect(() => {
    // Fetch appointments
    // fetchRequest(import.meta.env.VITE_APP_SERVER_URI + "user/appointment", "GET")
    //   .then((response) => {
    //     console.log(response.data);
    //     if (response.success) {
    //       setAppointments(response.data);
    //     } else {
    //       toast.error("Unable to fetch appointments");
    //       setAppointments([
    //         {
    //           appointmentId: 1,
    //           patientId: 1,
    //           physicianId: 3,
    //           nurseId: 2,
    //           startDate: "2025-03-14T10:00:00",
    //           endDate: "2025-03-14T10:30:00",
    //           examinationRoomId: 3,
    //         },
    //         {
    //           appointmentId: 2,
    //           patientId: 1,
    //           physicianId: 3,
    //           nurseId: 1,
    //           startDate: "2025-03-14T10:00:00",
    //           endDate: "2025-03-14T10:30:00",
    //           examinationRoomId: 2,
    //         },
    //       ]);
    //     }
    //   })
    //   .catch(() => {
    //     toast.error("Unable to fetch appointments");
    //   });
    // fetchRequest(import.meta.env.VITE_APP_SERVER_URI + "user/appointment", "GET")
    // .then((response) => {
    //   console.log(response.data); 
    //   if (response.success && response.data) {
    //     setAppointments(response.data);  
    //     toast.error("Unable to fetch appointments");
    //   }
    // })
    // .catch(() => {
    //   toast.error("Unable to fetch appointments");
    // });
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI + "user/appointment", "GET")
      .then((response) => {
        console.log(response.data);
        if (response.success && response.data) {
          if (user.role === "USER") {
            setAppointments(response.data.filter((appt) => appt.patientId === user.id));
            console.log(user.id);
          } else {
            setAppointments(response.data);
          }
        } else {
          toast.error("Unable to fetch appointments");
        }
      })
      .catch(() => {
        toast.error("Unable to fetch appointments");
      });

    fetchRequest(import.meta.env.VITE_APP_SERVER_URI + "user/getAll", "GET").then((response) => {
      console.log(response.data);  
      if (response.success && response.data) {
        setPatients(response.data); 
      } else {
        toast.error("Unable to fetch patients");
      }
    });

    // Fetch nurses
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI + "user/nurse", "GET").then((response) => {
      console.log(response.data);
      if (response.success) {
        setNurses(response.data);
      } else {
        toast.error("Unable to fetch nurses");
        setNurses([
          {
            id: "2",
            name: "Nello O'niel",
            position: "Nurse",
            ssn: "123456789",
            registered: true,
          },
        ]);
      }
    });

    // Fetch physicians
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI + "user/physicians", "GET").then((response) => {
      console.log(response.data);
      if (response.success) {
        setPhysicians(response.data);
      } else {
        toast.error("Unable to fetch physicians");
        setPhysicians([
          {
            id: "3",
            name: "Dr. Br Ambedkar",
            position: "Physician",
            ssn: "123456789",
            registered: true,
          },
          {
            id: "4",
            name: "Dr. Cr Ambedkar",
            position: "Physician",
            ssn: "123456789",
            registered: true,
          },
        ]);
      }
    });

    // Fetch rooms
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI + "user/room/all", "GET").then((response) => {
      console.log(response.data);
      if (response.success) {
        setAvailableRooms(response.data);
      } else {
        toast.error("Unable to fetch rooms");
        setAvailableRooms([
          {
            id: "3",
            roomNo: 101,
            booked: false,
          },
          {
            id: "4",
            roomNo: 105,
            booked: true,
          },
        ]);
      }
    });
  }, []);

  // const handleSave = () => {
  //   let newFormData = {
  //     ...formData,
  //     startTime: formData.startTime.toISOString(),
  //     patientId: user.id,
  //   };
  //   console.log("Form Data:", newFormData);
  //   if (isEditing) {
  //     setAppointments((prev) =>
  //       prev.map((appt) => (appt.appointmentId === formData.appointmentId ? { ...formData } : appt))
  //     );
  //     toast.success("Appointment updated successfully!");
  //   } else {
  //     setAppointments((prev) => [...prev, { ...formData, appointmentId: Date.now() }]);
  //     toast.success("Appointment added successfully!");
  //   }
  //   setShowModal(false);
  // };

  const handleSave = () => {
    let newFormData = {
      appointmentId: 0, 
      physicianId: formData.physicianId,
      nurseId: 0,
      patientId: user.id, 
      startDate: formData.startTime.toISOString(), 
      examinationRoomId: 0,
    };
  
    console.log("Form Data:", newFormData);
  
    const token = localStorage.getItem("token");
  
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI + "user/appointment", "POST", newFormData)
      .then((response) => {
        if (response.success) {
          setAppointments((prev) => [
            ...prev,
            { ...newFormData, appointmentId: Date.now() }, 
          ]);
          toast.success("Appointment added successfully!");
        } else {
          toast.error("Failed to add appointment!");
        }
      })
      .catch(() => {
        toast.error("An error occurred while adding the appointment.");
      });
  
    setShowModal(false); // Close the modal after saving
  };
  
  

  const handleEditClick = (appointment) => {
    console.log("Editing Appointment:", appointment);
    setFormData({
      ...appointment,
      startTime: new Date(appointment.startDate),
      endTime: new Date(appointment.endDate),
    });
    setIsEditing(true);
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    const token = localStorage.getItem("token");
  
    if (!token) {
      toast.error("You are not authorized. Please login.");
      return;
    }
    fetchRequest(import.meta.env.VITE_APP_SERVER_URI + `user/appointment/${id}`, "DELETE", {
      headers: {
        'Authorization': `Bearer ${token}`,  
      }
    })
      .then((response) => {
        console.log(response.data);
        if (response.success) {
          setAppointments((prev) => prev.filter((appt) => appt.appointmentId !== id));
  
          toast.success("Appointment canceled!");
        } else {
          toast.error("Failed to cancel appointment!");
        }
      })
      .catch((error) => {
        console.error('There was an error deleting the appointment:', error);
        toast.error("Failed to cancel appointment!");
      });
  };
  
  
  

  return (
    <Layout>
      <div className="p-8">
        <h2 className="text-3xl font-semibold text-gray-900 mb-6">Appointments</h2>
        {user.role === "USER" && (
          <button
            className="mb-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
            onClick={() => {
              setIsEditing(false);
              setFormData({
                patientId: "",
                physicianId: physicians.length > 0 ? physicians[0].id : "",
                nurseId: "",
                startTime: new Date(),
                examinationRoomId: "",
              });
              setShowModal(true);
            }}
          >
            Add Appointment
          </button>
        )}
        <div className="overflow-x-auto bg-white shadow-lg rounded-lg border border-gray-200">
          <table className="w-full text-left border-collapse">
            <thead className="bg-blue-600 text-white">
              <tr>
                {user.role === "ADMIN" && <th className="p-4">Patient</th>}
                <th className="p-4">Physician</th>
                <th className="p-4">Nurse</th>
                <th className="p-4">Start Time</th>
                <th className="p-4">Room</th>
                {user.role === "ADMIN" && <th className="p-4 text-center">Actions</th>}
              </tr>
            </thead>
            {/* <tbody>
              {appointments.map((appt) => (
                console.log(appt),
                <tr key={appt.appointmentId} className="border-b hover:bg-gray-50 transition-all">
                  {user.role === "ADMIN" && <td className="p-4">{appt.patientId}</td>}
                  <td className="p-4">{physicians.find((p) => p.empId === appt.employeeId)?.name}</td>
                  <td className="p-4">{nurses.find((n) => n.id === appt.empId)?.name}</td>
                  <td className="p-4">
                    {new Date(appt.startDate).toLocaleString('en-US', {
                      weekday: 'short', // 'long' or 'short' to display weekday
                      year: 'numeric',
                      month: 'short',   // 'long' or 'short' to display month
                      day: 'numeric',
                      hour: 'numeric',
                      minute: 'numeric',
                      second: 'numeric',
                      hour12: true,     // Use 12-hour format if desired (set to false for 24-hour format)
                    })}
                  </td>
                  <td className="p-4">{availableRooms.find((r) => r.id === appt.roomNo)?.roomNo}</td>
                  {user.role === "ADMIN" && (
                    <td className="p-4 flex justify-center gap-3">
                      <button
                        className="bg-yellow-500 text-white px-4 py-2 rounded-md hover:bg-yellow-600"
                        onClick={() => handleEditClick(appt)}
                      >
                        Edit
                      </button>

                      <button
                        className="bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700"
                        onClick={() => handleDelete(appt.appointmentId)}
                      >
                        Cancel
                      </button>
                    </td>
                  )}
                </tr>
              ))}
            </tbody> */}
            <tbody>
  {appointments.map((appt) => (
    console.log(appt),
    // <tr key={appt.appointmentId} className="border-b hover:bg-gray-50 transition-all">
    //   {user.role === "ADMIN" && <td className="p-4">{appt.patientId}</td>}
    //   <td className="p-4">{appt.physicianId}</td>
    //   <td className="p-4">{appt.nurseId}</td>
    //   <td className="p-4">
    //     {new Date(appt.startDate).toLocaleString('en-US', {
    //       weekday: 'short', 
    //       year: 'numeric',
    //       month: 'short',   
    //       day: 'numeric',
    //       hour: 'numeric',
    //       minute: 'numeric',
    //       second: 'numeric',
    //       hour12: true,     
    //     })}
    //   </td>
    //   <td className="p-4">{appt.examinationRoomId}</td>
    //   {user.role === "ADMIN" && (
    //     <td className="p-4 flex justify-center gap-3">
    //       <button
    //         className="bg-yellow-500 text-white px-4 py-2 rounded-md hover:bg-yellow-600"
    //         onClick={() => handleEditClick(appt)}
    //       >
    //         Edit
    //       </button>
    //       <button
    //         className="bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700"
    //         onClick={() => handleDelete(appt.appointmentId)}
    //       >
    //         Cancel
    //       </button>
    //     </td>
    //   )}
    // </tr>
    <tr key={appt.appointmentId} className="border-b hover:bg-gray-50 transition-all">
  {user.role === "ADMIN" && (
    <td className="p-4">
      {
        patients.find((patients) => patients.id === appt.patientId)?.username || "N/A"
      }
    </td>
  )}

  <td className="p-4">
    {
      physicians.find((physicians) => physicians.employeeId === appt.physicianId)?.name || "N/A"
    }
  </td>

  <td className="p-4">
    {
      // Find the nurse name using the nurseId
      nurses.find((nurses) => nurses.empId === appt.nurseId)?.name || "N/A"
    }
  </td>

  <td className="p-4">
    {new Date(appt.startDate).toLocaleString('en-US', {
      weekday: 'short',
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric',
      second: 'numeric',
      hour12: true,
    })}
  </td>

  <td className="p-4">
    {
      availableRooms.find((rooms) => rooms.roomNo === appt.examinationRoomId)?.roomNo || "N/A"
    }
  </td>

  {user.role === "ADMIN" && (
    <td className="p-4 flex justify-center gap-3">
      <button
        className="bg-yellow-500 text-white px-4 py-2 rounded-md hover:bg-yellow-600"
        onClick={() => handleEditClick(appt)}
      >
        Edit
      </button>

      <button
        className="bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700"
        onClick={() => handleDelete(appt.appointmentId)}
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
            <button className="absolute top-2 right-2 text-gray-600 hover:text-gray-900" onClick={() => setShowModal(false)}>
              ✖
            </button>
            <h2 className="text-2xl font-semibold mb-4 text-center">{isEditing ? "Edit" : "Add"} Appointment</h2>
            <label className="block mb-2">Physician</label>
            <select
              className="w-full p-2 border rounded mb-3"
              value={formData.physicianId}
              onChange={(e) => setFormData({ ...formData, physicianId: parseInt(e.target.value) })}
            >
              <option value="" disabled>
                Select a Physician
              </option>
              {physicians.map((p, i) => (
                <option key={i} value={p.employeeId}>
                  {p.name}
                </option>
              ))}
            </select>
            {user.role === "ADMIN" && <label className="block mb-2">Nurse</label>}
            {user.role === "ADMIN" && (
              <select
                className="w-full p-2 border rounded mb-3"
                value={formData.nurseId}
                onChange={(e) => setFormData({ ...formData, nurseId: e.target.value })}
              >
                <option value="" disabled>
                  Select a Nurse
                </option>
                {nurses.map((n, i) => (
                  <option key={i} value={n.id}>
                    {n.name}
                  </option>
                ))}
              </select>
            )}
            <label className="block mb-2">Start Time</label>
            <DatePicker
              selected={formData.startTime}
              onChange={(date) => setFormData({ ...formData, startTime: date })}
              showTimeSelect
              dateFormat="Pp"
              className="w-full p-2 border rounded mb-3"
            />
            {/* <label className="block mb-2">End Time</label>
            <DatePicker selected={formData.endTime} onChange={(date) => setFormData({ ...formData, endTime: date})} showTimeSelect dateFormat="Pp" className="w-full p-2 border rounded mb-3" /> */}
            {user.role === "ADMIN" && <label className="block mb-2">Examination Room</label>}
            {user.role === "ADMIN" && (
              <select
                className="w-full p-2 border rounded mb-3"
                value={formData.examinationRoomId}
                onChange={(e) => setFormData({ ...formData, examinationRoomId: e.target.value })}
              >
                <option value="" disabled>
                  Select a room no
                </option>
                {availableRooms
                  .filter((r) => r.booked === false)
                  .map((r, i) => (
                    <option key={i} value={r.id}>
                      {r.roomNo}
                    </option>
                  ))}
              </select>
            )}
            <button
              onClick={handleSave}
              className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            >
              Save
            </button>
          </div>
        </div>
      )}
    </Layout>
  );
}
