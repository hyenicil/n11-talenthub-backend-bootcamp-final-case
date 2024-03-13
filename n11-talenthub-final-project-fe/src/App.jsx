import { Heading, Stack } from '@chakra-ui/react';
import AppTabs from './components/AppTabs';
import { colors } from './main';

const App = () => {
  return (
    <Stack py={12}>
      <Heading alignSelf={'center'} color={colors.primary[500]}>
        N11 TALENTHUB
      </Heading>
      <AppTabs />
    </Stack>
  );
};

export default App;
