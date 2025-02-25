import { createSlice } from '@reduxjs/toolkit'

export const authenticationSlice = createSlice({
  name: 'authentication',
  initialState:
  {
    value: false
  },
  reducers:
  {
    login: (state) => {
      state.value = true;
    },
    logout: (state) =>
    {
        state.value = false;
    }
  }
})

export const { login, logout } = authenticationSlice.actions

export default authenticationSlice.reducer