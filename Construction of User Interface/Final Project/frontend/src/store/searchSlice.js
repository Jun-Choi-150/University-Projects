import { createSlice } from "@reduxjs/toolkit";

const searchedListSlice = createSlice({
  name: "search",
  initialState: { list: [] },
  reducers: {
    changedList: (state, action) => {
      state.list = action.payload;
    },
  },
});

export const { changedList } = searchedListSlice.actions;
export default searchedListSlice.reducer;
