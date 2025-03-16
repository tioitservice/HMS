package com.hms.user_service;

import com.hms.user_service.model.Active;
import com.hms.user_service.model.Role;
import com.hms.user_service.model.Room;
import com.hms.user_service.model.User;
import com.hms.user_service.repo.RoomRepository;
import com.hms.user_service.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserServiceApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoomRepository roomRepository;

	public UserServiceApplication(UserRepository userRepository, RoomRepository roomRepository) {
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Create the admin user if it doesn't exist
		final String pass = "$2a$10$QxmK5K.KadeuXZl/OtlO9ulk3fE8F/awkz2k02wqn5N8gUG9iDiz6"; // admin123
		var admin = User.builder()
				.username("admin")
				.email("admin@gmail.com")
				.password(pass)
				.active(Active.ACTIVE)
				.role(Role.ADMIN)
				.build();
		if (userRepository.findByUsername("admin").isEmpty()) {
			userRepository.save(admin);
		}

		// Create 10-15 rooms if they don't exist
		for (int i = 101; i <= 115; i++) {
			if (!roomRepository.existsById(i)) {
				Room room = new Room();
				room.setRoomNo(i);
				room.setBooked(false); // Set initial booking status
				roomRepository.save(room);
				System.out.println("Room " + i + " added at startup.");
			} else {
				System.out.println("Room " + i + " already exists.");
			}
		}
	}
}
