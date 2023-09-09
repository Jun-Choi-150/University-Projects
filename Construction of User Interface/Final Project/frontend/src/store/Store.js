
import { configureStore } from "@reduxjs/toolkit";
import cartReducer from "./cartSlice.js";
import searchedListReducer from "./searchSlice.js";
import summaryReducer from "./summarySlice.js";

const store = configureStore({
  reducer: {
    cart: cartReducer,
    search: searchedListReducer,
    summary: summaryReducer,
  },
});

export default store;
