document.addEventListener("DOMContentLoaded", () => {
  const BASE_URL = "http://localhost:8080";
  const DEBUG = true;

  // Add debug utility
  function debugLog(message, data) {
    if (DEBUG) {
      console.log(`[DEBUG] ${message}:`, data);
    }
  }

  const token = localStorage.getItem("jwt");

  if (!token) {
    alert("Unauthorized access. Please log in.");
    window.location.href = "login.html";
    return;
  }

  // Function to validate the token (basic example)
  function isTokenExpired(token) {
    const payload = JSON.parse(atob(token.split(".")[1]));
    const currentTime = Math.floor(Date.now() / 1000);
    return payload.exp < currentTime; // Assumes `exp` is in the payload
  }

  if (isTokenExpired(token)) {
    alert("Session expired! Please log in again.");
    localStorage.removeItem("jwt");
    window.location.href = "login.html";
  }

  const headers = {
    Authorization: `Bearer ${token}`,
  };

  debugLog("Request headers", headers);

  // Overview Elements
  const totalAdminsEl = document.getElementById("totalAdmins");
  const totalBloodBanksEl = document.getElementById("totalBloodBanks");
  const totalDonorsEl = document.getElementById("totalDonors");
  const totalDonationsEl = document.getElementById("totalDonations");

  // Blood Bank Table
  const bloodBankTableBody = document.getElementById("bloodBankTableBody");
  const searchBloodBank = document.getElementById("searchBloodBank");

  // Fetch and Display Overview Data
  async function fetchOverview() {
    try {
      const admins = await fetch(`${BASE_URL}/api/admins/getAll`, {
        headers,
      }).then(handleErrors);
      const bloodBanks = await fetch(`${BASE_URL}/api/blood-banks/getAll`, {
        headers,
        method: "GET",
      }).then(handleErrors);
      const donors = await fetch(`${BASE_URL}/api/donors/getAll`, {
        headers,
      }).then(handleErrors);
      const donations = await fetch(`${BASE_URL}/api/donation-records/getAll`, {
        headers,
      }).then(handleErrors);

      console.log("Response data:", { admins, bloodBanks }); // Debug response

      document.getElementById("totalAdmins").textContent = admins.length || 0;
      document.getElementById("totalBloodBanks").textContent =
        bloodBanks.length || 0;
      document.getElementById("totalDonors").textContent = donors.length || 0;
      document.getElementById("totalDonations").textContent =
        donations.length || 0;
    } catch (error) {
      console.error("Error details:", {
        message: error.message,
        status: error.status,
        statusText: error.statusText,
      });
      alert("Failed to load dashboard data. Please check console for details.");
    }
  }

  function handleErrors(response) {
    if (!response.ok) {
      if (response.status === 401) {
        alert("Unauthorized. Please log in again.");
        localStorage.removeItem("jwtToken"); // Clear invalid token
        window.location.href = "login.html";
      } else if (response.status === 403) {
        alert("Forbidden: You do not have access to this resource.");
      }
      throw new Error(`HTTP Error ${response.status}`);
    }
    return response.json();
  }

  // Fetch and Display Blood Banks
  async function fetchBloodBanks(query = "") {
    try {
      const url = query
        ? `${BASE_URL}/api/blood-banks/findByName/${query}`
        : `${BASE_URL}/api/blood-banks/getAll`;
      const bloodBanks = await fetch(url, { headers }).then(handleErrors);

      bloodBankTableBody.innerHTML = bloodBanks
        .map(
          (bank) => `
                <tr>
                    <td>${bank.name}</td>
                    <td>${bank.location}</td>
                    <td>${bank.contact}</td>
                    <td>
                        <button>Edit</button>
                        <button>Delete</button>
                    </td>
                </tr>
            `
        )
        .join("");
    } catch (error) {
      console.error("Blood bank fetch error:", error);
      alert("Failed to load blood banks.");
    }
  }

  // Event Listeners
  searchBloodBank.addEventListener("input", () => {
    fetchBloodBanks(searchBloodBank.value);
  });

  // Donor Management Elements
  const donorTableBody = document.getElementById("donorTableBody");
  const searchDonorInput = document.getElementById("searchDonor");

  // Fetch and Display Donors
  // Fetch and Display Donors
  async function fetchDonors(query = "") {
    try {
      const url = query
        ? `${BASE_URL}/api/donors/getAll?search=${query}`
        : `${BASE_URL}/api/donors/getAll`;
      const donors = await fetch(url, { headers }).then(handleErrors);

      donorTableBody.innerHTML = donors
        .map(
          (donor) => `
              <tr>
                <td>${donor.firstName} ${donor.lastName || "N/A"}</td>
                  <td>${donor.politicalId || "N/A"}</td>
                  <td>${donor.bloodGroup}</td>
                  <td>${donor.age}</td>
                  <td>${donor.lastDonationDate || "N/A"}</td>
                  <td>${donor.gender}</td>
                  <td>${donor.phoneNumber || "N/A"}</td>
                  <td>
                      <button class="edit-btn" data-id="${donor.politicalId
            }">Edit</button>
                      <button class="delete-btn" data-id="${donor.politicalId
            }">Delete</button>
                  </td>
              </tr>
          `
        )
        .join("");
    } catch (error) {
      console.error("Donor fetch error:", error);
      alert("Failed to load donors.");
    }
  }

  // Handle Donor Actions
  donorTableBody.addEventListener("click", async (event) => {
    if (event.target.classList.contains("delete-btn")) {
      const id = event.target.dataset.id;
      if (confirm("Are you sure you want to delete this donor?")) {
        try {
          await fetch(`${BASE_URL}/api/donors/delete/${id}`, {
            method: "DELETE",
            headers,
          });
          alert("Donor deleted successfully.");
          fetchDonors(); // Refresh table
        } catch (error) {
          console.error("Donor delete error:", error);
          alert("Failed to delete donor.");
        }
      }
    }
  });

  // Search Donors
  document.getElementById("donorSearchButton").addEventListener("click", () => {
    const query = searchDonorInput.value.trim();
    fetchDonors(query);
  });

  // Initial Load
  fetchDonors();

  // Donation Records Table Elements
  const donationRecordTableBody = document.getElementById(
    "donationRecordTableBody"
  );
  const searchDonationRecord = document.getElementById("searchDonationRecord");

  // Fetch and Display Donation Records
  async function fetchDonationRecords(query = "") {
    try {
      const url = query
        ? `${BASE_URL}/api/donation-records/get/${query}`
        : `${BASE_URL}/api/donation-records/getAll`;
      const donationRecords = await fetch(url, { headers }).then(handleErrors);

      donationRecordTableBody.innerHTML = donationRecords
        .map(
          (record) => `
                <tr>
                    <td>${record.politicalId}</td>
                    <td>${record.donationDate}</td>
                    <td>${record.bloodBankName}</td>
                    <td>
                        <button>Edit</button>
                        <button>Delete</button>
                    </td>
                </tr>
            `
        )
        .join("");
    } catch (error) {
      console.error("Donation records fetch error:", error);
      alert("Failed to load donation records.");
    }
  }

  // Search Donation Records
  searchDonationRecord.addEventListener("input", () => {
    fetchDonationRecords(searchDonationRecord.value);
  });

  // Load Donation Records on Page Load
  fetchDonationRecords();

  // Initial Load
  fetchOverview();
  fetchBloodBanks();
});
