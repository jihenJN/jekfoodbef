import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlates, NewPlates } from '../plates.model';

export type PartialUpdatePlates = Partial<IPlates> & Pick<IPlates, 'id'>;

export type EntityResponseType = HttpResponse<IPlates>;
export type EntityArrayResponseType = HttpResponse<IPlates[]>;

@Injectable({ providedIn: 'root' })
export class PlatesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(plates: NewPlates): Observable<EntityResponseType> {
    return this.http.post<IPlates>(this.resourceUrl, plates, { observe: 'response' });
  }

  update(plates: IPlates): Observable<EntityResponseType> {
    return this.http.put<IPlates>(`${this.resourceUrl}/${this.getPlatesIdentifier(plates)}`, plates, { observe: 'response' });
  }

  partialUpdate(plates: PartialUpdatePlates): Observable<EntityResponseType> {
    return this.http.patch<IPlates>(`${this.resourceUrl}/${this.getPlatesIdentifier(plates)}`, plates, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPlates>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlates[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlatesIdentifier(plates: Pick<IPlates, 'id'>): string {
    return plates.id;
  }

  comparePlates(o1: Pick<IPlates, 'id'> | null, o2: Pick<IPlates, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlatesIdentifier(o1) === this.getPlatesIdentifier(o2) : o1 === o2;
  }

  addPlatesToCollectionIfMissing<Type extends Pick<IPlates, 'id'>>(
    platesCollection: Type[],
    ...platesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const plates: Type[] = platesToCheck.filter(isPresent);
    if (plates.length > 0) {
      const platesCollectionIdentifiers = platesCollection.map(platesItem => this.getPlatesIdentifier(platesItem)!);
      const platesToAdd = plates.filter(platesItem => {
        const platesIdentifier = this.getPlatesIdentifier(platesItem);
        if (platesCollectionIdentifiers.includes(platesIdentifier)) {
          return false;
        }
        platesCollectionIdentifiers.push(platesIdentifier);
        return true;
      });
      return [...platesToAdd, ...platesCollection];
    }
    return platesCollection;
  }
}
