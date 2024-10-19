import './App.css';
import { useSelector } from 'react-redux';
import MainPage from './components/main-page';
import LoginView from './components/login-view';
import { properties } from './properties';

function App()
{
  const value = useSelector(state => state.authentication.value);
  properties.setDocker();
  //inizio
  return (
    <div>
      <div className="main-page">
        {value? <MainPage/> : <LoginView/>}
      </div>
    </div>
  );
}

export default App;
