/* eslint-disable react/prop-types */
import { useCallback, useEffect, useState } from 'react';
import { reviewAxios } from '../../utils/base-axios';
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Button,
  Flex,
  Box,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  useDisclosure,
} from '@chakra-ui/react';
import { FaStar, FaRegStar } from 'react-icons/fa';
import UpdateReview from './UpdateReview';

const RestaurantReviews = ({ restaurant }) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [restaurantReviews, setRestaurantReviews] = useState([]);

  const fetchRestaurantReviews = useCallback(() => {
    reviewAxios.get(`/with-restaurantId/${restaurant.id}`).then((res) => {
      setRestaurantReviews(res.data.data);
    });
  }, [restaurant.id]);

  useEffect(() => {
    fetchRestaurantReviews();
  }, [fetchRestaurantReviews]);

  return (
    <>
      <Button size={'sm'} variant={'outline'} onClick={onOpen}>
        Get restaurant reviews
      </Button>

      <Modal size={'5xl'} isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>RestaurantReviews</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <TableContainer>
              <Table>
                <Thead>
                  <Tr>
                    <Th>User id</Th>
                    <Th>Comment</Th>
                    <Th>Rate</Th>
                    <Th>Action</Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {restaurantReviews.map((review, index) => {
                    return (
                      <Tr key={index}>
                        <Td>{review.userId}</Td>
                        <Td>{review.comment}</Td>
                        <Td>
                          <Flex>
                            {new Array(5).fill(null).map((_, index) => {
                              return (
                                <Box key={index} color={'gold'}>
                                  {index < review.rate ? (
                                    <FaStar />
                                  ) : (
                                    <FaRegStar />
                                  )}
                                </Box>
                              );
                            })}
                          </Flex>
                        </Td>
                        <Td>
                          <UpdateReview
                            afterSave={fetchRestaurantReviews}
                            review={review}
                          />
                        </Td>
                      </Tr>
                    );
                  })}
                </Tbody>
              </Table>
            </TableContainer>
          </ModalBody>

          <ModalFooter></ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default RestaurantReviews;
