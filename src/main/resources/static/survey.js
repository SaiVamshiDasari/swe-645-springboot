document.addEventListener("DOMContentLoaded", () => {
    // Handle form submission
    const surveyForm = document.querySelector("form");
    surveyForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        // Collect form data
        const formData = {
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            email: document.getElementById("email").value,
            telephoneNumber: document.getElementById("phone").value,
            streetAddress: document.getElementById("street").value,
            city: document.getElementById("city").value,
            state: document.getElementById("state").value,
            zip: document.getElementById("zip").value,
            dateOfSurvey: document.getElementById("date").value,
            likedMost: Array.from(document.querySelectorAll('input[name="likedMost"]:checked'))
                .map(input => input.value)
                .join(","),
            howInterested: document.querySelector('input[name="interest"]:checked').value,
            recommendationLikelihood: document.getElementById("recommend").value,
        };

        // POST data to backend
        try {
            const response = await fetch("http://localhost:8081/api/surveys", {
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
                alert("Failed to submit survey. Please try again.");
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
            const response = await fetch("http://localhost:8081/api/surveys/all");
            if (response.ok) {
                const surveys = await response.json();
                localStorage.setItem("surveys", JSON.stringify(surveys));
                window.location.href = "survey.html"; // Redirect to survey.html
            } else {
                alert("Failed to fetch survey data. Please try again.");
            }
        } catch (error) {
            console.error("Error fetching surveys:", error);
            alert("An error occurred while fetching surveys.");
        }
    });
});
