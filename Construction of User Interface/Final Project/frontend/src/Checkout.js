import React, { useRef } from "react";
import { useSelector, useDispatch } from "react-redux";
import { makeSummary } from "./store/summarySlice.js";

export default function FormValidation({onCompletePaymentClick }) {
  
  const summary = useSelector((state) => state.summary.list);
  let summaryTemp = [];
  const formRef = useRef(null);
  const alertPlaceholderRef = useRef(null);
  //const summary = useSelector((state) => state.summary);
  const inputCardRef = useRef();
  const dispatch = useDispatch();
  

  const isNumeric = (n) => {
    return !isNaN(parseFloat(n)) && isFinite(n);
  };

  const handleCardInput = (event) => {
    let inputCard = event.target;
    if (!inputCard.value) {
      event.preventDefault();
    } else {
      inputCard.value = inputCard.value.replace(/-/g, "");
      let newVal = "";
      for (let i = 0, nums = 0; i < inputCard.value.length; i++) {
        if (nums !== 0 && nums % 4 === 0) {
          newVal += "-";
        }
        newVal += inputCard.value[i];
        if (isNumeric(inputCard.value[i])) {
          nums++;
        }
      }
      inputCard.value = newVal;
    }
  };

  const showAlert = (message, type) => {
    const wrapper = document.createElement("div");
    wrapper.innerHTML = [
      `<div class="alert alert-${type} alert-dismissible" role="alert">`,
      ` <div>${message}</div>`,
      ' <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
      "</div>",
    ].join("");
    alertPlaceholderRef.current.append(wrapper);
  };

  const validate = () => {
    let val = true;
    const email = formRef.current["inputEmail4"];
    const name = formRef.current["inputName"];
    const card = formRef.current["inputCard"];
    const zip = formRef.current["inputZip"];
    
    const emailPattern = /^\S+@\S+\.\S+$/;
    const zipPattern = /^[a-zA-Z0-9]{5}$/;
   
    if (name.value.length === 0) {
      name.classList.add("is-invalid");
      val = false;
    } else {
      name.classList.remove("is-invalid");
      name.classList.add("is-valid");
      summaryTemp.push({ key: "Name", value: name.value });
    }
  
    if (!email.value.match(emailPattern)) {
      email.classList.add("is-invalid");
      val = false;
    } else {
      email.classList.remove("is-invalid");
      email.classList.add("is-valid");
      summaryTemp.push({ key: "Email", value: email.value });
    }
  
    if (!card.value.match(/^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$/)) {
      card.classList.add("is-invalid");
      val = false;
    } else {
      card.classList.remove("is-invalid");
      card.classList.add("is-valid");
      summaryTemp.push({ key: "Card", value: card.value });
    }
  
    if (!zip.value.match(zipPattern)) {
      zip.classList.add("is-invalid");
      val = false;
    } else {
      zip.classList.remove("is-invalid");
      zip.classList.add("is-valid");
      summaryTemp.push({ key: "ZIP", value: zip.value });
    }

    return val;
  };
  
  const handleSubmit = (event) => {
    event.preventDefault(); // Add this line to prevent the form from submitting
    const validationResult = validate();
    if (!validationResult) {
      event.stopPropagation();
    } else {
      dispatch(makeSummary(summaryTemp));
      onCompletePaymentClick();
    }
  };
  
  
  return (
    <div className="container mx-auto">
      <div className="flex justify-center">
        <div className="w-2/12"></div>

        <div className="w-8/12">
          <h1 className="text-4xl font-bold mb-5">
            Payment
          </h1>

          <div id="liveAlertPlaceholder" ref={alertPlaceholderRef}></div>

          {/* Form Section */}
          <form
            className={!formRef.current ? "space-y-5" : "space-y-5 collapse"}
            id="checkout-form"
            onSubmit={handleSubmit}
            ref={formRef}
          >
            {/* Full Name */}
            <div className="w-full">
              <label htmlFor="inputName" className="block mb-1">
                Full Name
              </label>
              <input
                type="text"
                className="w-full border-2 p-2"
                id="inputName"
              />
            </div>

            {/* Email */}
            <div className="w-full">
              <label htmlFor="inputEmail4" className="block mb-1">
                Email
              </label>
              <input
                type="email"
                className="w-full border-2 p-2"
                id="inputEmail4"
              />
            </div>

            {/* Credit Card */}
            <div className="w-full">
              <label htmlFor="inputCard" className="block mb-1">
                Card
              </label>
              <div className="flex items-center border-2 p-2">
                <i className="bi bi-credit-card-fill"></i>
                <input
                  type="text"
                  id="inputCard"
                  className="flex-grow ml-3"
                  placeholder="XXXX-XXXX-XXXX-XXXX"
                  onInput={handleCardInput}
                  ref={inputCardRef}
                />
              </div>
            </div>

            {/* Address */}
            <div className="w-full">
              <label htmlFor="inputAddress" className="block mb-1">
                Address
              </label>
              <input
                type="text"
                className="w-full border-2 p-2"
                id="inputAddress"
                placeholder="1234 Main St"
              />
            </div>
            {/* Address 2 */}
            <div className="w-full">
              <label htmlFor="inputAddress2" className="block mb-1">
                Address 2
              </label>
              <input
                type="text"
                className="w-full border-2 p-2"
                id="inputAddress2"
                placeholder="Apartment, studio, or floor"
              />
            </div>

            {/* City */}
            <div className="w-full md:w-1/2">
              <label htmlFor="inputCity" className="block mb-1">
                City
              </label>
              <input
                type="text"
                className="w-full border-2 p-2"
                id="inputCity"
              />
            </div>
            {/* State */}
            <div className="w-full md:w-1/3">
              <label htmlFor="inputState" className="block mb-1">
                State
              </label>
              <select id="inputState" className="w-full border-2 p-2">
                <option selected>Choose...</option>
              </select>
            </div>
            {/* Zip */}
            <div className="w-full md:w-1/6">
              <label htmlFor="inputZip" className="block mb-1">
                Zip
              </label>
              <input
                type="text"
                className="w-full border-2 p-2"
                id="inputZip"
              />
            </div>

            {/* Check me out */}
            <div className="w-full">
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  id="gridCheck"
                />
                <label className="form-check-label" htmlFor="gridCheck">
                  Check me out
                </label>
              </div>
            </div>

            {/* Submit Button */}
            <div className="w-full">
              <button
                type="submit"
                className="w-full bg-green-500 text-white py-2 mt-5"
              >
                <i className="bi bi-bag-check"></i> Order
              </button>
            </div>
          </form>
        </div>

        <div className="w-2/12"></div>
      </div>
    </div>
  );
}
