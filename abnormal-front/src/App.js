import logo from './logo.svg';
import './App.css';
import React from "react";
import { Route, Routes, BrowserRouter} from "react-router-dom";
import MainPage from './pages/MainPage';
import AbnormalBehaviorPage from './pages/AbnormalBehaviorPage';
import CctvPage from './pages/CctvPage';
import LoginPage from './pages/LoginPage';
import ResultPage from './pages/ResultPage';
import SearchPage from './pages/SearchPage';
import SignupPage from './pages/SignupPage';


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainPage/>} /> {/* 홈화면 페이지 <Route path="/" element={<MainPage/>}/>*/}
        <Route path="/abnormal" element={<AbnormalBehaviorPage/>}/>
        <Route path="/cctv" element={<CctvPage />}/>
        <Route path="/login" element={<LoginPage/>}/>
        <Route path="/result" element={<ResultPage/>}/>
        <Route path="/search" element={<SearchPage/>}/>
        <Route path="/signup" element={<SignupPage/>}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;