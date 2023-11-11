import logo from "./logo.svg";
import "./App.css";
import React from "react";
import { Route, Routes, BrowserRouter, Link } from "react-router-dom";
import MainPage from "./pages/MainPage";
import AbnormalBehaviorPage from "./pages/AbnormalBehaviorPage";
import CctvPage from "./pages/CctvPage";
import AskPage from "./pages/AskPage";
import LoginPage from "./pages/LoginPage";
import ResultPage from "./pages/ResultPage";
import SearchPage from "./pages/SearchPage";
import SignupPage from "./pages/SignupPage";
import Header from "./components/Header";

function App() {
  return (
    <>
      <BrowserRouter>
      <Header>
        <div className="menu">
          <div className="menu-bar">
            <Link to="/" className="menu-bar-item">7팀</Link>
            <Link to='/abnormal' className="menu-bar-item">이상행동</Link>
            <Link to='/cctv' className="menu-bar-item">CCTV</Link>
            <Link to='/ask' className="menu-bar-item">문의하기</Link>
          </div>
        </div>
      </Header>
        <Routes>
          <Route path="/" element={<MainPage />} />{" "}
          {/* 홈화면 페이지 <Route path="/" element={<MainPage/>}/>*/}
          <Route path="/abnormal" element={<AbnormalBehaviorPage />} />
          <Route path="/cctv" element={<CctvPage />} />
          <Route path="/ask" element={<AskPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/result" element={<ResultPage />} />
          <Route path="/search" element={<SearchPage />} />
          <Route path="/signup" element={<SignupPage />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
