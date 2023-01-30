import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PlatesService } from '../service/plates.service';

import { PlatesComponent } from './plates.component';

describe('Plates Management Component', () => {
  let comp: PlatesComponent;
  let fixture: ComponentFixture<PlatesComponent>;
  let service: PlatesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'plates', component: PlatesComponent }]), HttpClientTestingModule],
      declarations: [PlatesComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PlatesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlatesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PlatesService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.plates?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to platesService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getPlatesIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPlatesIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
