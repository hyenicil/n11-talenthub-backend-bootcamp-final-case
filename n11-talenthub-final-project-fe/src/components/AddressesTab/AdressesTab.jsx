import { useCallback, useEffect, useState } from 'react';
import { addressAxios } from '../../utils/base-axios';
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Stack,
  Flex,
  Text,
} from '@chakra-ui/react';
import UpdateAddress from './UpdateAddress';
import DeleteAddress from './DeleteAddress';

const AddressesTab = () => {
  const [addresses, setAddresses] = useState([]);

  const fetchAddresses = useCallback(() => {
    addressAxios.get('').then((res) => {
      setAddresses(res.data.data);
    });
  }, []);

  useEffect(() => {
    fetchAddresses();
  }, [fetchAddresses]);

  if (!addresses.length)
    return (
      <Stack align={'center'}>
        <Text>No Data</Text>
      </Stack>
    );

  return (
    <TableContainer>
      <Table>
        <Thead>
          <Tr>
            <Th>Id</Th>
            <Th>City</Th>
            <Th>District</Th>
            <Th>Street</Th>
            <Th>Location</Th>
            <Th>User id</Th>
          </Tr>
        </Thead>
        <Tbody>
          {addresses.map((address) => {
            const [lat, lng] = address.location.split(',');
            const location = `(${lat} , ${lng})`;
            return (
              <Tr key={address.id}>
                <Td>{address.id}</Td>
                <Td>{address.city}</Td>
                <Td>{address.district}</Td>
                <Td>{address.street}</Td>
                <Td>{location}</Td>
                <Td>{address.userId}</Td>
                <Td>
                  <Flex gap={2}>
                    <DeleteAddress
                      afterSave={fetchAddresses}
                      address={address}
                    />
                    <UpdateAddress
                      afterSave={fetchAddresses}
                      address={address}
                    />
                  </Flex>
                </Td>
              </Tr>
            );
          })}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default AddressesTab;
