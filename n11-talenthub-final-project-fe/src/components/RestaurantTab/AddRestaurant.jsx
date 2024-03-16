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
import { restaurantAxios } from "../../utils/base-axios";

const AddRestaurant = ({ afterSave }) => {
  const { isOpen, onOpen, onClose } = useDisclosure({
    onClose: () => setErrors({}),
  });
  const [values, setValues] = useState({});
  const [errors, setErrors] = useState({});

  const handleSubmit = async (e) => {
    e.preventDefault();
    await restaurantAxios
      .post("", values)
      .then(() => {
        afterSave();
        onClose();

        setValues({});
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
      <Button onClick={onOpen}>Create restaurant</Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Restaurant information</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <form onSubmit={handleSubmit}>
              <Stack>
                <FormControl isInvalid={!!errors.name}>
                  <FormLabel>Name</FormLabel>
                  <Input onChange={handleChange("name")} />
                  <FormErrorMessage>{errors.name}</FormErrorMessage>
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

export default AddRestaurant;
