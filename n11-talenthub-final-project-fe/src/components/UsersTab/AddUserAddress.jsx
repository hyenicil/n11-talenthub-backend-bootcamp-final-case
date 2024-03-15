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
  FormErrorMessage,
} from "@chakra-ui/react";
import { useState } from "react";
import { addressAxios } from "../../utils/base-axios";

const AddUserAddress = ({ user }) => {
  const { isOpen, onOpen, onClose } = useDisclosure({
    onClose: () => setErrors({}),
  });
  const [values, setValues] = useState({});
  const [errors, setErrors] = useState({});

  const handleSubmit = async (e) => {
    e.preventDefault();
    await addressAxios
      .post("", { ...values, userId: user.id })
      .then(() => {
        afterSave();
        onClose();
      })
      .catch((data) => {
        setErrors(data.response.data.data.details);
      });
  };

  const handleChange = (key) => (e) => {
    setValues((prev) => {
      return { ...prev, [key]: e.target.value };
    });
  };

  return (
    <>
      <Button variant={"ghost"} size={"sm"} onClick={onOpen}>
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
                <FormControl isInvalid={!!errors.city}>
                  <FormLabel>City</FormLabel>
                  <Input onChange={handleChange("city")} />
                  <FormErrorMessage>{errors.city}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.district}>
                  <FormLabel>District</FormLabel>
                  <Input onChange={handleChange("district")} />
                  <FormErrorMessage>{errors.district}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.street}>
                  <FormLabel>Street</FormLabel>
                  <Input onChange={handleChange("street")} />
                  <FormErrorMessage>{errors.street}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.location}>
                  <FormLabel>Location</FormLabel>
                  <Input onChange={handleChange("location")} />
                  <FormErrorMessage>{errors.location}</FormErrorMessage>
                </FormControl>

                <Button type="submit">Create</Button>
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
