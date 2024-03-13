/* eslint-disable react/prop-types */
import { useCallback, useEffect, useState } from 'react';
import { recommendationAxios } from '../../utils/base-axios';
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

const Recommendations = ({ user }) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [recommendations, setRecommendations] = useState([]);

  const fetchRecommendations = useCallback(() => {
    recommendationAxios.get(`/${user.id}`).then((res) => {
      setRecommendations(res.data.data);
    });
  }, [user.id]);

  useEffect(() => {
    fetchRecommendations();
  }, [fetchRecommendations]);

  return (
    <>
      <Button size={'sm'} variant={'outline'} onClick={onOpen}>
        Get recommendations
      </Button>

      <Modal size={'5xl'} isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Recommendations</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <TableContainer>
              <Table>
                <Thead>
                  <Tr>
                    <Th>Id</Th>
                    <Th>Name</Th>
                    <Th>Location</Th>
                    <Th>Average score</Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {recommendations.map((recommendation) => {
                    const [lat, lng] = recommendation.location.split(',');
                    const location = `(${lat} , ${lng})`;

                    return (
                      <Tr key={recommendation.id}>
                        <Td>{recommendation.id.substring(0, 6)}</Td>
                        <Td>{recommendation.name}</Td>
                        <Td>{location}</Td>
                        <Td>
                          <Flex>
                            {new Array(5).fill(null).map((_, index) => {
                              return (
                                <Box key={index} color={'gold'}>
                                  {index < recommendation.averageScore ? (
                                    <FaStar />
                                  ) : (
                                    <FaRegStar />
                                  )}
                                </Box>
                              );
                            })}
                          </Flex>
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

export default Recommendations;
