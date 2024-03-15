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
  FormErrorMessage,
} from "@chakra-ui/react";
import { useState } from "react";
import { userAxios } from "../../utils/base-axios";

// eslint-disable-next-line react/prop-types
const AddUser = ({ afterSave }) => {
  const { isOpen, onOpen, onClose } = useDisclosure({
    onClose: () => setErrors({}),
  });
  const [values, setValues] = useState({});
  const [errors, setErrors] = useState({});

  const handleSubmit = async (e) => {
    e.preventDefault();
    await userAxios
      .post("", values)
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
      <Button onClick={onOpen}>Create user</Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>User information</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <form onSubmit={handleSubmit}>
              <Stack>
                <FormControl isInvalid={!!errors.name}>
                  <FormLabel>Name</FormLabel>
                  <Input onChange={handleChange("name")} />
                  <FormErrorMessage>{!!errors.name}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.surname}>
                  <FormLabel>Surname</FormLabel>
                  <Input onChange={handleChange("surname")} />
                  <FormErrorMessage>{!!errors.surname}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.email}>
                  <FormLabel>Email</FormLabel>
                  <Input onChange={handleChange("email")} />
                  <FormErrorMessage>{!!errors.email}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.birthDate}>
                  <FormLabel>Birth date</FormLabel>
                  <Input onChange={handleChange("birthDate")} />
                  <FormErrorMessage>{!!errors.birthDate}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.gender}>
                  <FormLabel>Gender</FormLabel>
                  <Select
                    placeholder="Select option"
                    onChange={handleChange("gender")}
                  >
                    <option value="MALE">Male</option>
                    <option value="FEMALE">Female</option>
                  </Select>
                  <FormErrorMessage>{!!errors.gender}</FormErrorMessage>
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

export default AddUser;
