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

  // Blood Bank Table
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
                        <button class="edit-btn" data-id="${bank.name}">Edit</button>
                        <button class="delete-btn" data-id="${bank.name}">Delete</button>
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

  // Donor Management Elements
  const donorTableBody = document.getElementById("donorTableBody");

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
                      <button class="edit-btn" data-id="${
                        donor.politicalId
                      }">Edit</button>
                      <button class="delete-btn" data-id="${
                        donor.politicalId
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
            <td>${record.bloodBankName}</td>
            <td>${record.donationDate}</td>
            <td>
              <button class="edit-btn" data-id="${record.id}">Edit</button>
              <button class="delete-btn" data-id="${record.id}">Delete</button>
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

  // Handle Blood Bank Actions
  const bloodBankTableBody = document.getElementById("bloodBankTableBody");
  bloodBankTableBody.addEventListener("click", async (event) => {
    if (event.target.classList.contains("delete-btn")) {
      const name = event.target.dataset.id;
      if (confirm("Are you sure you want to delete this blood bank?")) {
        try {
          await fetch(`${BASE_URL}/api/blood-banks/delete/${name}`, {
            method: "DELETE",
            headers,
          });
          alert("Blood bank deleted successfully.");
          fetchBloodBanks(); // Refresh table
        } catch (error) {
          console.error("Blood bank delete error:", error);
          alert("Failed to delete blood bank.");
        }
      }
    }
  });

  // Handle Donation Record Actions
  const donationRecordTableBody = document.getElementById(
    "donationRecordTableBody"
  );
  donationRecordTableBody.addEventListener("click", async (event) => {
    const target = event.target;

    if (target.classList.contains("delete-btn")) {
      const id = target.dataset.id; // Unique ID of the donation record
      if (confirm("Are you sure you want to delete this donation record?")) {
        try {
          // Send DELETE request to the backend
          await fetch(`${BASE_URL}/api/donation-records/delete/${id}`, {
            method: "DELETE",
            headers: headers,
          });
          alert("Donation record deleted successfully.");
          fetchDonors(); // Refresh donor table
          fetchDonationRecords(); // Refresh table
        } catch (error) {
          console.error("Donation record delete error:", error);
          alert("Failed to delete donation record.");
        }
      }
    }
  });

  document
    .getElementById("newBloodBankButton")
    .addEventListener("click", () => {
      const name = prompt("Enter Blood Bank Name:");
      const location = prompt("Enter Blood Bank Location:");
      const contact = prompt("Enter Blood Bank Contact:");
      if (name && location && contact) {
        fetch(`${BASE_URL}/api/blood-banks/create`, {
          method: "POST",
          headers: {
            ...headers,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ name, location, contact }),
        })
          .then(handleErrors)
          .then(() => {
            alert("Blood Bank created successfully!");
            fetchBloodBanks(); // Refresh table
          })
          .catch((error) => alert("Failed to create Blood Bank."));
      }
    });

  document
    .getElementById("newDonationRecordButton")
    .addEventListener("click", () => {
      const politicalId = prompt("Enter Donor ID:");
      const bloodBankName = prompt("Enter Blood Bank Name:");
      const donationDate = prompt("Enter Donation Date (YYYY-MM-DD):");

      if (politicalId && bloodBankName && donationDate) {
        fetch(`${BASE_URL}/api/donation-records/create`, {
          method: "POST",
          headers: {
            ...headers,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            politicalId,
            bloodBank: {
              name: bloodBankName,
            },
            donationDate,
          }),
        })
          .then(handleErrors)
          .then(() => {
            alert("Donation Record created successfully!");
            fetchDonors(); // Refresh
            fetchDonationRecords(); // Refresh table
          })
          .catch((error) => {
            console.error("Error creating donation record:", error);
            alert("Failed to create Donation Record.");
          });
      }
    });

  // Scroll and highlight rows for Blood Banks
  document.getElementById("searchButton").addEventListener("click", () => {
    const query = document
      .getElementById("searchBloodBank")
      .value.trim()
      .toLowerCase();
    const rows = document.querySelectorAll("#bloodBankTableBody tr");

    let found = false;
    rows.forEach((row) => {
      const nameCell = row
        .querySelector("td:first-child")
        .textContent.trim()
        .toLowerCase();
      if (nameCell === query) {
        found = true;
        row.scrollIntoView({ behavior: "smooth", block: "center" });
        row.style.backgroundColor = "#fffbcc"; // Temporarily highlight
        setTimeout(() => (row.style.backgroundColor = ""), 2000); // Remove highlight
      }
    });

    if (!found) {
      alert("No matching blood bank found.");
    }
  });

  // Scroll and highlight rows for Donors
  document.getElementById("donorSearchButton").addEventListener("click", () => {
    const query = document
      .getElementById("searchDonor")
      .value.trim()
      .toLowerCase();
    const rows = document.querySelectorAll("#donorTableBody tr");

    let found = false;
    rows.forEach((row) => {
      const nameCell = row
        .querySelector("td:first-child")
        .textContent.trim()
        .toLowerCase();
      if (nameCell === query) {
        found = true;
        row.scrollIntoView({ behavior: "smooth", block: "center" });
        row.style.backgroundColor = "#fffbcc"; // Temporarily highlight
        setTimeout(() => (row.style.backgroundColor = ""), 2000); // Remove highlight
      }
    });

    if (!found) {
      alert("No matching donor found.");
    }
  });

  // Scroll and highlight rows for Donation Records
  document
    .getElementById("donationSearchButton")
    .addEventListener("click", () => {
      const query = document
        .getElementById("searchDonationRecord")
        .value.trim()
        .toLowerCase();
      const rows = document.querySelectorAll("#donationRecordTableBody tr");

      let found = false;
      rows.forEach((row) => {
        const idCell = row
          .querySelector("td:first-child")
          .textContent.trim()
          .toLowerCase();
        if (idCell === query) {
          found = true;
          row.scrollIntoView({ behavior: "smooth", block: "center" });
          row.style.backgroundColor = "#fffbcc"; // Temporarily highlight
          setTimeout(() => (row.style.backgroundColor = ""), 2000); // Remove highlight
        }
      });

      if (!found) {
        alert("No matching donation record found.");
      }
    });

  // Handle Blood Bank Actions (Edit and Delete)
  bloodBankTableBody.addEventListener("click", async (event) => {
    const target = event.target;

    if (target.classList.contains("edit-btn")) {
      const name = target.dataset.id;
      const newName = prompt("Enter new Blood Bank Name:", name);
      const newLocation = prompt("Enter new Blood Bank Location:");
      const newContact = prompt("Enter new Blood Bank Contact:");

      if (newName && newLocation && newContact) {
        try {
          await fetch(`${BASE_URL}/api/blood-banks/update`, {
            method: "POST",
            headers: {
              ...headers,
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              name: newName,
              location: newLocation,
              contact: newContact,
            }),
          });
          alert("Blood Bank updated successfully.");
          fetchBloodBanks(); // Refresh table
        } catch (error) {
          console.error("Error updating Blood Bank:", error);
          alert("Failed to update Blood Bank.");
        }
      }
    }
  });

  // Handle Donor Actions (Edit and Delete)
  donorTableBody.addEventListener("click", async (event) => {
    const target = event.target;

    if (target.classList.contains("edit-btn")) {
      const id = target.dataset.id;
      const newBloodGroup = prompt("Enter new Blood Group:");
      const newAge = prompt("Enter new Age:");
      const newGender = prompt("Enter new Gender:");
      const newPhone = prompt("Enter new Phone Number:");

      if (newGender && newAge && newBloodGroup && newPhone) {
        try {
          await fetch(`${BASE_URL}/api/donors/update`, {
            method: "POST",
            headers: {
              ...headers,
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              politicalId: id,
              age: newAge,
              gender: newGender,
              bloodGroup: newBloodGroup,
              phoneNumber: newPhone,
            }),
          });
          alert("Donor updated successfully.");
          fetchDonors(); // Refresh table
        } catch (error) {
          console.error("Error updating Donor:", error);
          alert("Failed to update Donor.");
        }
      }
    }
  });

  // Handle Donation Record Actions (Edit and Delete)
  donationRecordTableBody.addEventListener("click", async (event) => {
    const target = event.target;

    if (target.classList.contains("edit-btn")) {
      const id = target.dataset.id;
      const newBloodBankName = prompt("Enter new Blood Bank Name:");
      const newDonationDate = prompt("Enter new Donation Date (YYYY-MM-DD):");

      if (newBloodBankName && newDonationDate) {
        try {
          await fetch(`${BASE_URL}/api/donation-records/update`, {
            method: "POST",
            headers: {
              ...headers,
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              politicalId: id,
              bloodBank: {
                name: newBloodBankName,
              },
              donationDate: newDonationDate,
            }),
          });
          alert("Donation Record updated successfully.");
          fetchDonationRecords(); // Refresh table
        } catch (error) {
          console.error("Error updating Donation Record:", error);
          alert("Failed to update Donation Record.");
        }
      }
    }
  });

  const adminTableBody = document.getElementById("adminTableBody");

