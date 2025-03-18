package com.hms.user_service.controller;

import com.hms.user_service.model.Appointment;
import com.hms.user_service.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/appointment")
    public ResponseEntity<?> addAppointment(@RequestBody Appointment appointment) {
        System.out.println(appointment.getPatientId());
        System.out.println(appointment.getNurseId());
        System.out.println(appointment.getPhysicianId());
        System.out.println(appointment.getExaminationRoomId());
        System.out.println(appointment.getStartDate());
        Appointment ap = appointmentService.addAppointment(appointment);
        if (ap == null) {
            return ResponseEntity.badRequest().body("Failed to add appointment");
        }
        return ResponseEntity.ok(ap);
    }

    @GetMapping("/appointment")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/appointment/{startdate}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStartDate(@PathVariable Date startdate) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByStartDate(startdate));
    }

    @GetMapping("/appointment/patient/{appointmentid}")
    public ResponseEntity<Appointment> getPatientByAppointmentId(@PathVariable int appointmentid) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(appointmentid);
        return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/appointment/physician/{appointmentid}")
    public ResponseEntity<Appointment> getPhysicianByAppointmentId(@PathVariable int appointmentid) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(appointmentid);
        return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/appointment/nurse/{appointmentid}")
    public ResponseEntity<Appointment> getNurseByAppointmentId(@PathVariable int appointmentid) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(appointmentid);
        return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/appointment/examinationroom/{appointmentid}")
    public ResponseEntity<Appointment> getExaminationRoomByAppointmentId(@PathVariable int appointmentid) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(appointmentid);
        return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/appointment/physician/{patientid}")
    public ResponseEntity<List<Appointment>> getPhysiciansByPatientId(@PathVariable int patientid) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientid));
    }

    @GetMapping("/appointment/physician/{patientid}/{date}")
    public ResponseEntity<List<Appointment>> getPhysicianByPatientIdAndDate(@PathVariable int patientid, @PathVariable Date date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientIdAndDate(patientid, date));
    }

    @GetMapping("/appointment/nurse/{patientid}")
    public ResponseEntity<List<Appointment>> getNursesByPatientId(@PathVariable int patientid) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientid));
    }

    @GetMapping("/appointment/nurse/{patientid}/{date}")
    public ResponseEntity<List<Appointment>> getNurseByPatientIdAndDate(@PathVariable int patientid, @PathVariable Date date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientIdAndDate(patientid, date));
    }

    @GetMapping("/appointment/date/{patientid}")
    public ResponseEntity<List<Date>> getAppointmentDatesByPatientId(@PathVariable int patientid) {
        return ResponseEntity.ok(appointmentService.getAppointmentDatesByPatientId(patientid));
    }

    @GetMapping("/appointment/patient/{physicianid}")
    public ResponseEntity<List<Appointment>> getPatientsByPhysicianId(@PathVariable int physicianid) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPhysicianId(physicianid));
    }

    @GetMapping("/appointment/patient/{physicianid}/{date}")
    public ResponseEntity<List<Appointment>> getPatientsByPhysicianIdAndDate(@PathVariable int physicianid, @PathVariable Date date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPhysicianIdAndDate(physicianid, date));
    }

    @GetMapping("/appointment/patient/{physicianid}/{patientid}")
    public ResponseEntity<Appointment> getPatientByPhysicianIdAndPatientId(@PathVariable int physicianid, @PathVariable int patientid) {
        Optional<Appointment> appointment = appointmentService.getAppointmentByPhysicianIdAndPatientId(physicianid, patientid);
        return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/appointment/patient/{nurseid}")
    public ResponseEntity<List<Appointment>> getPatientsByNurseId(@PathVariable int nurseid) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByNurseId(nurseid));
    }

    @GetMapping("/appointment/patient/{nurseid}/{patientid}")
    public ResponseEntity<Appointment> getPatientByNurseIdAndPatientId(@PathVariable int nurseid, @PathVariable int patientid) {
        Optional<Appointment> appointment = appointmentService.getAppointmentByNurseIdAndPatientId(nurseid, patientid);
        return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/appointment/patient/{nurseid}/{date}")
    public ResponseEntity<List<Appointment>> getPatientsByNurseIdAndDate(@PathVariable int nurseid, @PathVariable Date date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByNurseIdAndDate(nurseid, date));
    }

    @GetMapping("/appointment/room/{patientid}/{date}")
    public ResponseEntity<List<Appointment>> getRoomByPatientIdAndDate(@PathVariable int patientid, @PathVariable Date date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientIdAndDate(patientid, date));
    }

    @GetMapping("/appointment/room/{physicianid}/{date}")
    public ResponseEntity<List<Appointment>> getRoomsByPhysicianIdAndDate(@PathVariable int physicianid, @PathVariable Date date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPhysicianIdAndDate(physicianid, date));
    }

    @GetMapping("/appointment/room/{nurseid}/{date}")
    public ResponseEntity<List<Appointment>> getRoomsByNurseIdAndDate(@PathVariable int nurseid, @PathVariable Date date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByNurseIdAndDate(nurseid, date));
    }

    @PutMapping("/appointment/room/{appointmentid}")
    public ResponseEntity<Appointment> updateExaminationRoom(@PathVariable int appointmentid, @RequestBody int roomId) {
        Optional<Appointment> updatedAppointment = appointmentService.updateExaminationRoom(appointmentid, roomId);
        return updatedAppointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/appointment/{appointmentid}")
    public ResponseEntity<?> updateAppointment(
            @PathVariable int appointmentid,
            @RequestBody Appointment appointment) {
        // Ensure the appointment ID in the path matches the one in the body
        if (appointment.getAppointmentId() != appointmentid) {
            return ResponseEntity.badRequest().body("Appointment ID in path and body must match");
        }

        Optional<Appointment> existingAppointment = appointmentService.getAppointmentById(appointmentid);
        if (!existingAppointment.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Appointment updatedAppointment = appointmentService.updateAppointment(appointment);
        if (updatedAppointment == null) {
            return ResponseEntity.badRequest().body("Failed to update appointment");
        }
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/appointment/{appointmentid}")
    public ResponseEntity<?> deleteAppointment(@PathVariable int appointmentid) {
        boolean isDeleted = appointmentService.deleteAppointment(appointmentid);
        if (isDeleted) {
            return ResponseEntity.ok(appointmentid);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}