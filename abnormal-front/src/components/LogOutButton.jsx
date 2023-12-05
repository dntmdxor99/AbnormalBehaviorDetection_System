import React, { useContext } from "react";
import Button from "@mui/material/Button";
import { AuthContext } from "../App";


const LogoutButton = () => {
  const { setUser } = useContext(AuthContext);

  const handleLogout = () => {
    setUser(null);
  };

  return (
    <Button variant="contained" onClick={handleLogout}>
      로그아웃
    </Button>
  );
};


export default LogoutButton;