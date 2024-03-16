import { useCallback, useEffect, useState } from "react";
import { userAxios } from "../../utils/base-axios";
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
  Box,
} from "@chakra-ui/react";
import AddUser from "./AddUser";
import UpdateUser from "./UpdateUser";
import DeleteUser from "./DeleteUser";
import AddUserAddress from "./AddUserAddress";
import Recommendations from "./Recommendations";

const UsersTab = () => {
  const [users, setUsers] = useState([]);

  const fetchUsers = useCallback(() => {
    userAxios.get("").then((res) => {
      setUsers(res.data.data);
    });
  }, []);

  useEffect(() => {
    fetchUsers();
  }, [fetchUsers]);

  if (!users.length)
    return (
      <Stack>
        <Flex justify={"start"}>
          <AddUser afterSave={fetchUsers} />
        </Flex>
        <Text alignSelf={"center"}>No Data</Text>
      </Stack>
    );

  return (
    <Stack>
      <Flex justify={"start"}>
        <AddUser afterSave={fetchUsers} />
      </Flex>
      <TableContainer>
        <Table variant="striped" colorScheme="blackAlpha">
          <Thead>
            <Tr>
              <Th>Id</Th>
              <Th>Name</Th>
              <Th>Surname</Th>
              <Th>Birth date</Th>
              <Th>Email</Th>
              <Th>Gender</Th>
              <Th>Action</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {users.map((user) => {
              return (
                <Tr key={user.id}>
                  <Td>{user.id}</Td>
                  <Td>{user.name}</Td>
                  <Td>{user.surname}</Td>
                  {user.birthDate ? <Td>{user.birthDate}</Td> : nu}
                  <Td>{user.email}</Td>
                  <Td>{user.gender}</Td>
                  <Td>
                    <Flex gap={2}>
                      <DeleteUser afterSave={fetchUsers} user={user} />
                      <UpdateUser afterSave={fetchUsers} user={user} />
                    </Flex>
                  </Td>
                  <Td>
                    <Flex gap={2}>
                      <AddUserAddress user={user} />
                      <Recommendations user={user} />
                    </Flex>
                  </Td>
                </Tr>
              );
            })}
          </Tbody>
        </Table>
      </TableContainer>
    </Stack>
  );
};

export default UsersTab;
