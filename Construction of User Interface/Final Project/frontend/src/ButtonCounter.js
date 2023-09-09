import React, { useState, useEffect } from 'react';
import { Button } from "@material-tailwind/react";
import { useDispatch, useSelector } from "react-redux";
import { addToCart, removeFromCart } from "./store/cartSlice.js";

function ButtonCounter({ product })  {
  const dispatch = useDispatch();
  const cartItems = useSelector((state) => state.cart);
  const [productQuantity, setProductQuantity] = useState(0);

  const increment = (e) => {
    e.stopPropagation();
    dispatch(addToCart(product));
  };

  const decrement = (e) => {
    e.stopPropagation();
    if (productQuantity > 0) {
      dispatch(removeFromCart(product.id));
    }
  };

  useEffect(() => {
    const cartItem = cartItems.find((item) => item.id === product.id);
    setProductQuantity(cartItem ? cartItem.quantity : 0);
  }, [cartItems, product.id]);

  return (
    <div style={{ display: 'flex', justifyContent: 'right', alignItems: 'center', gap: '1rem' }}>
      <Button
        size="sm"
        variant="outlined"
        onClick={decrement}
        color="gray"
        style={{ fontSize: "0.75rem", padding: "0.25rem 0.5rem", borderRadius: "4px" }}
      >
        -
      </Button>
      <span>{productQuantity}</span>
      <Button
        size="sm"
        variant="outlined"
        onClick={increment}
        color="gray"
        style={{ fontSize: "0.75rem", padding: "0.25rem 0.5rem", borderRadius: "4px" }}
      >
        +
      </Button>
    </div>
  );
}

export default ButtonCounter;
