import { createSlice } from "@reduxjs/toolkit";

const cartSlice = createSlice({
  name: "cart",
  initialState: [],
  reducers: {
    addToCart: (state, action) => {
      const product = action.payload;
      const index = state.findIndex((item) => item.id === product.id);

      if (index === -1) {
        state.push({ ...product, quantity: 1 });
      } else {
        state[index].quantity += 1;
      }
    },
    removeFromCart: (state, action) => {
      const productID = action.payload;
      const index = state.findIndex((item) => item.id === productID);

      if (index !== -1) {
        if (state[index].quantity > 1) {
          state[index].quantity -= 1;
        } else {
          state.splice(index, 1);
        }
      }
    },
    resetFromCart: (state, action) => {
      const productID = action.payload;
      const index = state.findIndex((item) => item.id === productID);

      if (index !== -1) {
        state.splice(index, 1);
      }
    }
  },
});

export const { addToCart, removeFromCart, resetFromCart } = cartSlice.actions;
export default cartSlice.reducer;
