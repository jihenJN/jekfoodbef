import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlatesFormService } from './plates-form.service';
import { PlatesService } from '../service/plates.service';
import { IPlates } from '../plates.model';
import { IRestaurant } from 'app/entities/restaurant/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/service/restaurant.service';

import { PlatesUpdateComponent } from './plates-update.component';

describe('Plates Management Update Component', () => {
  let comp: PlatesUpdateComponent;
  let fixture: ComponentFixture<PlatesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let platesFormService: PlatesFormService;
  let platesService: PlatesService;
  let restaurantService: RestaurantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlatesUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PlatesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlatesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    platesFormService = TestBed.inject(PlatesFormService);
    platesService = TestBed.inject(PlatesService);
    restaurantService = TestBed.inject(RestaurantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Restaurant query and add missing value', () => {
      const plates: IPlates = { id: 'CBA' };
      const restaurant: IRestaurant = { id: 'b4fce2bb-ad58-4a88-810d-7dd47951d458' };
      plates.restaurant = restaurant;

      const restaurantCollection: IRestaurant[] = [{ id: '64012863-36b3-4de0-9fbe-1725855ca917' }];
      jest.spyOn(restaurantService, 'query').mockReturnValue(of(new HttpResponse({ body: restaurantCollection })));
      const additionalRestaurants = [restaurant];
      const expectedCollection: IRestaurant[] = [...additionalRestaurants, ...restaurantCollection];
      jest.spyOn(restaurantService, 'addRestaurantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plates });
      comp.ngOnInit();

      expect(restaurantService.query).toHaveBeenCalled();
      expect(restaurantService.addRestaurantToCollectionIfMissing).toHaveBeenCalledWith(
        restaurantCollection,
        ...additionalRestaurants.map(expect.objectContaining)
      );
      expect(comp.restaurantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const plates: IPlates = { id: 'CBA' };
      const restaurant: IRestaurant = { id: 'd3b9c535-31df-40de-92fd-beae2bae6b5a' };
      plates.restaurant = restaurant;

      activatedRoute.data = of({ plates });
      comp.ngOnInit();

      expect(comp.restaurantsSharedCollection).toContain(restaurant);
      expect(comp.plates).toEqual(plates);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlates>>();
      const plates = { id: 'ABC' };
      jest.spyOn(platesFormService, 'getPlates').mockReturnValue(plates);
      jest.spyOn(platesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plates });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plates }));
      saveSubject.complete();

      // THEN
      expect(platesFormService.getPlates).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(platesService.update).toHaveBeenCalledWith(expect.objectContaining(plates));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlates>>();
      const plates = { id: 'ABC' };
      jest.spyOn(platesFormService, 'getPlates').mockReturnValue({ id: null });
      jest.spyOn(platesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plates: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plates }));
      saveSubject.complete();

      // THEN
      expect(platesFormService.getPlates).toHaveBeenCalled();
      expect(platesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlates>>();
      const plates = { id: 'ABC' };
      jest.spyOn(platesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plates });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(platesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRestaurant', () => {
      it('Should forward to restaurantService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(restaurantService, 'compareRestaurant');
        comp.compareRestaurant(entity, entity2);
        expect(restaurantService.compareRestaurant).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
