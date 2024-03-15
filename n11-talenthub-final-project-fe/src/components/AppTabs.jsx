import { Tabs, TabList, TabPanels, Tab, TabPanel } from '@chakra-ui/react';
import UsersTab from './UsersTab/UsersTab';
import RestaurantsTab from './RestaurantTab/RestaurantTab';
import AddressesTab from './AddressesTab/AdressesTab';

const AppTabs = () => {
  return (
    <Tabs isFitted variant='soft-rounded' isLazy px={12} py={2} gap={12}>
      <TabList p={2} bgColor={'primary.50'} rounded={'full'}>
        <Tab  _selected={{ color: 'white', bg: 'primary.500' }}>Users</Tab>
        <Tab  _selected={{ color: 'white', bg: 'primary.500' }}>Addresses</Tab>
        <Tab  _selected={{ color: 'white', bg: 'primary.500' }}>Restaurants</Tab>
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
