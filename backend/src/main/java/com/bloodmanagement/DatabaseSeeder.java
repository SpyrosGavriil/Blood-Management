package com.bloodmanagement;

import com.bloodmanagement.controller.DonationRecordController;
import com.bloodmanagement.model.*;
import com.bloodmanagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

        private final UserRepository userRepository;
        private final DonorRepository donorRepository;
        private final AdminRepository adminRepository;
        private final BloodBankRepository bloodBankRepository;
        private final PasswordEncoder passwordEncoder;
        private final DonationRecordController donationRecordController;

        @PostConstruct
        public void seedDatabase() {
                // Seed BloodBanks
                BloodBank bloodBank1 = BloodBank.builder()
                                .name("Central Blood Bank")
                                .location("Downtown City")
                                .contact("1234567890")
                                .build();

                BloodBank bloodBank2 = BloodBank.builder()
                                .name("Regional Blood Center")
                                .location("Northside Area")
                                .contact("2345678901")
                                .build();

                BloodBank bloodBank3 = BloodBank.builder()
                                .name("City Hospital Blood Bank")
                                .location("Eastside City")
                                .contact("3456789012")
                                .build();

                BloodBank bloodBank4 = BloodBank.builder()
                                .name("Metro Blood Services")
                                .location("West End")
                                .contact("4567890123")
                                .build();

                bloodBankRepository.saveAll(Arrays.asList(bloodBank1, bloodBank2, bloodBank3, bloodBank4));

                // Seed Users and Donors
                User user1 = User.builder()
                                .firstName("John")
                                .lastName("Doe")
                                .username("john_doe")
                                .politicalId(123456)
                                .password(passwordEncoder.encode("M3rcury#2025!"))
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

                User user3 = User.builder()
                                .firstName("Alice")
                                .lastName("Brown")
                                .username("alice_brown")
                                .politicalId(345678)
                                .password(passwordEncoder.encode("password789"))
                                .role(Role.USER)
                                .build();

                User user4 = User.builder()
                                .firstName("Bob")
                                .lastName("Johnson")
                                .username("bob_johnson")
                                .politicalId(456789)
                                .password(passwordEncoder.encode("password321"))
                                .role(Role.USER)
                                .build();

                User user5 = User.builder()
                                .firstName("Charlie")
                                .lastName("Davis")
                                .username("charlie_davis")
                                .politicalId(567890)
                                .password(passwordEncoder.encode("password654"))
                                .role(Role.USER)
                                .build();

                User user6 = User.builder()
                                .firstName("David")
                                .lastName("Miller")
                                .username("david_miller")
                                .politicalId(678901)
                                .password(passwordEncoder.encode("password987"))
                                .role(Role.USER)
                                .build();

                User user7 = User.builder()
                                .firstName("Emma")
                                .lastName("Wilson")
                                .username("emma_wilson")
                                .politicalId(789012)
                                .password(passwordEncoder.encode("password147"))
                                .role(Role.USER)
                                .build();

                User user8 = User.builder()
                                .firstName("Fiona")
                                .lastName("Taylor")
                                .username("fiona_taylor")
                                .politicalId(890123)
                                .password(passwordEncoder.encode("password258"))
                                .role(Role.USER)
                                .build();

                User user9 = User.builder()
                                .firstName("George")
                                .lastName("Anderson")
                                .username("george_anderson")
                                .politicalId(901234)
                                .password(passwordEncoder.encode("password369"))
                                .role(Role.USER)
                                .build();

                User user10 = User.builder()
                                .firstName("Hannah")
                                .lastName("Clark")
                                .username("hannah_clark")
                                .politicalId(123890)
                                .password(passwordEncoder.encode("password741"))
                                .role(Role.USER)
                                .build();

                List<User> users = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10);
                userRepository.saveAll(users);

                Donor donor1 = Donor.builder()
                                .politicalId(user1.getPoliticalId())
                                .bloodGroup("A+")
                                .age(30)
                                .gender("Male")
                                .phoneNumber(12345678L)
                                .build();

                Donor donor2 = Donor.builder()
                                .politicalId(user2.getPoliticalId())
                                .bloodGroup("O-")
                                .age(28)
                                .gender("Female")
                                .phoneNumber(23456789L)
                                .build();

                Donor donor3 = Donor.builder()
                                .politicalId(user3.getPoliticalId())
                                .bloodGroup("B+")
                                .age(35)
                                .gender("Female")
                                .phoneNumber(34567890L)
                                .build();

                Donor donor4 = Donor.builder()
                                .politicalId(user4.getPoliticalId())
                                .bloodGroup("AB-")
                                .age(40)
                                .gender("Male")
                                .phoneNumber(45678901L)
                                .build();

                Donor donor5 = Donor.builder()
                                .politicalId(user5.getPoliticalId())
                                .bloodGroup("O+")
                                .age(25)
                                .gender("Male")
                                .phoneNumber(56789012L)
                                .build();

                Donor donor6 = Donor.builder()
                                .politicalId(user6.getPoliticalId())
                                .bloodGroup("A-")
                                .age(29)
                                .gender("Male")
                                .phoneNumber(67890123L)
                                .build();

                Donor donor7 = Donor.builder()
                                .politicalId(user7.getPoliticalId())
                                .bloodGroup("AB+")
                                .age(32)
                                .gender("Female")
                                .phoneNumber(78901234L)
                                .build();

                Donor donor8 = Donor.builder()
                                .politicalId(user8.getPoliticalId())
                                .bloodGroup("B-")
                                .age(27)
                                .gender("Female")
                                .phoneNumber(89012345L)
                                .build();

                Donor donor9 = Donor.builder()
                                .politicalId(user9.getPoliticalId())
                                .bloodGroup("O-")
                                .age(31)
                                .gender("Male")
                                .phoneNumber(90123456L)
                                .build();

                Donor donor10 = Donor.builder()
                                .politicalId(user10.getPoliticalId())
                                .bloodGroup("A+")
                                .age(26)
                                .gender("Female")
                                .phoneNumber(12389012L)
                                .build();

                donorRepository.saveAll(Arrays.asList(donor1, donor2, donor3, donor4, donor5, donor6, donor7, donor8,
                                donor9, donor10));

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

                List<DonationRecord> donationRecords = Arrays.asList(
                                // Donations for Donor 1
                                DonationRecord.builder()
                                                .politicalId(donor1.getPoliticalId())
                                                .bloodBank(bloodBank1)
                                                .donationDate(LocalDate.of(2025, 1, 10))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor1.getPoliticalId())
                                                .bloodBank(bloodBank2)
                                                .donationDate(LocalDate.of(2025, 2, 5))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor1.getPoliticalId())
                                                .bloodBank(bloodBank3)
                                                .donationDate(LocalDate.of(2025, 3, 1))
                                                .build(),

                                // Donations for Donor 2
                                DonationRecord.builder()
                                                .politicalId(donor2.getPoliticalId())
                                                .bloodBank(bloodBank2)
                                                .donationDate(LocalDate.of(2025, 1, 12))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor2.getPoliticalId())
                                                .bloodBank(bloodBank4)
                                                .donationDate(LocalDate.of(2025, 2, 10))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor2.getPoliticalId())
                                                .bloodBank(bloodBank1)
                                                .donationDate(LocalDate.of(2025, 3, 8))
                                                .build(),

                                // Donations for Donor 3
                                DonationRecord.builder()
                                                .politicalId(donor3.getPoliticalId())
                                                .bloodBank(bloodBank3)
                                                .donationDate(LocalDate.of(2025, 1, 15))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor3.getPoliticalId())
                                                .bloodBank(bloodBank1)
                                                .donationDate(LocalDate.of(2025, 2, 20))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor3.getPoliticalId())
                                                .bloodBank(bloodBank2)
                                                .donationDate(LocalDate.of(2025, 3, 25))
                                                .build(),

                                // Donations for Donor 4
                                DonationRecord.builder()
                                                .politicalId(donor4.getPoliticalId())
                                                .bloodBank(bloodBank4)
                                                .donationDate(LocalDate.of(2025, 1, 18))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor4.getPoliticalId())
                                                .bloodBank(bloodBank3)
                                                .donationDate(LocalDate.of(2025, 2, 18))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor4.getPoliticalId())
                                                .bloodBank(bloodBank2)
                                                .donationDate(LocalDate.of(2025, 3, 18))
                                                .build(),

                                // Donations for Donor 5
                                DonationRecord.builder()
                                                .politicalId(donor5.getPoliticalId())
                                                .bloodBank(bloodBank1)
                                                .donationDate(LocalDate.of(2025, 1, 20))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor5.getPoliticalId())
                                                .bloodBank(bloodBank2)
                                                .donationDate(LocalDate.of(2025, 2, 24))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor5.getPoliticalId())
                                                .bloodBank(bloodBank3)
                                                .donationDate(LocalDate.of(2025, 3, 28))
                                                .build(),

                                // Donations for Donor 6
                                DonationRecord.builder()
                                                .politicalId(donor6.getPoliticalId())
                                                .bloodBank(bloodBank2)
                                                .donationDate(LocalDate.of(2025, 1, 22))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor6.getPoliticalId())
                                                .bloodBank(bloodBank1)
                                                .donationDate(LocalDate.of(2025, 2, 10))
                                                .build(),
                                DonationRecord.builder()
                                                .politicalId(donor6.getPoliticalId())
                                                .bloodBank(bloodBank4)
                                                .donationDate(LocalDate.of(2025, 3, 14))
                                                .build());

                // Loop to create all donation records
                for (DonationRecord record : donationRecords) {
                        donationRecordController.createDonationRecord(record);
                }

        }
}
