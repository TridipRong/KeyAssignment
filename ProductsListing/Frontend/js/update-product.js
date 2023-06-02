document.addEventListener("DOMContentLoaded", function () {
  var urlParams = new URLSearchParams(window.location.search);
  var productID = urlParams.get("id");

  // Fetch the product data from the API using the product ID
  var url = `http://localhost:8081/products/${productID}`;

  fetch(url)
    .then((response) => response.json())
    .then((data) => {
      // Populate the form fields with the retrieved product data
      document.getElementById("productName").value = data.productName;
      document.getElementById("productDescription").value =
        data.productDescription;
      document.getElementById("price").value = data.price;
      document.getElementById("quantity").value = data.quantity;
      document.getElementById("imageLink").value = data.imageLink;
      document.getElementById("category").value = data.category.categoryName;
      console.log(data.productDescription);
      console.log(data.category);
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("An error occurred. Please try again.");
    });

  // Add event listener to the form submit button
  document.getElementById("submit").addEventListener("click", function (event) {
    event.preventDefault();

    // Get the updated form input values
    var updatedProductName = document.getElementById("productName").value;
    var updatedProductDescription =
      document.getElementById("productDescription").value;
    var updatedPrice = parseFloat(document.getElementById("price").value);
    var updatedQuantity = parseInt(document.getElementById("quantity").value);
    var updatedImageLink = document.getElementById("imageLink").value;
    var updatedCategoryID = document.getElementById("category").value;
    console.log(updatedImageLink);
    // Create the updated product object
    var updatedProduct = {
      productName: updatedProductName,
      productDescription: updatedProductDescription,
      price: updatedPrice,
      quantity: updatedQuantity,
      imageLink: updatedImageLink,
      category: {
        categoryId: updatedCategoryID,
      },
    };

    // Send the updated product data to the API for updating
    var updateURL = `http://localhost:8081/products/updateProduct/${productID}`;
    fetch(updateURL, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updatedProduct),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        alert("Product updated successfully!");
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("An error occurred. Please try again.");
      });
  });
});
function populateCategoryList(categories) {
  var categorySelect = document.getElementById("category");

  // Create "Select a category" option
  var selectOption = document.createElement("option");
  selectOption.value = "";
  selectOption.textContent = "Select a category";

  // Insert "Select a category" option at the beginning
  categorySelect.insertBefore(selectOption, categorySelect.firstChild);

  categories.forEach(function (category) {
    var option = document.createElement("option");
    option.value = category.categoryId;
    option.textContent = category.categoryName;
    categorySelect.appendChild(option);
  });
}
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
document.addEventListener("DOMContentLoaded", function () {
  fetchCategoryData();
});
