document.addEventListener("DOMContentLoaded", () => {
  const BASE_URL = "http://localhost:8080/api"; // Update to match your backend
  const DEBUG = true;
  
  // Add debug utility
  function debugLog(message, data) {
    if (DEBUG) {
      console.log(`[DEBUG] ${message}:`, data);
    }
  }
  
  const token = localStorage.getItem("jwt");
  const politicalId = localStorage.getItem("politicalId");
  debugLog("Political ID", politicalId);
  
  if (!token) {
    alert("Unauthorized access. Please log in.");
    window.location.href = "../html/login.html";
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
    window.location.href = "../html/login.html";
  }

  const headers = {
    Authorization: `Bearer ${token}`,
  };

  debugLog("Request headers", headers);

  function handleErrors(response) {
    if (!response.ok) {
      if (response.status === 401) {
        alert("Unauthorized. Please log in again.");
        localStorage.removeItem("jwt");
        window.location.href = "../html/login.html";
      }
      throw new Error(`HTTP Error ${response.status}`);
    }
    return response.json();
  }
  

  // Fetch and populate user details
  async function loadUserDetails() {
    try {
      const response = await fetch(`${BASE_URL}/users/get/${politicalId}`, {
        headers,
        method: "GET",
      });
      const user = await response.json();
      document.getElementById(
        "userName"
      ).textContent = `${user.firstName} ${user.lastName}`;
    } catch (error) {
      console.error(error);
      alert("Session expired. Please log in again.");
      window.location.href = "../html/login.html"; // Redirect to login page
    }
  }

  // Fetch overview stats
  // Fetch overview stats
async function loadOverview() {
  try {
    // Retrieve the political ID from local storage
    const politicalId = localStorage.getItem("politicalId");
    if (!politicalId) throw new Error("Political ID not found");

    // Fetch donations
    const donationsResponse = await fetch(`${BASE_URL}/donation-records/donor/${politicalId}`, { headers });
    if (!donationsResponse.ok) throw new Error("Failed to fetch donations");

    const totalDonations = await donationsResponse.json();

    // Fetch blood banks
    const bloodBanksResponse = await fetch(`${BASE_URL}/blood-banks/getAll`, { headers });
    if (!bloodBanksResponse.ok) throw new Error("Failed to fetch blood banks");

    const bloodBanks = await bloodBanksResponse.json();

    // Update the UI with the fetched data
    document.getElementById("totalDonations").textContent = totalDonations.length;
    document.getElementById("totalBloodBanks").textContent = bloodBanks.length;
  } catch (error) {
    console.error(error);
    alert("Failed to load overview stats.");
  }
}

// Example usage: Load overview stats when the page loads
document.addEventListener("DOMContentLoaded", () => {
  loadOverview();
});

  // Fetch blood banks
  async function loadBloodBanks() {
    try {
      console.log("Fetching blood banks...");
      const response = await fetch(`${BASE_URL}/blood-banks/getAll`, {
        headers,
      });
  
      console.log("Response status:", response.status);
      if (!response.ok) {
        throw new Error(`Failed to fetch: ${response.status}`);
      }
  
      const bloodBanks = await response.json();
      console.log("Blood banks:", bloodBanks);
  
      const table = document.getElementById("bloodBanksTable");
      if (!table) throw new Error("Table element not found");
  
      table.innerHTML = bloodBanks
        .map(
          (bank) =>
            `<tr>
              <td>${bank.name}</td>
              <td>${bank.location}</td>
              <td>${bank.contact}</td>
            </tr>`
        )
        .join("");
    } catch (error) {
      console.error("Error loading blood banks:", error);
      alert("Failed to load blood banks. Check the console for details.");
    }
  }
  

  // Fetch donation history
  // filepath: /c:/Users/spyro/OneDrive/Desktop/Blood/Blood-Management/frontend/js/user-dashboard.js

  // Fetch donation history for the logged-in user
  async function loadDonationHistory() {
    try {
      // Retrieve the political ID from local storage
      if (!politicalId) throw new Error("Political ID not found");

      const response = await fetch(
        `${BASE_URL}/donation-records/donor/${politicalId}`,
        { headers}
      );
      if (!response.ok) throw new Error("Failed to fetch donation history");

      const history = await response.json();
      const table = document.getElementById("donationHistoryTable");

      table.innerHTML = history
        .map(
          (record) =>
            `<tr>
            <td>${record.bloodBankName}</td>
            <td>${record.donationDate}</td>
          </tr>`
        )
        .join("");
    } catch (error) {
      console.error(error);
      alert("Failed to load donation history.");
    }
  }

  // Logout function
  document.getElementById("logoutButton").addEventListener("click", () => {
    localStorage.removeItem("jwt");
    window.location.href = "../html/login.html";
  });

  // Load data when the page loads
  loadOverview();
  loadUserDetails();
  loadBloodBanks();
  loadDonationHistory();
});
