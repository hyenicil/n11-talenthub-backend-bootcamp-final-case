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

const UpdateAddress = ({ afterSave, address }) => {
  const { isOpen, onOpen, onClose } = useDisclosure({
    onClose: () => setErrors({}),
  });
  const [values, setValues] = useState(address);
  const [errors, setErrors] = useState({});

  const handleSubmit = async (e) => {
    e.preventDefault();
    await addressAxios
      .patch(`/${address.id}`, values)
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
          <ModalHeader>Address information</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <form onSubmit={handleSubmit}>
              <Stack>
                <FormControl isInvalid={!!errors.city}>
                  <FormLabel>City</FormLabel>
                  <Input
                    defaultValue={address.city}
                    onChange={handleChange("city")}
                  />
                  <FormErrorMessage>{errors.city}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.district}>
                  <FormLabel>District</FormLabel>
                  <Input
                    defaultValue={address.district}
                    onChange={handleChange("district")}
                  />
                  <FormErrorMessage>{errors.district}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.street}>
                  <FormLabel>Street</FormLabel>
                  <Input
                    defaultValue={address.street}
                    onChange={handleChange("street")}
                  />
                  <FormErrorMessage>{errors.street}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.location}>
                  <FormLabel>Location</FormLabel>
                  <Input
                    defaultValue={address.location}
                    onChange={handleChange("location")}
                  />
                  <FormErrorMessage>{errors.location}</FormErrorMessage>
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

export default UpdateAddress;
