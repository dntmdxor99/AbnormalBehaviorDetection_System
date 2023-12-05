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
import Button from "@mui/material/Button";
import Header from "./components/Header";
import PageLayout from "./components/PageLayout";
import { AuthProvider } from "./context/AuthContext";


function App() {
  return (
    <AuthProvider>
      <>
      <BrowserRouter>
        <Header>
          <div className="menu">
            <div className="menu-bar">
              <Link to="/" className="menu-home-item">
                7팀
              </Link>
              <Link to="/abnormal" className="menu-bar-item">
                이상행동
              </Link>
              <Link to="/cctv" className="menu-bar-item">
                CCTV
              </Link>
              <Link to="/ask" className="menu-bar-item">
                문의하기
              </Link>
              <div className="menu-bar-buttons">
                <Link>
                  <Link to="/login">
                    <Button variant="outlined">로그인</Button>
                  </Link>
                </Link>

                <Link to="/signup">
                  <Button variant="contained">가입하기</Button>
                </Link>
              </div> 
            </div>
          </div>
        </Header>
        <Routes>
          <Route path="/" element={<MainPage />} />{" "}
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
    </AuthProvider>
    
  );
}

export default App;