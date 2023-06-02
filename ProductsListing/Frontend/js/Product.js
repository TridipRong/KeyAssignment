// Function to populate the product list
function populateProductList(products) {
  var productList = document.getElementById("product-list");
  products.forEach(function (product) {
    var div = document.createElement("div");
    div.classList.add("product-item");

    var productImage = document.createElement("img");
    productImage.src = product.imageLink;
    productImage.alt = product.productName;
    div.appendChild(productImage);

    var productName = document.createElement("h3");
    productName.textContent = product.productName;
    div.appendChild(productName);

    var productPrice = document.createElement("p");
    productPrice.textContent = "Price: $" + product.price;
    div.appendChild(productPrice);

    var buttonsContainer = document.createElement("div");
    buttonsContainer.classList.add("buttons-container");

    var addToCartButton = document.createElement("button");
    addToCartButton.textContent = "Add to Cart";
    addToCartButton.classList.add("add-to-cart-button");
    buttonsContainer.appendChild(addToCartButton);

    var buyNowButton = document.createElement("button");
    buyNowButton.textContent = "Buy Now";
    buyNowButton.classList.add("buy-now-button");
    buttonsContainer.appendChild(buyNowButton);

    div.appendChild(buttonsContainer);

    productList.appendChild(div);

    div.addEventListener("click", function () {
      var productID = product.productId;
      window.location.href = "product.html?id=" + productID;
    });
  });
}

function fetchProductData() {
  var url = "http://localhost:8081/products/productsList";

  fetch(url)
    .then((response) => response.json())
    .then((data) => {
      populateProductList(data);
      console.log(data);
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}

fetchProductData();

function getProductIDFromURL() {
  const urlParams = new URLSearchParams(window.location.search);
  const productID = urlParams.get("id");
  return productID;
}

// Function to fetch the product details from the backend API
function fetchProductDetails() {
  const productID = getProductIDFromURL();
  var url = `http://localhost:8081/products/${productID}`;

  fetch(url)
    .then((response) => response.json())
    .then((data) => {
      populateProductDetails(data);

      console.log(data);
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}

// Function to populate the product details on the page
function populateProductDetails(product) {
  const productDetailsContainer = document.getElementById("product-details");
  var productImage = document.createElement("img");
  productImage.src = product.imageLink;
  productImage.alt = product.productName;
  productDetailsContainer.appendChild(productImage);

  const productName = document.createElement("h2");
  productName.textContent = product.productName;
  productDetailsContainer.appendChild(productName);

  const productDescription = document.createElement("p");
  productDescription.textContent = product.productDescription;
  productDetailsContainer.appendChild(productDescription);

  const productPrice = document.createElement("p");
  productPrice.textContent = `Price: $${product.price}`;
  productDetailsContainer.appendChild(productPrice);

  const productCategory = document.createElement("p");
  productCategory.textContent = product.category.categoryName;
  productDetailsContainer.appendChild(productCategory);
  var buttonsContainer = document.createElement("div");
  buttonsContainer.classList.add("buttons-container");

  var addToCartButton = document.createElement("button");
  addToCartButton.textContent = "Add to Cart";
  addToCartButton.classList.add("add-to-cart-button");
  buttonsContainer.appendChild(addToCartButton);

  var buyNowButton = document.createElement("button");
  buyNowButton.textContent = "Buy Now";
  buyNowButton.classList.add("buy-now-button");
  buttonsContainer.appendChild(buyNowButton);

  productDetailsContainer.appendChild(buttonsContainer);
}

// Call the function to fetch and populate the product details
fetchProductDetails();

//Delete the product

document.addEventListener("DOMContentLoaded", function () {
  document
    .getElementById("delete-button")
    .addEventListener("click", function (event) {
      event.preventDefault();

      var confirmation = confirm("Are you sure you want to delete?");

      if (confirmation) {
        const productID = getProductIDFromURL();

        // Delete request code
        var url = `http://localhost:8081/products/deleteProduct/${productID}`;

        fetch(url, {
          method: "DELETE",
        })
          .then((response) => response.json())
          .then((data) => {
            console.log(data);
            alert("Product deleted successfully!");
          })
          .catch((error) => {
            console.error("Error:", error);
            alert("An error occurred. Please try again.");
          });
      }
      window.location.replace("index.html");
    });
});

document.addEventListener("DOMContentLoaded", function () {
  document
    .getElementById("update-button")
    .addEventListener("click", function () {
      var productID = getProductIDFromURL();
      window.location.href = "updateproduct.html?id=" + productID;
    });
});
