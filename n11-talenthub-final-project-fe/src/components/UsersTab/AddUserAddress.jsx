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
} from '@chakra-ui/react';
import { useState } from 'react';
import { addressAxios } from '../../utils/base-axios';

const AddUserAddress = ({ user }) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [values, setValues] = useState({});

  const handleSubmit = async (e) => {
    e.preventDefault();
    await addressAxios.post('', { ...values, userId: user.id });
    onClose();
  };

  const handleChange = (key) => (e) => {
    setValues((prev) => {
      return { ...prev, [key]: e.target.value };
    });
  };

  return (
    <>
      <Button variant={'ghost'} size={'sm'} onClick={onOpen}>
        Create address
      </Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Address information</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <form onSubmit={handleSubmit}>
              <Stack>
                <FormControl>
                  <FormLabel>City</FormLabel>
                  <Input onChange={handleChange('city')} />
                </FormControl>
                <FormControl>
                  <FormLabel>District</FormLabel>
                  <Input onChange={handleChange('district')} />
                </FormControl>
                <FormControl>
                  <FormLabel>Street</FormLabel>
                  <Input onChange={handleChange('street')} />
                </FormControl>
                <FormControl>
                  <FormLabel>Location</FormLabel>
                  <Input onChange={handleChange('location')} />
                </FormControl>

                <Button type='submit'>Create</Button>
              </Stack>
            </form>
          </ModalBody>

          <ModalFooter></ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default AddUserAddress;
