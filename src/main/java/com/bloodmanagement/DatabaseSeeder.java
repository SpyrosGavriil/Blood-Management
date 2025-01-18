package com.bloodmanagement;

import com.bloodmanagement.model.*;
import com.bloodmanagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

        private final UserRepository userRepository;
        private final DonorRepository donorRepository;
        private final AdminRepository adminRepository;
        private final BloodBankRepository bloodBankRepository;
        private final DonationRecordRepository donationRecordRepository;
        private final PasswordEncoder passwordEncoder;

        @PostConstruct
        public void seedDatabase() {
                // Seed BloodBanks
                BloodBank bloodBank1 = BloodBank.builder()
                                .name("Central Blood Bank")
                                .location("Downtown City")
                                .build();

                BloodBank bloodBank2 = BloodBank.builder()
                                .name("Regional Blood Center")
                                .location("Northside Area")
                                .build();

                bloodBankRepository.saveAll(Arrays.asList(bloodBank1, bloodBank2));

                // Seed Users and Donors
                User user1 = User.builder()
                                .firstName("John")
                                .lastName("Doe")
                                .username("john_doe")
                                .politicalId(123456)
                                .password(passwordEncoder.encode("password123"))
                                .role(Role.USER)
                                .build();

                User user2 = User.builder()
                                .firstName("Jane")
                                .lastName("Smith")
                                .username("jane_smith")
                                .politicalId(234567)
                                .password(passwordEncoder.encode("password456"))
                                .role(Role.USER)
                                .build();

                userRepository.saveAll(Arrays.asList(user1, user2));

                Donor donor1 = Donor.builder()
                                .politicalId(user1.getPoliticalId())
                                .bloodGroup("A+")
                                .age(30)
                                .gender("Male")
                                .build();

                Donor donor2 = Donor.builder()
                                .politicalId(user2.getPoliticalId())
                                .bloodGroup("O-")
                                .age(28)
                                .gender("Female")
                                .build();

                donorRepository.saveAll(Arrays.asList(donor1, donor2));

                // Seed Admins
                Admin admin1 = Admin.builder()
                                .firstName("Admin")
                                .lastName("User")
                                .username("admin_user")
                                .politicalId(123456)
                                .password(passwordEncoder.encode("adminPass123"))
                                .role(Role.ADMIN)
                                .build();

                adminRepository.save(admin1);

                // Seed DonationRecords
                DonationRecord record1 = DonationRecord.builder()
                                .politicalId(donor1.getPoliticalId())
                                .bloodBank(bloodBank1)
                                .donationDate(LocalDate.of(2025, 1, 10)) // Corrected to LocalDate
                                .build();

                DonationRecord record2 = DonationRecord.builder()
                                .politicalId(donor2.getPoliticalId())
                                .bloodBank(bloodBank2)
                                .donationDate(LocalDate.of(2025, 1, 12)) // Corrected to LocalDate
                                .build();
                donationRecordRepository.saveAll(Arrays.asList(record1, record2));
        }
}