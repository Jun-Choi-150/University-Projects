import React from "react";
import { useSelector } from "react-redux";

function CartSummary({ onBackClick, onPayClick }) {
  const cart = useSelector((state) => state.cart);

  const calculateSubtotal = () => {
    return cart.reduce((total, item) => total + item.quantity * item.price, 0);
  };

  const calculateTax = () => {
    const subtotal = calculateSubtotal();
    const taxRate = 0.06; // Iowa's tax rate (6%)
    const taxAmount = subtotal * taxRate;
    return taxAmount.toFixed(2);
  };

  const calculateTotal = () => {
    const subtotal = calculateSubtotal();
    const taxAmount = parseFloat(calculateTax());
    const total = subtotal + taxAmount;
    return total.toFixed(2);
  };

  return (
    <div className="bg-white max-w-2xl mx-auto px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">
      <h2 className="text-2xl font-bold tracking-tight text-gray-900">
        Order Summary
      </h2>

      <div className="mt-6 space-y-4">
        {cart.map((product) => (
          <div
            key={product._id}
            className="bg-gray-100 p-4 rounded-md flex justify-between items-center"
          >
            <div className="flex items-center">
              <div className="h-16 w-16 flex-shrink-0 overflow-hidden rounded-md bg-gray-200 lg:h-16 lg:w-16">
                <img
                  src={product.image}
                  alt={product.title}
                  className="h-full w-full object-cover object-center lg:h-full lg:w-full"
                />
              </div>
              <div className="ml-4">
                <h3 className="text-sm text-gray-700">{product.title}</h3>
                <p className="mt-1 text-sm text-gray-500">
                  Qty: {product.quantity}
                </p>
              </div>
            </div>
            <p className="text-sm font-medium text-gray-900">
              ${(product.price * product.quantity).toFixed(2)}
            </p>
          </div>
        ))}
      </div>

      <div className="mt-8">
        <div className="flex justify-between text-base font-medium text-gray-900">
          <p></p>
          <p>
            Subtotal: ${calculateSubtotal().toFixed(2)} + Tax: ${calculateTax()}
          </p>
        </div>
        <div className="flex justify-between text-base font-medium text-gray-900">
          <p></p>
          <p>Total:${calculateTotal()}</p>
        </div>
        <p></p>
        <div>
          <div className="flex justify-end space-x-4 mt-4">
            <button
              onClick={onBackClick}
              className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-5 py-2 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
            >
              Back
            </button>
            <button
              onClick={onPayClick}
              className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-5 py-2 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
            >
              Pay
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CartSummary;
