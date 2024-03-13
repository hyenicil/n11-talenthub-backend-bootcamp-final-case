/* eslint-disable react/prop-types */
import {
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  useDisclosure,
  Button,
  Stack,
  FormControl,
  Input,
  FormLabel,
  Select,
} from '@chakra-ui/react';
import { useState } from 'react';
import { userAxios } from '../../utils/base-axios';

const UpdateUser = ({ afterSave, user }) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [values, setValues] = useState(user);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await userAxios.patch(`/${user.id}`, values);
    afterSave();
    onClose();
  };

  const handleChange = (key) => (e) => {
    setValues((prev) => {
      return { ...prev, [key]: e.target.value };
    });
  };

  return (
    <>
      <Button size={'sm'} onClick={onOpen}>
        Update
      </Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>User information</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <form onSubmit={handleSubmit}>
              <Stack>
                <FormControl>
                  <FormLabel>Name</FormLabel>
                  <Input
                    defaultValue={user.name}
                    onChange={handleChange('name')}
                  />
                </FormControl>
                <FormControl>
                  <FormLabel>Surname</FormLabel>
                  <Input
                    defaultValue={user.surname}
                    onChange={handleChange('surname')}
                  />
                </FormControl>
                <FormControl>
                  <FormLabel>Email</FormLabel>
                  <Input
                    defaultValue={user.email}
                    onChange={handleChange('email')}
                  />
                </FormControl>
                <FormControl>
                  <FormLabel>Birth date</FormLabel>
                  <Input
                    defaultValue={user.birthDate? user.birthDate : ""}
                    onChange={handleChange('birthDate')}
                  />
                </FormControl>
                <FormControl>
                  <FormLabel>Gender</FormLabel>
                  <Select
                    defaultValue={user.gender}
                    placeholder='Select option'
                    onChange={handleChange('gender')}
                  >
                    <option value='MALE'>Male</option>
                    <option value='FEMALE'>Female</option>
                  </Select>
                </FormControl>
                <Button type='submit'>Update</Button>
              </Stack>
            </form>
          </ModalBody>

          <ModalFooter></ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default UpdateUser;
