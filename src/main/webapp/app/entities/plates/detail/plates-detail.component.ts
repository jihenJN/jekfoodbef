import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlates } from '../plates.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-plates-detail',
  templateUrl: './plates-detail.component.html',
})
export class PlatesDetailComponent implements OnInit {
  plates: IPlates | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plates }) => {
      this.plates = plates;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
