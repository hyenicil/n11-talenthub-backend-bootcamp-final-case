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
import { restaurantAxios } from '../../utils/base-axios';

// eslint-disable-next-line react/prop-types
const AddRestaurant = ({ afterSave }) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [values, setValues] = useState({});

  const handleSubmit = async (e) => {
    e.preventDefault();
    await restaurantAxios.post('', values);
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
      <Button onClick={onOpen}>Create restaurant</Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Restaurant information</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <form onSubmit={handleSubmit}>
              <Stack>
                <FormControl>
                  <FormLabel>Name</FormLabel>
                  <Input onChange={handleChange('name')} />
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

export default AddRestaurant;
