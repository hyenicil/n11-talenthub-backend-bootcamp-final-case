import { Center, Heading, Stack } from '@chakra-ui/react';
import AppTabs from './components/AppTabs';
import { colors } from './main';

const App = () => {
  return (
    <Stack>
      <Heading py={6} textAlign={'Center'} bgColor={'primary.100'} color={colors.primary[700]}>
        N11 TALENTHUB
      </Heading>
      <AppTabs />
    </Stack>
  );
};

export default App;
