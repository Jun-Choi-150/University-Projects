import { createSlice } from "@reduxjs/toolkit";

const summarySlice = createSlice({
  name: "summary",
  initialState: { list: [] },
  reducers: {
    makeSummary: (state, action) => {
      state.list = []; // Clear the existing summary list
      state.list = action.payload;
    },
  },
});

export const { makeSummary } = summarySlice.actions;
export default summarySlice.reducer;
