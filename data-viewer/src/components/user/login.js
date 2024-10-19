import { Button, TextField } from "@mui/material";
import { useContext, useRef, useState } from "react";
import { useDispatch } from "react-redux";
import { login, logout } from "../../redux/authenticationSlice";
import { properties } from "../../properties";
import { createUserView } from "../../redux/loginSlice";
import './login.css';

export function LoginComponent()
{

    const dispatch = useDispatch()

    const usernameRef = useRef();
    const passwordRef = useRef();

    const handleClick = () =>
    {
        fetch(properties.LOGIN_URL,
            {
                method: "POST",
                headers:
                {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({"username":usernameRef.current.value, "password":passwordRef.current.value})
            }
        ).then(
            response => response.ok? dispatch(login()): dispatch(logout())
        )
    }

    const handleCreate = () =>
    {
        dispatch(createUserView())
    }

    return(
        <div className="login">
            <TextField size="small" placeholder="Username" inputRef={usernameRef}></TextField>
            <TextField size="small" type="password" placeholder="Password" inputRef={passwordRef}></TextField>
            <Button variant="contained" size="large" onClick={handleClick}>Login</Button>
            <Button variant="contained" size="large" onClick={handleCreate}>Create new user</Button>
        </div>
    );
}