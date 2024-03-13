import ReactDOM from 'react-dom/client';
import App from './App.jsx';
import { ChakraProvider, extendTheme } from '@chakra-ui/react';

export const colors = {
  primary: {
    50: '#efeffe',
    100: '#e1e2fe',
    200: '#c9c9fc',
    300: '#aaa9f8',
    400: '#9186f3',
    500: '#7f69eb',
    600: '#714dde',
    700: '#633fc3',
    800: '#573aac',
    900: '#43327d',
    950: '#291d49',
  },
};

export const extendedTheme = extendTheme({
  colors,
  components: {
    Button: {
      defaultProps: {
        colorScheme: 'primary',
      },
    },
    Tabs: {
      defaultProps: {
        colorScheme: 'primary',
      },
    },
  },
});

ReactDOM.createRoot(document.getElementById('root')).render(
  <ChakraProvider theme={extendedTheme}>
    <App />
  </ChakraProvider>,
);
