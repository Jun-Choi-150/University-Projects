import React from "react";
import { useState, useEffect } from "react";

export default function Edit() {
  const [product, setProduct] = useState([]);
  const [viewer1, setViewer1] = useState(false);
  const [viewer4, setViewer4] = useState(false);
  const [oneProduct, setOneProduct] = useState([]);

  const [checked1, setChecked1] = useState(false);
  const [checked2, setChecked2] = useState(false);
  const [checked3, setChecked3] = useState(false);
  const [checked4, setChecked4] = useState(false);

  const [productId, setProductId] = useState("");
  const [productToUpdate, setProductToUpdate] = useState(null);
  const [newPrice, setNewPrice] = useState("");

  const [index, setIndex] = useState(0);

  useEffect(() => {
    getAllProducts(); //any happen once when I load webpage
  }, []);

  // new Product
  const [addNewProduct, setAddNewProduct] = useState({
    _id: 0,
    title: "",
    price: 0.0,
    description: "",
    category: "",
    image: "http://127.0.0.1:4000/images/",
    rating: { rate: 0.0, count: 0 },
  });

  function getAllProducts() {
    fetch("http://localhost:4000/")
      .then((response) => response.json())
      .then((data) => {
        console.log("Show Catalog of Products :");
        console.log(data);
        setProduct(data);
      });
    setViewer1(!viewer1);
  }

  function handleChange(evt) {
    const value = evt.target.value;
    if (evt.target.name === "_id") {
      setAddNewProduct({ ...addNewProduct, _id: value });
    } else if (evt.target.name === "title") {
      setAddNewProduct({ ...addNewProduct, title: value });
    } else if (evt.target.name === "price") {
      setAddNewProduct({ ...addNewProduct, price: value });
    } else if (evt.target.name === "description") {
      setAddNewProduct({ ...addNewProduct, description: value });
    } else if (evt.target.name === "category") {
      setAddNewProduct({ ...addNewProduct, category: value });
    } else if (evt.target.name === "image") {
      const temp = value;
      setAddNewProduct({ ...addNewProduct, image: temp });
    } else if (evt.target.name === "rate") {
      setAddNewProduct({ ...addNewProduct, rating: { rate: value } });
    } else if (evt.target.name === "count") {
      const temp = addNewProduct.rating.rate;
      setAddNewProduct({
        ...addNewProduct,
        rating: { rate: temp, count: value },
      });
    }
  }

  function handleOnSubmit(e) {
    e.preventDefault();
    console.log(e.target.value);
    fetch("http://localhost:4000/insert", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(addNewProduct),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Post a new product completed");
        console.log(data);
        if (data) {
          //const keys = Object.keys(data);
          const value = Object.values(data);
          alert(value);
        }
      });
  }

  useEffect(() => {
    getAllProducts();
  }, [checked4]);

  function getOneByOneProductNext() {
    if (product.length > 0) {
      if (index === product.length - 1) setIndex(0);
      else setIndex(index + 1);
      if (product.length > 0) setViewer4(true);
      else setViewer4(false);
    }
  }

  function getOneByOneProductPrev() {
    if (product.length > 0) {
      if (index === 0) setIndex(product.length - 1);
      else setIndex(index - 1);
      if (product.length > 0) setViewer4(true);
      else setViewer4(false);
    }
  }

  // function deleteOneProduct(deleteid) {
  //   console.log("Product to delete :", deleteid);
  //   fetch("http://localhost:4000/delete/", {
  //     method: "DELETE",
  //     headers: { "Content-Type": "application/json" },
  //     body: JSON.stringify({ _id: deleteid }),
  //   })
  //     .then((response) => response.json())
  //     .then((data) => {
  //       console.log("Delete a product completed : ", deleteid);
  //       console.log(data);
  //       if (data) {
  //         //const keys = Object.keys(data);
  //         const value = Object.values(data);
  //         alert(value);
  //       }
  //     });
  //   setChecked4(!checked4);
  // }

  const deleteOneProduct = async (deleteid) => {
    console.log("Product to delete :", deleteid);
    try {
      const response = await fetch("http://localhost:4000/delete/", {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ _id: deleteid }),
      });
      const data = await response.json();
      console.log("Delete a product completed : ", deleteid);
      console.log(data);
      if (data) {
        //const keys = Object.keys(data);
        const value = Object.values(data);
        alert(value);
      }
  
      // Update the product state after deletion
      setProduct((prevProducts) => prevProducts.filter((item) => item._id !== deleteid));
  
      // Update the index state after deletion
      if (index >= product.length - 1) {
        setIndex(0);
      }
    } catch (error) {
      console.log("Error while deleting the product: " + error);
    }
    setChecked4(!checked4);
  };
  

  const fetchProductInfo = async () => {
    const response = await fetch(`http://localhost:4000/product/${productId}`);
    const productData = await response.json();
    setProductToUpdate(productData);
  };

  const updateProductPrice = async () => {
    const response = await fetch("http://localhost:4000/update", {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ _id: productToUpdate._id, newPrice }),
    });
    const responseData = await response.json();
    console.log(responseData);
    fetchProductInfo();
  };

  return (
    <div className="bg-white max-w-2xl mx-auto px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">
      <h2 className="text-2xl font-bold tracking-tight text-gray-900">
        Catalog of Products
      </h2>

      {/* Read */}
      <div className="container mx-auto">
        <div className="container max-w-3xl px-4 mx-auto sm:px-8">
          <div className="py-8">
            <div className="px-4 py-4 -mx-4 overflow-x-auto sm:-mx-8 sm:px-8">
              <div className="inline-block min-w-full overflow-hidden rounded-lg shadow">
                <table className="min-w-full leading-normal">
                  <thead className="sticky top-0 z-10 bg-white">
                    <tr>
                      <th
                        scope="col"
                        className="px-5 py-3 text-sm font-normal text-left text-gray-800 uppercase bg-white border-b border-gray-200"
                      >
                        <label className="flex items-center mb-3 space-x-3">
                          <input
                            type="checkbox"
                            checked={checked1}
                            onChange={(e) => setChecked1(!checked1)}
                            id="acceptdelete"
                            name="checked-demo"
                            className="form-tick appearance-none bg-white bg-check h-6 w-6 border border-gray-300 rounded-md checked:bg-blue-500 checked:border-transparent focus:outline-none"
                          />
                          <span className="font-normal text-gray-700 dark:text-white">
                            <strong>Read Products</strong>
                          </span>
                        </label>
                      </th>
                    </tr>
                  </thead>
                </table>
                {checked1 && (
                  <div className="w-full flex">
                    <div
                      key={product[index]._id}
                      className="w-1/3 bg-cover bg-landscape"
                    >
                      <img
                        src={product[index].image}
                        style={{
                          objectFit: "contain",
                          maxHeight: "200px",
                          width: "100%",
                        }}
                      />
                    </div>
                    <div className="w-2/3 p-4">
                      <div className="ml-4">
                        Id: {product[index]._id} <br />
                        Title: {product[index].title} <br />
                        Category: {product[index].category} <br />
                        Price: {product[index].price} <br />
                        Rate: {product[index].rating.rate} <br />
                        Count:{product[index].rating.count} <br />
                      </div>
                    </div>
                  </div>
                )}
                {checked1 && (
                  <div className="flex justify-center space-x-2 mt-4">
                    <button
                      className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-3 py-1 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
                      onClick={() => getOneByOneProductPrev()}
                    >
                      Prev
                    </button>
                    <button
                      className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-3 py-1 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
                      onClick={() => getOneByOneProductNext()}
                    >
                      Next
                    </button>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Create */}
      <div className="container mx-auto">
        <div className="container max-w-3xl px-4 mx-auto sm:px-8">
          <div className="py-8">
            <div className="px-4 py-4 -mx-4 overflow-x-auto sm:-mx-8 sm:px-8">
              <div className="inline-block min-w-full overflow-hidden rounded-lg shadow">
                <table className="min-w-full leading-normal">
                  <thead className="sticky top-0 z-10 bg-white">
                    <tr>
                      <th
                        scope="col"
                        className="px-5 py-3 text-sm font-normal text-left text-gray-800 uppercase bg-white border-b border-gray-200"
                      >
                        <label className="flex items-center mb-3 space-x-3">
                          <input
                            type="checkbox"
                            checked={checked2}
                            onChange={(e) => setChecked2(!checked2)}
                            id="acceptdelete"
                            name="checked-demo"
                            className="form-tick appearance-none bg-white bg-check h-6 w-6 border border-gray-300 rounded-md checked:bg-blue-500 checked:border-transparent focus:outline-none"
                          />
                          <span className="font-normal text-gray-700 dark:text-white">
                            <strong>Create a product</strong>
                          </span>
                        </label>
                      </th>
                    </tr>
                  </thead>
                </table>
                {checked2 && (
                  <div className="mt-4">
                    <form action="">
                      <div class="md:flex md:items-center mb-6">
                        <div class="md:w-1/3">
                          <label
                            class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
                            for="inline-full-name"
                          >
                            Product ID
                          </label>
                        </div>
                        <div class="md:w-1/3">
                          <input
                            type="number"
                            placeholder="id?"
                            name="_id"
                            value={addNewProduct._id}
                            onChange={handleChange}
                            className="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                          />
                        </div>
                      </div>

                      <div class="md:flex md:items-center mb-6">
                        <div class="md:w-1/3">
                          <label
                            class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
                            for="inline-full-name"
                          >
                            Product Title
                          </label>
                        </div>
                        <div class="md:w-1/3">
                          <input
                            type="text"
                            placeholder="title?"
                            name="title"
                            value={addNewProduct.title}
                            onChange={handleChange}
                            className="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                          />
                        </div>
                      </div>
                      <div class="md:flex md:items-center mb-6">
                        <div class="md:w-1/3">
                          <label
                            class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
                            for="inline-full-name"
                          >
                            Product Price
                          </label>
                        </div>
                        <div class="md:w-1/3">
                          <input
                            type="number"
                            placeholder="price?"
                            name="price"
                            value={addNewProduct.price}
                            onChange={handleChange}
                            className="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                          />
                        </div>
                      </div>
                      <div class="md:flex md:items-center mb-6">
                        <div class="md:w-1/3">
                          <label
                            class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
                            for="inline-full-name"
                          >
                            Description
                          </label>
                        </div>
                        <div class="md:w-1/3">
                          <input
                            type="text"
                            placeholder="description?"
                            name="description"
                            value={addNewProduct.description}
                            onChange={handleChange}
                            className="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                          />
                        </div>
                      </div>
                      <div class="md:flex md:items-center mb-6">
                        <div class="md:w-1/3">
                          <label
                            class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
                            for="inline-full-name"
                          >
                            Category
                          </label>
                        </div>
                        <div class="md:w-1/3">
                          <input
                            type="text"
                            placeholder="category?"
                            name="category"
                            value={addNewProduct.category}
                            onChange={handleChange}
                            className="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                          />
                        </div>
                      </div>
                      <div class="md:flex md:items-center mb-6">
                        <div class="md:w-1/3">
                          <label
                            class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
                            for="inline-full-name"
                          >
                            Image Path
                          </label>
                        </div>
                        <div class="md:w-1/3">
                          <input
                            type="text"
                            placeholder="image?"
                            name="image"
                            value={addNewProduct.image}
                            onChange={handleChange}
                            className="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                          />
                        </div>
                      </div>
                      <div class="md:flex md:items-center mb-6">
                        <div class="md:w-1/3">
                          <label
                            class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
                            for="inline-full-name"
                          >
                            Rate
                          </label>
                        </div>
                        <div class="md:w-1/3">
                          <input
                            type="number"
                            placeholder="rate?"
                            name="rate"
                            value={addNewProduct.rating.rate}
                            onChange={handleChange}
                            className="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                          />
                        </div>
                      </div>
                      <div class="md:flex md:items-center mb-6">
                        <div class="md:w-1/3">
                          <label
                            class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
                            for="inline-full-name"
                          >
                            Count
                          </label>
                        </div>
                        <div class="md:w-1/3">
                          <input
                            type="number"
                            placeholder="count?"
                            name="count"
                            value={addNewProduct.rating.count}
                            onChange={handleChange}
                            className="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                          />
                        </div>
                      </div>
                      <div className="flex justify-center space-x-2 mt-4">
                        <button
                          type="submit"
                          className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-3 py-1 text-base font-medium text-white shadow-sm hover:bg-indigo-700 mt-4"
                          onClick={handleOnSubmit}
                        >
                          Submit
                        </button>
                      </div>
                    </form>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Update */}
      <div className="container mx-auto">
        <div class="container max-w-3xl px-4 mx-auto sm:px-8">
          <div class="py-8">
            <div class="px-4 py-4 -mx-4 overflow-x-auto sm:-mx-8 sm:px-8">
              <div class="inline-block min-w-full overflow-hidden rounded-lg shadow">
                <table class="min-w-full leading-normal">
                  <thead>
                    <tr>
                      <th
                        scope="col"
                        class="px-5 py-3 text-sm font-normal text-left text-gray-800 uppercase bg-white border-b border-gray-200"
                      >
                        <label class="flex items-center mb-3 space-x-3">
                          <input
                            type="checkbox"
                            checked={checked3}
                            onChange={(e) => setChecked3(!checked3)}
                            id="acceptdelete"
                            name="checked-demo"
                            class="form-tick appearance-none bg-white bg-check h-6 w-6 border border-gray-300 rounded-md checked:bg-blue-500 checked:border-transparent focus:outline-none"
                          />
                          <span class="font-normal text-gray-700 dark:text-white">
                            <strong>Update a product</strong>
                          </span>
                        </label>
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <div>
                      {checked3 && (
                        <div>
                          <div class="md:flex md:items-center mb-6">
                            <div class="md:w-1/3">
                              <label
                                class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
                                for="inline-full-name"
                              >
                                Enter Product ID:
                              </label>
                            </div>
                            <div class="md:w-1/3">
                              <input
                                type="text"
                                placeholder="Product ID"
                                id="productId"
                                value={productId}
                                onChange={(e) => setProductId(e.target.value)}
                                className="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                              />
                            </div>
                          </div>
                          <div className="flex justify-center space-x-2 mt-4">
                            <button
                              className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-2 py-1 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
                              onClick={() => fetchProductInfo()}
                            >
                              Search
                            </button>
                          </div>
                        </div>
                      )}
                      {checked3 && productToUpdate && (
                        <div>
                          <div className="w-full flex mb-4">
                            <div
                              key={productToUpdate._id}
                              className="w-1/3 bg-cover bg-landscape"
                            >
                              <img
                                src={productToUpdate.image}
                                style={{
                                  objectFit: "contain",
                                  maxHeight: "200px",
                                  width: "100%",
                                }}
                              />
                            </div>
                            <div className="w-2/3 p-4">
                              <div className="ml-4">
                                Id: {productToUpdate._id} <br />
                                Title: {productToUpdate.title} <br />
                                Category: {productToUpdate.category} <br />
                                Price: {productToUpdate.price} <br />
                                Rate: {productToUpdate.rating.rate} <br />
                                Count:{productToUpdate.rating.count} <br />
                              </div>
                            </div>
                          </div>
                          <div className="w-full flex justify-center items-center mb-4">
                            <div className="flex items-center">
                              <label
                                class="block text-gray-500 font-bold md:text-left mb-1 md:mb-0 pr-4"
                                for="inline-full-name"
                              >
                                Enter New Price:
                              </label>
                              <div class="md:w-1/3">
                                <input
                                  type="text"
                                  placeholder="Update Price"
                                  id="newPrice"
                                  value={newPrice}
                                  onChange={(e) => setNewPrice(e.target.value)}
                                  className="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                                />
                              </div>
                            </div>
                            <div className="flex justify-center space-x-2 ml-4">
                              <button
                                className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-2 py-1 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
                                onClick={() => updateProductPrice()}
                              >
                                Update Price
                              </button>
                            </div>
                          </div>
                        </div>
                      )}
                    </div>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Delete */}
      <div className="container mx-auto">
        <div className="container max-w-3xl px-4 mx-auto sm:px-8">
          <div className="py-8">
            <div className="px-4 py-4 -mx-4 overflow-x-auto sm:-mx-8 sm:px-8">
              <div className="inline-block min-w-full overflow-hidden rounded-lg shadow">
                <table className="min-w-full leading-normal">
                  <thead className="sticky top-0 z-10 bg-white">
                    <tr>
                      <th
                        scope="col"
                        className="px-5 py-3 text-sm font-normal text-left text-gray-800 uppercase bg-white border-b border-gray-200"
                      >
                        <label className="flex items-center mb-3 space-x-3">
                          <input
                            type="checkbox"
                            checked={checked4}
                            onChange={(e) => setChecked4(!checked4)}
                            id="acceptdelete"
                            name="checked-demo"
                            className="form-tick appearance-none bg-white bg-check h-6 w-6 border border-gray-300 rounded-md checked:bg-blue-500 checked:border-transparent focus:outline-none"
                          />
                          <span className="font-normal text-gray-700 dark:text-white">
                            <strong>Delete a product</strong>
                          </span>
                        </label>
                      </th>
                    </tr>
                  </thead>
                </table>

                {checked4 && (
                  <div className="w-full flex">
                    <div
                      key={product[index]._id}
                      className="w-1/3 bg-cover bg-landscape"
                    >
                      <img
                        src={product[index].image}
                        style={{
                          objectFit: "contain",
                          maxHeight: "200px",
                          width: "100%",
                        }}
                      />
                    </div>
                    <div className="w-2/3 p-4">
                      <div className="ml-4">
                        Id: {product[index]._id} <br />
                        Title: {product[index].title} <br />
                        Category: {product[index].category} <br />
                        Price: {product[index].price} <br />
                        Rate: {product[index].rating.rate} <br />
                        Count:{product[index].rating.count} <br />
                      </div>
                    </div>
                  </div>
                )}
                {checked4 && (
                  <div className="flex justify-center space-x-2 mt-4">
                    <button
                      className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-3 py-1 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
                      onClick={() => getOneByOneProductPrev()}
                    >
                      Prev
                    </button>
                    <button
                      className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-3 py-1 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
                      onClick={() => getOneByOneProductNext()}
                    >
                      Next
                    </button>
                    <button
                      className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-3 py-1 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
                      onClick={() => deleteOneProduct(product[index]._id)}
                    >
                      Delete
                    </button>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
