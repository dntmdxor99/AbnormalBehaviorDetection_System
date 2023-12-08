import logo from "./logo.svg";
import "./App.css";
import React from "react";
import { Route, Routes, BrowserRouter } from "react-router-dom";
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
import { RecoilRoot } from "recoil";



function App() {
  return (
    <>
      <RecoilRoot>
        <AuthProvider>
          <BrowserRouter>
            <Header />
            <Routes>
              <Route path="/" element={<MainPage />} />
              <Route path="/abnormal" element={<AbnormalBehaviorPage />} />
              <Route path="/cctv" element={<CctvPage />} />
              <Route path="/ask" element={<AskPage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/result" element={<ResultPage />} />
              <Route path="/search" element={<SearchPage />} />
              <Route path="/signup" element={<SignupPage />} />
            </Routes>
          </BrowserRouter>
        </AuthProvider>
      </RecoilRoot>
    </>
  );
}


export default App;
