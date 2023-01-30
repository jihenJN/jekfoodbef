import { IPlates, NewPlates } from './plates.model';

export const sampleWithRequiredData: IPlates = {
  id: 'abc64f4f-f41e-4fe2-b2ac-9ce7f01d05a5',
  name: 'Licensed invoice Health',
  price: 19594,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
};

export const sampleWithPartialData: IPlates = {
  id: '14b15938-f128-46f6-9290-c238b63b23dc',
  name: 'web-enabled Handcrafted Cambridgeshire',
  price: 1615,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  origin: 'firewall',
  stars: 4,
  favorite: false,
};

export const sampleWithFullData: IPlates = {
  id: '98246ce0-1c30-48aa-b1f8-4041475955e1',
  name: 'ability Borders data-warehouse',
  price: 86706,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  origin: 'AI auxiliary Switzerland',
  stars: 2,
  favorite: true,
  cookTime: 38689,
};

export const sampleWithNewData: NewPlates = {
  name: 'Gloves Up-sized definition',
  price: 58096,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