// Fetch and Display Admins
async function fetchAdmins(query = "") {
  try {
    const url = query
      ? `${BASE_URL}/api/admins/get/${query}`
      : `${BASE_URL}/api/admins/getAll`;
    const admins = await fetch(url, { headers }).then(handleErrors);

    adminTableBody.innerHTML = admins
      .map(
        (admin) => `
        <tr>
          <td>${admin.politicalId}</td>
          <td>${admin.firstName}</td>
          <td>${admin.lastName}</td>
          <td>${admin.username}</td>
          <td>
            <button class="edit-btn" data-id="${admin.politicalId}">Edit</button>
            <button class="delete-btn" data-id="${admin.politicalId}">Delete</button>
          </td>
        </tr>
      `
      )
      .join("");
  } catch (error) {
    console.error("Admin fetch error:", error);
    alert("Failed to load admins.");
  }
}

// Handle Admin Actions (Edit and Delete)
adminTableBody.addEventListener("click", async (event) => {
  const target = event.target;

  if (target.classList.contains("delete-btn")) {
    const id = target.dataset.id;
    if (confirm("Are you sure you want to delete this admin?")) {
      try {
        await fetch(`${BASE_URL}/api/admins/delete/${id}`, {
          method: "DELETE",
          headers,
        });
        alert("Admin deleted successfully.");
        fetchAdmins(); // Refresh table
      } catch (error) {
        console.error("Admin delete error:", error);
        alert("Failed to delete admin.");
      }
    }
  }

  if (target.classList.contains("edit-btn")) {
    const id = target.dataset.id;
    const newFirstName = prompt("Enter new First Name:");
    const newLastName = prompt("Enter new Last Name:");
    const newUsername = prompt("Enter new Username:");

    if (newFirstName && newLastName) {
      try {
        await fetch(`${BASE_URL}/api/admins/update/${id}`, {
          method: "PUT",
          headers: {
            ...headers,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            firstName: newFirstName,
            lastName: newLastName,
            username: newUsername,
          }),
        });
        alert("Admin updated successfully.");
        fetchAdmins(); // Refresh table
      } catch (error) {
        console.error("Admin update error:", error);
        alert("Failed to update admin.");
      }
    }
  }
});

// Add New Admin
document.getElementById("newAdminButton").addEventListener("click", () => {
  const firstName = prompt("Enter First Name:");
  const lastName = prompt("Enter Last Name:");
  const politicalId = prompt("Enter Political ID:");
  const username = prompt("Enter Username:");
  const password = prompt("Enter Password:");

  if (firstName && lastName && username && password) {
    fetch(`${BASE_URL}/api/admins/create`, {
      method: "POST",
      headers: {
        ...headers,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ politicalId, firstName, lastName, username, password }),
    })
      .then(handleErrors)
      .then(() => {
        alert("Admin created successfully!");
        fetchAdmins(); // Refresh table
      })
      .catch((error) => {
        console.error("Error creating admin:", error);
        alert("Failed to create admin.");
      });
  }
});

  // Initial Load
  fetchOverview();
  fetchDonors();
  fetchDonationRecords();
  fetchBloodBanks();
  fetchAdmins(); // Fetch Admins
});
