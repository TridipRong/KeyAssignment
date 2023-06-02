document.addEventListener("DOMContentLoaded", function () {
  document.getElementById("submit").addEventListener("click", function (event) {
    event.preventDefault(); // Prevent form submission
    var url = "http://localhost:8081/products/createProducts";
    var name = document.getElementById("productName").value;
    var description = document.getElementById("productDescription").value;
    var pPrice = parseFloat(document.getElementById("price").value);
    var pQuantity = parseInt(document.getElementById("quantity").value);
    var imglink = document.getElementById("imageLink").value;

    var categorySelect = document.getElementById("category");
    var selectedCategoryId = categorySelect.value;
    var selectedCategoryName =
      categorySelect.options[categorySelect.selectedIndex].text;

    var product = {
      productName: name,
      productDescription: description,
      price: pPrice,
      quantity: pQuantity,
      imageLink: imglink,
      category: {
        categoryId: selectedCategoryId,
        categoryName: selectedCategoryName,
      },
    };

    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(product),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        document.getElementById("productName").value = "";
        document.getElementById("productDescription").value = "";
        document.getElementById("price").value = "";
        document.getElementById("quantity").value = "";
        document.getElementById("imageLink").value = "";
        document.getElementById("category").selectedIndex = 0;
        alert("Product created successfully!");
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("An error occurred. Please try again.");
      });

    product.value = "";
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
