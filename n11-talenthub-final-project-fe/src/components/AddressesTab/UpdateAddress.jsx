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

const UpdateAddress = ({ afterSave, address }) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [values, setValues] = useState(address);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await addressAxios.patch(`/${address.id}`, values);
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
          <ModalHeader>Address information</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <form onSubmit={handleSubmit}>
              <Stack>
                <FormControl>
                  <FormLabel>City</FormLabel>
                  <Input
                    defaultValue={address.city}
                    onChange={handleChange('city')}
                  />
                </FormControl>
                <FormControl>
                  <FormLabel>District</FormLabel>
                  <Input
                    defaultValue={address.district}
                    onChange={handleChange('district')}
                  />
                </FormControl>
                <FormControl>
                  <FormLabel>Street</FormLabel>
                  <Input
                    defaultValue={address.street}
                    onChange={handleChange('street')}
                  />
                </FormControl>
                <FormControl>
                  <FormLabel>Location</FormLabel>
                  <Input
                    defaultValue={address.location}
                    onChange={handleChange('location')}
                  />
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

export default UpdateAddress;
