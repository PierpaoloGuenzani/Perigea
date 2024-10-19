import { Button, TextField } from "@mui/material";
import { useRef } from "react";
import { properties } from "../../properties";
import { useState } from "react";
import { useDispatch } from "react-redux";
import { login, loginView } from "../../redux/loginSlice";


export default function UserForm()
{
    const dispatch = useDispatch();

    const [state, setState] = useState(false);
    const usernameRef = useRef(null);
    const passwordRef = useRef(null);
    const passwordCheckRef = useRef(null);

    const myFetchFunction = () =>
    {
        if(passwordRef.current.value !== passwordCheckRef.current.value)
        {
            setState(true);
            return
        }
        fetch(properties.CREATE_USER,
            {
                method: "POST",
                headers:
                {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({username: usernameRef.current.value, password: passwordRef.current.value})
            }).then(response => 
                {
                    console.log(response.ok);
                    dispatch(loginView())
                })
    }
    return(
        <div>
            <TextField placeholder="username" inputRef={usernameRef} size="small"></TextField>
            <TextField placeholder="password" inputRef={passwordRef} size="small"></TextField>
            <TextField placeholder="password check"
            inputRef={passwordCheckRef} 
            error={state}
            size="small"></TextField>
            <Button variant="contained" size="large" onClick={myFetchFunction}>Create</Button>
        </div>
    );
}