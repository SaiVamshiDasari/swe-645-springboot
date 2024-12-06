/*<!-- /*Assignment-3
 *   <---Team Members--->
* Sai Vamshi Dasari-G01464718
* Aryan Sudhagoni-G01454180
* Lahari ummadisetty-G01454186
*/

document.addEventListener("DOMContentLoaded", () => {
    // Define your backend base URL
    const BASE_URL = "http://acd202bc46cc9407cb4f97a7021422c3-71503224.us-east-1.elb.amazonaws.com/"; // Change this to your deployed backend URL if needed

    // Handle form submission
    const surveyForm = document.querySelector("form");
    surveyForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        // Collect form data
        const formData = {
            firstName: document.getElementById("firstName").value.trim(),
            lastName: document.getElementById("lastName").value.trim(),
            email: document.getElementById("email").value.trim(),
            telephoneNumber: document.getElementById("phone").value.trim(),
            streetAddress: document.getElementById("street").value.trim(),
            city: document.getElementById("city").value.trim(),
            state: document.getElementById("state").value.trim(),
            zip: document.getElementById("zip").value.trim(),
            dateOfSurvey: document.getElementById("date").value,
            likedMost: Array.from(document.querySelectorAll('input[name="likedMost"]:checked'))
                .map(input => input.value)
                .join(","),
            howInterested: document.querySelector('input[name="interest"]:checked')?.value || "",
            recommendationLikelihood: document.getElementById("recommend").value.trim(),
        };

        console.log("Submitting survey data:", formData); // Log the data being submitted

        // POST data to backend
        try {
            const response = await fetch(`${BASE_URL}/api/survey`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                alert("Survey submitted successfully!");
                surveyForm.reset();
            } else {
                const errorData = await response.json();
                console.error("Error response from backend:", errorData);
                alert(`Failed to submit survey: ${errorData.message || "Unknown error"}`);
            }
        } catch (error) {
            console.error("Error submitting survey:", error);
            alert("An error occurred while submitting the survey.");
        }
    });

    // Handle "View Surveys" button click
    const viewSurveysButton = document.getElementById("view-surveys-btn");
    viewSurveysButton.addEventListener("click", async (event) => {
        event.preventDefault();

        // Fetch all surveys
        try {
            const response = await fetch(`${BASE_URL}/api/survey/all`);
            if (response.ok) {
                const surveys = await response.json();
                console.log("Fetched surveys:", surveys); // Log fetched surveys for debugging
                localStorage.setItem("surveys", JSON.stringify(surveys));
                window.location.href = "survey.html"; // Redirect to survey.html
            } else {
                const errorData = await response.json();
                console.error("Error fetching surveys:", errorData);
                alert(`Failed to fetch survey data: ${errorData.message || "Unknown error"}`);
            }
        } catch (error) {
            console.error("Error fetching surveys:", error);
            alert("An error occurred while fetching surveys.");
        }
    });
});
