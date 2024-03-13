import axios from 'axios';

export const userAxios = axios.create({
  baseURL: 'http://localhost:8060/api/v1/users',
});

export const addressAxios = axios.create({
  baseURL: 'http://localhost:8060/api/v1/addresses',
});

export const restaurantAxios = axios.create({
  baseURL: 'http://localhost:8060/api/v1/restaurants',
});

export const recommendationAxios = axios.create({
  baseURL: 'http://localhost:8060/api/v1/recommendation',
});

export const reviewAxios = axios.create({
  baseURL: 'http://localhost:8060/api/v1/reviews',
});
