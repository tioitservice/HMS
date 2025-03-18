package com.hms.user_service.service;

import com.hms.user_service.model.Appointment;
import com.hms.user_service.model.Room;
import com.hms.user_service.repo.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final RoomService roomService; // Added dependency

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, RoomService roomService) {
        this.appointmentRepository = appointmentRepository;
        this.roomService = roomService;
    }

    public Appointment addAppointment(Appointment appointment) {
        // Check if the room exists and is not booked

//        Optional<Room> room = roomService.getRoomByNo(appointment.getExaminationRoomId());
//        if(room.get().getRoomNo()!=0){
//
//            if (room.isEmpty()) {
//                throw new IllegalArgumentException("Room does not exist");
//            }
//            if (room.get().isBooked()) {
//                throw new IllegalArgumentException("Room is already booked");
//            }
//        }
//
//        // Check if the room is available at the specified time
//        List<Appointment> existingAppointments = appointmentRepository
//                .findByExaminationRoomIdAndStartDate(appointment.getExaminationRoomId(), appointment.getStartDate());
//        if (!existingAppointments.isEmpty()) {
//            throw new IllegalArgumentException("Room is booked for this time slot");
//        }
//
//        // Book the room and save the appointment
//        roomService.updateRoomStatus(appointment.getExaminationRoomId(), true);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsByStartDate(Date startDate) {
        return appointmentRepository.findByStartDate(startDate);
    }

    public Optional<Appointment> getAppointmentById(int appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByPhysicianId(int physicianId) {
        return appointmentRepository.findByPhysicianId(physicianId);
    }

    public List<Appointment> getAppointmentsByNurseId(int nurseId) {
        return appointmentRepository.findByNurseId(nurseId);
    }

    public List<Appointment> getAppointmentsByRoomId(int roomId) {
        return appointmentRepository.findByExaminationRoomId(roomId);
    }

    public List<Appointment> getAppointmentsByPhysicianIdAndDate(int physicianId, Date date) {
        return appointmentRepository.findByPhysicianIdAndStartDate(physicianId, date);
    }

    public List<Appointment> getAppointmentsByNurseIdAndDate(int nurseId, Date date) {
        return appointmentRepository.findByNurseIdAndStartDate(nurseId, date);
    }

    public List<Appointment> getAppointmentsByPatientIdAndDate(int patientId, Date date) {
        return appointmentRepository.findByPatientIdAndStartDate(patientId, date);
    }

    public List<Date> getAppointmentDatesByPatientId(int patientId) {
        return appointmentRepository.findDatesByPatientId(patientId);
    }

    public Optional<Appointment> getAppointmentByPhysicianIdAndPatientId(int physicianId, int patientId) {
        return appointmentRepository.findByPhysicianIdAndPatientId(physicianId, patientId);
    }

    public Optional<Appointment> getAppointmentByNurseIdAndPatientId(int nurseId, int patientId) {
        return appointmentRepository.findByNurseIdAndPatientId(nurseId, patientId);
    }

    public Optional<Appointment> updateExaminationRoom(int appointmentId, int roomId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            Appointment updatedAppointment = appointment.get();
            // Free the old room
            roomService.updateRoomStatus(updatedAppointment.getExaminationRoomId(), true);

            // Check if the new room is available
            Optional<Room> newRoom = roomService.getRoomByNo(roomId);
            if (newRoom.isEmpty() || newRoom.get().isBooked()) {
                throw new IllegalArgumentException("New room is not available");
            }

            // Check for time conflicts in the new room
            List<Appointment> conflictingAppointments = appointmentRepository
                    .findByExaminationRoomIdAndStartDate(roomId, updatedAppointment.getStartDate());
            if (!conflictingAppointments.isEmpty() &&
                    conflictingAppointments.stream().noneMatch(a -> a.getAppointmentId() == appointmentId)) {
                throw new IllegalArgumentException("New room is booked for this time slot");
            }

            // Book the new room
            roomService.updateRoomStatus(roomId, true);
            updatedAppointment.setExaminationRoomId(roomId);
            return Optional.of(appointmentRepository.save(updatedAppointment));
        }
        return Optional.empty();
    }
    public Appointment updateAppointment(Appointment appointment) {
        Optional<Appointment> existingAppointment = appointmentRepository.findById(appointment.getAppointmentId());
        if (existingAppointment.isPresent()) {
            // Update the existing appointment with new values
            Appointment updated = existingAppointment.get();
            updated.setPatientId(appointment.getPatientId());
            updated.setPhysicianId(appointment.getPhysicianId());
            updated.setNurseId(appointment.getNurseId());
            updated.setExaminationRoomId(appointment.getExaminationRoomId());
            updated.setStartDate(appointment.getStartDate());
            // Add other fields as needed
            return appointmentRepository.save(updated);
        }
        return null; // Or throw an exception if preferred
    }
    public boolean deleteAppointment(int appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            Appointment appt = appointment.get();

            // Assuming Appointment has a reference to a room number (examinationRoomNo)
            int roomNo = appt.getExaminationRoomId(); // Adjust as needed to get the room number from the appointment

            // Use the updateRoomStatus method to mark the room as available
            Optional<Room> roomUpdate = roomService.updateRoomStatus(roomNo, false);  // false means the room is available

            if (roomUpdate.isPresent()) {
                // Room successfully updated
                System.out.println("Room status updated to available");
            } else {
                System.out.println("Room not found or unable to update");
            }
            appointmentRepository.delete(appointment.get());
            return true;
        } else {
            return false; // Appointment not found
        }
    }
}