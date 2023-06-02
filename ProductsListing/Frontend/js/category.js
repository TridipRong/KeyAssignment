function fetchCategoryData() {
  var url = "http://localhost:8081/categories/categoryList";

  fetch(url)
    .then((response) => response.json())
    .then((data) => {
      populateCategoryList(data);
      console.log(data);
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}

function populateCategoryList(categories) {
  var categoryList = document.querySelector("#categories ul");

  categories.forEach(function (category) {
    var li = document.createElement("li");
    li.textContent = category.categoryName;

    var deleteButton = document.createElement("button");
    deleteButton.innerText = "Delete";
    deleteButton.addEventListener("click", function () {
      deleteCategory(category.categoryId);
    });
    li.appendChild(deleteButton);

    categoryList.appendChild(li);
  });
}

// Call the function to fetch and populate the category list
document.addEventListener("DOMContentLoaded", function () {
  fetchCategoryData();
  document.getElementById("create").addEventListener("click", function (event) {
    event.preventDefault();

    var categoryNameInput = document.getElementById("category-name");
    var categoryName = categoryNameInput.value;

    if (!categoryName) {
      return;
    }

    var url = "http://localhost:8081/categories/createCategory";

    var requestData = {
      categoryName: categoryName,
    };

    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestData),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        alert("Category Created successfully!");
      })
      .catch((error) => {
        console.error("Error:", error);
      });

    categoryNameInput.value = "";
  });
});

//Delete the product

function deleteCategory(id) {
  var confirmation = confirm("Are you sure you want to delete?");

  if (confirmation) {
    const categoryID = id;

    // Delete request code
    var url = `http://localhost:8081/categories/deleteCategory/${categoryID}`;

    fetch(url, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        alert("Category deleted successfully!");
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error:", error);
      });
    window.location.reload();
  }
}
