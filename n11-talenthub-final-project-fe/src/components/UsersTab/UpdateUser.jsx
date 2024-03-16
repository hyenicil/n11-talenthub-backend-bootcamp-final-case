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
  FormErrorMessage,
} from "@chakra-ui/react";
import { useState } from "react";
import { userAxios } from "../../utils/base-axios";

const UpdateUser = ({ afterSave, user }) => {
  const { isOpen, onOpen, onClose } = useDisclosure({
    onClose: () => setErrors({}),
  });
  const [values, setValues] = useState(user);
  const [errors, setErrors] = useState({});

  const handleSubmit = async (e) => {
    e.preventDefault();
    await userAxios
      .patch(`/${user.id}`, values)
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
      <Button size={"sm"} onClick={onOpen}>
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
                <FormControl isInvalid={!!errors.name}>
                  <FormLabel>Name</FormLabel>
                  <Input
                    defaultValue={user.name}
                    onChange={handleChange("name")}
                  />
                  <FormErrorMessage>{errors.name}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.surname}>
                  <FormLabel>Surname</FormLabel>
                  <Input
                    defaultValue={user.surname}
                    onChange={handleChange("surname")}
                  />
                  <FormErrorMessage>{errors.surname}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.email}>
                  <FormLabel>Email</FormLabel>
                  <Input
                    defaultValue={user.email}
                    onChange={handleChange("email")}
                  />
                  <FormErrorMessage>{errors.email}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.birthDate}>
                  <FormLabel>Birth date</FormLabel>
                  <Input
                    defaultValue={user.birthDate ? user.birthDate : ""}
                    onChange={handleChange("birthDate")}
                  />
                  <FormErrorMessage>{errors.birthDate}</FormErrorMessage>
                </FormControl>
                <FormControl>
                  <FormLabel>Gender</FormLabel>
                  <Select
                    defaultValue={user.gender}
                    placeholder="Select option"
                    onChange={handleChange("gender")}
                  >
                    <option value="MALE">Male</option>
                    <option value="FEMALE">Female</option>
                  </Select>
                  <FormErrorMessage>{errors.gender}</FormErrorMessage>
                </FormControl>
                <Button type="submit">Update</Button>
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
