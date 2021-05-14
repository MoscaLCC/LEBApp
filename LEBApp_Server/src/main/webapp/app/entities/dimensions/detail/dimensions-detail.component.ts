import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDimensions } from '../dimensions.model';

@Component({
  selector: 'jhi-dimensions-detail',
  templateUrl: './dimensions-detail.component.html',
})
export class DimensionsDetailComponent implements OnInit {
  dimensions: IDimensions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dimensions }) => {
      this.dimensions = dimensions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
