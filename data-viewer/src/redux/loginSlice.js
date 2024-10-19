import { createSlice } from "@reduxjs/toolkit";

export const loginSlice = createSlice({
    name: 'loginValue',
    initialState:
    {
        value: true
    },
    reducers:
    {
        createUserView: (state) =>
        {
            state.value = false;
        },
        loginView: (state) =>
        {
            state.value = true;
        }
    }
})

export const {createUserView, loginView} = loginSlice.actions

export default loginSlice.reducer