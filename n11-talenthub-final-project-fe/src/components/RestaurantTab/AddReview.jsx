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
import { reviewAxios } from "../../utils/base-axios";

const AddReview = ({ restaurant }) => {
  const { isOpen, onOpen, onClose } = useDisclosure({
    onClose: () => {
      setErrors({}), setValues({});
    },
  });
  const [values, setValues] = useState({});
  const [errors, setErrors] = useState({});

  const handleSubmit = async (e) => {
    e.preventDefault();
    await reviewAxios
      .post("", { ...values, restaurantId: restaurant.id })
      .then(() => {
        onClose();
        afterSave();
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
                <FormControl isInvalid={!!errors.comment}>
                  <FormLabel>Comment</FormLabel>
                  <Input onChange={handleChange("comment")} />
                  <FormErrorMessage>{errors.comment}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.userId}>
                  <FormLabel>User id</FormLabel>
                  <Input onChange={handleChange("userId")} />
                  <FormErrorMessage>{errors.userId}</FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={!!errors.rate}>
                  <FormLabel>Rate</FormLabel>
                  <Select
                    placeholder="Select Option"
                    onChange={handleChange("rate")}
                  >
                    <option value={1}>1</option>
                    <option value={2}>2</option>
                    <option value={3}>3</option>
                    <option value={4}>4</option>
                    <option value={5}>5</option>
                  </Select>
                  <FormErrorMessage>{errors.rate}</FormErrorMessage>
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

export default AddReview;
