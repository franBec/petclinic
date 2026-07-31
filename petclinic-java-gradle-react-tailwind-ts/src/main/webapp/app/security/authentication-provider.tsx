import React, { createContext, useEffect, useState, ReactNode } from 'react';
import { useTranslation } from 'react-i18next';
import { useMatches, useNavigate } from 'react-router';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import axios from 'axios';

const queryClient = new QueryClient();

export const AuthenticationContext = createContext<{
  isLoggedIn: () => boolean;
  getToken: () => string | null;
  login: (accessToken: string) => string;
  logout: () => void;
}>({
  isLoggedIn: () => false,
  getToken: () => null,
  login: () => '',
  logout: () => {},
});

export const AuthenticationProvider = ({ children }: AuthenticationProviderParams) => {
  const { t } = useTranslation();
  const [initCompleted, setInitCompleted] = useState(false);
  const [, setRenderTrigger] = useState(0);
  const [loginSuccessUrl, setLoginSuccessUrl] = useState('/');
  const navigate = useNavigate();
  const matches = useMatches();
  const roles = matches.reduce((accumulator: string[], currentMatch) => {
    return accumulator.concat((currentMatch.handle as RolesHandle)?.roles || []);
  }, []);

  const getToken = () => {
    return localStorage.getItem('access_token');
  };

  const isLoggedIn = () => {
    const tokenData = getTokenData();
    return tokenData !== null && getCurrentSeconds() < tokenData.exp;
  };

  const login = (accessToken: string) => {
    localStorage.setItem('access_token', accessToken);
    setRenderTrigger((n) => n + 1);
    const navigateTo = loginSuccessUrl;
    setLoginSuccessUrl('/');
    return navigateTo;
  };

  const logout = () => {
    if (isLoggedIn()) {
      setLoginSuccessUrl('/');
    }
    localStorage.removeItem('access_token');
    setRenderTrigger((n) => n + 1);
    navigate('/login', {
      state: {
        msgInfo: t('authentication.logout.success'),
      },
    });
  };

  const hasAnyRole = () => {
    const tokenData = getTokenData();
    if (!tokenData) return false;
    return roles.some((requiredRole) => tokenData.roles.includes(requiredRole));
  };

  const getTokenData = () => {
    const token = getToken();
    if (!token) return null;
    const parts = token.split('.');
    if (parts.length !== 3) return null;
    try {
      const payload = parts[1]!;
      const base64 = payload.replace(/-/g, '+').replace(/_/g, '/');
      return JSON.parse(atob(base64));
    } catch {
      return null;
    }
  };

  const getCurrentSeconds = () => {
    return Math.floor(new Date().getTime() / 1000);
  };

  // clean up any stale token on startup
  if (getTokenData() === null && localStorage.getItem('access_token')) {
    localStorage.removeItem('access_token');
  }

  useEffect(() => {
    // include token in outgoing requests
    const interceptor = axios.interceptors.request.use(
      (config) => {
        if (localStorage.getItem('access_token')) {
          config.headers['Authorization'] = 'Bearer ' + localStorage.getItem('access_token');
        }
        return config;
      },
      (error) => {
        return Promise.reject(error);
      },
    );
    setInitCompleted(true);

    return () => axios.interceptors.request.eject(interceptor);
  }, []);

  const checkAccessAllowed = () => {
    if (roles.length > 0 && !isLoggedIn()) {
      return 'login-required';
    } else if (roles.length > 0 && !hasAnyRole()) {
      return 'missing-role';
    }
    return null;
  };

  useEffect(() => {
    const accessError = checkAccessAllowed();
    if (!isLoggedIn() && ['/login', '/error'].indexOf(location.pathname) === -1) {
      setLoginSuccessUrl(location.pathname);
    }
    if (accessError === 'login-required') {
      setLoginSuccessUrl(location.pathname);
      navigate('/login', {
        state: {
          msgInfo: t('authentication.login.required'),
        },
      });
    } else if (accessError === 'missing-role') {
      navigate('/error', {
        state: {
          errorStatus: '403',
          msgError: t('authentication.role.missing'),
        },
      });
    }
  }, [matches]);

  if (checkAccessAllowed() !== null) {
    // don't render current route
    return;
  }
  return (
    <AuthenticationContext.Provider value={{ isLoggedIn, getToken, login, logout }}>
      {initCompleted && <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>}
    </AuthenticationContext.Provider>
  );
};

interface AuthenticationProviderParams {
  children: ReactNode;
}

interface RolesHandle {
  roles?: string[];
}
