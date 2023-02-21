import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlates, NewPlates } from '../plates.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlates for edit and NewPlatesFormGroupInput for create.
 */
type PlatesFormGroupInput = IPlates | PartialWithRequiredKeyOf<NewPlates>;

type PlatesFormDefaults = Pick<NewPlates, 'id' | 'favorite'>;

type PlatesFormGroupContent = {
  id: FormControl<IPlates['id'] | NewPlates['id']>;
  name: FormControl<IPlates['name']>;
  price: FormControl<IPlates['price']>;
  photo: FormControl<IPlates['photo']>;
  photoContentType: FormControl<IPlates['photoContentType']>;
  origin: FormControl<IPlates['origin']>;
  stars: FormControl<IPlates['stars']>;
  favorite: FormControl<IPlates['favorite']>;
  cookTime: FormControl<IPlates['cookTime']>;
  idrestaurant: FormControl<IPlates['idrestaurant']>;
  restaurant: FormControl<IPlates['restaurant']>;
};

export type PlatesFormGroup = FormGroup<PlatesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlatesFormService {
  createPlatesFormGroup(plates: PlatesFormGroupInput = { id: null }): PlatesFormGroup {
    const platesRawValue = {
      ...this.getFormDefaults(),
      ...plates,
    };
    return new FormGroup<PlatesFormGroupContent>({
      id: new FormControl(
        { value: platesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(platesRawValue.name, {
        validators: [Validators.required],
      }),
      price: new FormControl(platesRawValue.price, {
        validators: [Validators.required],
      }),
      photo: new FormControl(platesRawValue.photo, {
        validators: [Validators.required],
      }),
      photoContentType: new FormControl(platesRawValue.photoContentType),
      origin: new FormControl(platesRawValue.origin),
      stars: new FormControl(platesRawValue.stars, {
        validators: [Validators.max(5)],
      }),
      favorite: new FormControl(platesRawValue.favorite),
      cookTime: new FormControl(platesRawValue.cookTime),
      idrestaurant: new FormControl(platesRawValue.idrestaurant),
      restaurant: new FormControl(platesRawValue.restaurant, {
        validators: [Validators.required],
      }),
    });
  }

  getPlates(form: PlatesFormGroup): IPlates | NewPlates {
    return form.getRawValue() as IPlates | NewPlates;
  }

  resetForm(form: PlatesFormGroup, plates: PlatesFormGroupInput): void {
    const platesRawValue = { ...this.getFormDefaults(), ...plates };
    form.reset(
      {
        ...platesRawValue,
        id: { value: platesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PlatesFormDefaults {
    return {
      id: null,
      favorite: false,
    };
  }
}
