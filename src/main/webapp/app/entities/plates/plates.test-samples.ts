import { IPlates, NewPlates } from './plates.model';

export const sampleWithRequiredData: IPlates = {
  id: 'abc64f4f-f41e-4fe2-b2ac-9ce7f01d05a5',
  name: 'Licensed invoice Health',
  price: 19594,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
};

export const sampleWithPartialData: IPlates = {
  id: 'b15938f1-286f-46d2-90c2-38b63b23dcd5',
  name: 'California Account',
  price: 91805,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  origin: 'Clothing',
  stars: 1,
  favorite: true,
};

export const sampleWithFullData: IPlates = {
  id: '098246ce-01c3-408a-a71f-84041475955e',
  name: 'card',
  price: 17847,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  origin: 'Avon global',
  stars: 4,
  favorite: true,
  cookTime: 38851,
  idrestaurant: 'auxiliary Switzerland Rest',
  photos: 'Gloves Up-sized definition',
};

export const sampleWithNewData: NewPlates = {
  name: 'Intelligent grey',
  price: 99159,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
