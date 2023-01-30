import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRestaurant, NewRestaurant } from '../restaurant.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRestaurant for edit and NewRestaurantFormGroupInput for create.
 */
type RestaurantFormGroupInput = IRestaurant | PartialWithRequiredKeyOf<NewRestaurant>;

type RestaurantFormDefaults = Pick<NewRestaurant, 'id'>;

type RestaurantFormGroupContent = {
  id: FormControl<IRestaurant['id'] | NewRestaurant['id']>;
  name: FormControl<IRestaurant['name']>;
  phone: FormControl<IRestaurant['phone']>;
  address: FormControl<IRestaurant['address']>;
  photo: FormControl<IRestaurant['photo']>;
  photoContentType: FormControl<IRestaurant['photoContentType']>;
  longitude: FormControl<IRestaurant['longitude']>;
  latitude: FormControl<IRestaurant['latitude']>;
};

export type RestaurantFormGroup = FormGroup<RestaurantFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RestaurantFormService {
  createRestaurantFormGroup(restaurant: RestaurantFormGroupInput = { id: null }): RestaurantFormGroup {
    const restaurantRawValue = {
      ...this.getFormDefaults(),
      ...restaurant,
    };
    return new FormGroup<RestaurantFormGroupContent>({
      id: new FormControl(
        { value: restaurantRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(restaurantRawValue.name, {
        validators: [Validators.required],
      }),
      phone: new FormControl(restaurantRawValue.phone, {
        validators: [Validators.required],
      }),
      address: new FormControl(restaurantRawValue.address, {
        validators: [Validators.required],
      }),
      photo: new FormControl(restaurantRawValue.photo, {
        validators: [Validators.required],
      }),
      photoContentType: new FormControl(restaurantRawValue.photoContentType),
      longitude: new FormControl(restaurantRawValue.longitude),
      latitude: new FormControl(restaurantRawValue.latitude),
    });
  }

  getRestaurant(form: RestaurantFormGroup): IRestaurant | NewRestaurant {
    return form.getRawValue() as IRestaurant | NewRestaurant;
  }

  resetForm(form: RestaurantFormGroup, restaurant: RestaurantFormGroupInput): void {
    const restaurantRawValue = { ...this.getFormDefaults(), ...restaurant };
    form.reset(
      {
        ...restaurantRawValue,
        id: { value: restaurantRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RestaurantFormDefaults {
    return {
      id: null,
    };
  }
}
