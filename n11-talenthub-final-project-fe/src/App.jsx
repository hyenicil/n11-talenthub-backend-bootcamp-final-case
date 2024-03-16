import { Center, Flex, Heading, Stack, Image } from "@chakra-ui/react";
import AppTabs from "./components/AppTabs";
import { colors } from "./main";

const App = () => {
  return (
    <Stack>
      <Flex
        align={"Center"}
        gap={4}
        bgColor={"primary.100"}
        py={4}
        justify={"Center"}
      >
        <Image src="/n11_logo.svg" width={32} height={32} />
        <Heading color={colors.primary[700]}>TALENTHUB</Heading>
      </Flex>
      <AppTabs />
    </Stack>
  );
};

export default App;
