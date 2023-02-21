import { IPlates, NewPlates } from './plates.model';

export const sampleWithRequiredData: IPlates = {
  id: 'abc64f4f-f41e-4fe2-b2ac-9ce7f01d05a5',
  name: 'Licensed invoice Health',
  price: 19594,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
};

export const sampleWithPartialData: IPlates = {
  id: '4b15938f-1286-4f6d-a90c-238b63b23dcd',
  name: 'Passage Balboa',
  price: 99664,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  origin: 'Books Gabon Kids',
  stars: 0,
  favorite: false,
};

export const sampleWithFullData: IPlates = {
  id: '6ce01c30-8aa7-41f8-8041-475955e1b402',
  name: 'Avon global',
  price: 82748,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  origin: 'revolutionize Books SCSI',
  stars: 4,
  favorite: false,
  cookTime: 75848,
  idrestaurant: 'project',
};

export const sampleWithNewData: NewPlates = {
  name: 'eco-centric payment fuchsia',
  price: 38157,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
