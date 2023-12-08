import React from 'react';
import { Route, Navigate } from 'react-router-dom';
import { Routes } from 'react-router-dom/dist';

function PrivateRoute ({ element: Element, ...rest }) {
    return (
        <Routes>
            <Route
            {...rest}
            element={localStorage.getItem('login-token') ? (
                    <Element />
                ) : (
                    <Navigate to="/login" state={{from: rest.location}} />
                )
            }
            />
        </Routes>
    )
}

export default PrivateRoute;

