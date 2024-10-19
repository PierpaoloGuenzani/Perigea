import UserForm from "./user/user-form";
import { useSelector } from "react-redux";
import { LoginComponent } from "./user/login";
import './login-view.css';

export default function LoginView()
{
    const loginView = useSelector(state => state.login.value)

    return (
        <div className="main">
            {loginView? <LoginComponent/> : <UserForm/>}
        </div>
    )
}