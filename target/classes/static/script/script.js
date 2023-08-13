

// METHOD TO TOGGLE SLIDEBAR IN USER DASHBOARD
const toogleSideBar = () => {

	if ($(".sidebar").is(":visible")) {
		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");
	}
	else {
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "17%");
	}
};

// SCRIPT FOR CHECKING THE LENGTH OF PHONE NUMBER WHILE ADDING A CONTACT
function checkPhoneNumberLength(inputElement) {
	const phoneNumber = inputElement.value;
	const phoneNumberLength = phoneNumber.length;
	const errorDiv = document.getElementById("phone-error");

	if (phoneNumberLength < 9 || phoneNumberLength > 11) {
		errorDiv.style.display = "block";
	} else {
		errorDiv.style.display = "none";
	}
}


// FUNCTION FOR DELETING A SPECIFIC CONTACT
function deleteContact(cId) {
	swal({
		title: "Are you sure? ",
		text: "Once deleted you will not be able to recover this contact",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	}).then((willDelete) => {
		if (willDelete) {
			swal("Your Contact Has Been Deleted", {
				icon: "success"
			});

			// Delay the redirect for 2 seconds (2000 milliseconds)
			setTimeout(() => {
				window.location = "/user/delete/" + cId;
			}, 1700);
		} else {

		}
	});
}

// SEARCHING CONTACTS 
const search = () => {

	let query = document.getElementById("search-input").value

	if (query == "") {
		$(".search-result").hide()
	}
	else {
		// sending request to server

		let url = `http://localhost:8080/search/${query}`;

		fetch(url).then((response) => {
			return response.json();
		}).then((data) => {
			let text = `<div class='list-group'>`

			if (data.length == 0) {
				text += `<p>No result found.</p>`
			}
			else {
				data.forEach((contact) => {
					text += `<a href='/user/contact/${contact.cId}' class='list-group-item list-group-item-action search-font'>`;
					text += `<i class="fa-solid fa-user fa-sm"></i> ${contact.name} <small>(SCM2023-${contact.cId})</small>`;
					text += `</a>`;
				});
			}



			text += `</div>`

			$(".search-result").html(text)
			$(".search-result").show()
		});


	}

};

const activeNav = () => {

	//$("#home-option").removeClass("active")

	let elementsWithActiveClass = Array.from(document.querySelectorAll(".active-nav"))
	let spansWithActiveClass = Array.from(document.querySelectorAll(".white"))
	const currentURL = window.location.href;

	if (currentURL.includes("dashboard")) {

		elementsWithActiveClass.forEach((element) => {
			element.classList.remove("active-nav");
		});
		
		spansWithActiveClass.forEach((element) => {
			element.classList.remove("white");
			elements.classList.add("black")
		});

	}

	console.log(currentURL);
}


