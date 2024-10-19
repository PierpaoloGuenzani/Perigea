import { configureStore } from '@reduxjs/toolkit'
import authenticationReducer from './redux/authenticationSlice'
import loginReducer from './redux/loginSlice'

export default configureStore({
  reducer:
  {
    authentication: authenticationReducer,
    login: loginReducer
  }
})

