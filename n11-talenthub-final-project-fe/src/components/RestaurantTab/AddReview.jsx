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
import { reviewAxios } from '../../utils/base-axios';

const AddReview = ({ restaurant }) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [values, setValues] = useState({});

  const handleSubmit = async (e) => {
    e.preventDefault();
    await reviewAxios.post('', { ...values, restaurantId: restaurant.id });
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
        Create review
      </Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Review information</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <form onSubmit={handleSubmit}>
              <Stack>
                <FormControl>
                  <FormLabel>Comment</FormLabel>
                  <Input onChange={handleChange('comment')} />
                </FormControl>
                <FormControl>
                  <FormLabel>User id</FormLabel>
                  <Input onChange={handleChange('userId')} />
                </FormControl>
                <FormControl>
                  <FormLabel>Rate</FormLabel>
                  <Select onChange={handleChange('rate')}>
                    <option value={1}>1</option>
                    <option value={2}>2</option>
                    <option value={3}>3</option>
                    <option value={4}>4</option>
                    <option value={5}>5</option>
                  </Select>
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

export default AddReview;
