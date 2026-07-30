import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router';
import App from './app';
import Home from './home/home';
import Authentication from './security/authentication';
import Error from './error/error';

export default function AppRoutes() {
  const router = createBrowserRouter([
    {
      element: <App />,
      children: [
        { path: '', element: <Home /> },
        { path: 'login', element: <Authentication /> },
        { path: 'error', element: <Error /> },
        { path: '*', element: <Error /> },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
}
