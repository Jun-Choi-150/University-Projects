import React, { useState } from "react";
import "./App.css";
import NavBars from "./Navbars";
import Home from "./Home";
import CartSummary from "./OrderSummary";
import FormValidation from "./Checkout";
import OrderComplete from "./OrderComplete";
import { useSelector } from "react-redux";
import Edit from "./Edit";
import Credit from "./Credit"

function App() {
  const [currentPage, setCurrentPage] = useState("home");
  const summary = useSelector((state) => state.summary.list);

  const handleCheckoutClick = () => {
    setCurrentPage("summary");
  };

  const handleBackClick = () => {
    setCurrentPage("home");
  };

  const handlePayClick = () => {
    setCurrentPage("checkout");
  };

  const handleOrderHistory = () => {
    setCurrentPage("completed");
  };

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  return (
    <>
      {currentPage === "home" && (
        <div>
          <NavBars
            onCheckoutClick={handleCheckoutClick}
            onPageChange={handlePageChange}
            currentPage={currentPage}
          />
          <Home />
        </div>
      )}
      {currentPage === "summary" && (
        <div>
          <NavBars
            onCheckoutClick={handleCheckoutClick}
            onPageChange={handlePageChange}
            currentPage={currentPage}
          />
          <CartSummary
            onBackClick={handleBackClick}
            onPayClick={handlePayClick}
          />
        </div>
      )}
      {currentPage === "checkout" && (
        <div>
          <NavBars
            onCheckoutClick={handleCheckoutClick}
            onPageChange={handlePageChange}
            currentPage={currentPage}
          />
          <FormValidation onCompletePaymentClick={handleOrderHistory} />
        </div>
      )}
      {currentPage === "completed" && (
        <div>
          <NavBars
            onCheckoutClick={handleCheckoutClick}
            onPageChange={handlePageChange}
            currentPage={currentPage}
          />
          <OrderComplete summary={summary} />
        </div>
      )}

      {currentPage === "edit" && (
        <div>
          <NavBars
            onCheckoutClick={handleCheckoutClick}
            onPageChange={handlePageChange}
            currentPage={currentPage}
          />
          <Edit />
        </div>
      )}

      {currentPage === "team" && (
        <div>
          <NavBars
            onCheckoutClick={handleCheckoutClick}
            onPageChange={handlePageChange}
            currentPage={currentPage}
          />
          <Credit />
        </div>
      )}
    </>
  );
}

export default App;
