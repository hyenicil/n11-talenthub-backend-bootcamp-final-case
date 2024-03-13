import { Tabs, TabList, TabPanels, Tab, TabPanel } from '@chakra-ui/react';
import UsersTab from './UsersTab/UsersTab';
import RestaurantsTab from './RestaurantTab/RestaurantTab';
import AddressesTab from './AddressesTab/AdressesTab';

const AppTabs = () => {
  return (
    <Tabs isLazy p={12} gap={12}>
      <TabList>
        <Tab>Users</Tab>
        <Tab>Addresses</Tab>
        <Tab>Restaurants</Tab>
      </TabList>

      <TabPanels>
        <TabPanel>
          <UsersTab />
        </TabPanel>
        <TabPanel>
          <AddressesTab />
        </TabPanel>
        <TabPanel>
          <RestaurantsTab />
        </TabPanel>
      </TabPanels>
    </Tabs>
  );
};

export default AppTabs;
