import { IRestaurant, NewRestaurant } from './restaurant.model';

export const sampleWithRequiredData: IRestaurant = {
  id: '9a4f2ee4-d45a-4122-b55a-c0059e33895f',
  name: 'Locks Toys',
  phone: '302.645.1432 x70308',
  address: 'Road',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
};

export const sampleWithPartialData: IRestaurant = {
  id: 'f0cd5124-62b0-4233-b4c6-bcd02b909d19',
  name: 'Optimization revolutionary syndicate',
  phone: '(706) 805-6306 x511',
  address: 'encompassing Concrete Developer',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
};

export const sampleWithFullData: IRestaurant = {
  id: '05c0c20b-c7ba-4fce-bf3c-afcd4271fcda',
  name: 'Small JSON',
  phone: '1-274-511-6006 x66931',
  address: 'Personal Rubber 1080p',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  longitude: 33644,
  latitude: 49499,
};

export const sampleWithNewData: NewRestaurant = {
  name: 'synergies copy',
  phone: '664-426-4851',
  address: 'Granite',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
