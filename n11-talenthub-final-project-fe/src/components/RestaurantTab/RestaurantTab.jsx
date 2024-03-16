import { useCallback, useEffect, useState } from "react";
import { restaurantAxios } from "../../utils/base-axios";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Stack,
  Flex,
  Box,
  Text,
  Center,
} from "@chakra-ui/react";
import AddRestaurant from "./AddRestaurant";
import UpdateRestaurant from "./UpdateRestaurant";
import AddReview from "./AddReview";
import RestaurantReviews from "./RestaurantReviews";
import DeleteRestaurant from "./DeleteRestaurant";
import { FaStar, FaRegStar } from "react-icons/fa";

const RestaurantsTab = () => {
  const [restaurants, setRestaurants] = useState([]);

  const fetchRestaurants = useCallback(() => {
    restaurantAxios.get("").then((res) => {
      setRestaurants(res.data.data);
    });
  }, []);

  useEffect(() => {
    fetchRestaurants();
  }, [fetchRestaurants]);

  if (!restaurants.length)
    return (
      <Stack>
        <Flex justify={"start"}>
          <AddRestaurant afterSave={fetchRestaurants} />
        </Flex>
        <Text alignSelf={"Center"}>No Data</Text>
      </Stack>
    );

  return (
    <Stack>
      <Flex justify={"start"}>
        <AddRestaurant afterSave={fetchRestaurants} />
      </Flex>
      <TableContainer>
        <Table variant="striped" colorScheme="blackAlpha">
          <Thead>
            <Tr>
              <Th>Id</Th>
              <Th>Name</Th>
              <Th>Location</Th>
              <Th>Average score</Th>
              <Th>Actions</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {restaurants.map((restaurant) => {
              const [lat, lng] = restaurant.location.split(",");
              const location = `(${lat} , ${lng})`;

              return (
                <Tr key={restaurant.id}>
                  <Td>{restaurant.id.substring(0, 6)}</Td>
                  <Td>{restaurant.name}</Td>
                  <Td>{location}</Td>
                  <Td>
                    <Flex>
                      {new Array(5).fill(null).map((_, index) => {
                        return (
                          <Box key={index} color={"#ffd10d"}>
                            {index < restaurant.averageScore ? (
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
                    <Flex gap={4}>
                      <DeleteRestaurant
                        afterSave={fetchRestaurants}
                        restaurant={restaurant}
                      />
                      <UpdateRestaurant
                        afterSave={fetchRestaurants}
                        restaurant={restaurant}
                      />
                    </Flex>
                  </Td>
                  <Td>
                    <Flex gap={4}>
                      <AddReview restaurant={restaurant} />
                      <RestaurantReviews restaurant={restaurant} />
                    </Flex>
                  </Td>
                </Tr>
              );
            })}
          </Tbody>
        </Table>
      </TableContainer>
    </Stack>
  );
};

export default RestaurantsTab;
