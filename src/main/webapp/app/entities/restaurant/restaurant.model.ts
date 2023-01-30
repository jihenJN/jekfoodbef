export interface IRestaurant {
  id: string;
  name?: string | null;
  phone?: string | null;
  address?: string | null;
  photo?: string | null;
  photoContentType?: string | null;
  longitude?: number | null;
  latitude?: number | null;
}

export type NewRestaurant = Omit<IRestaurant, 'id'> & { id: null };
