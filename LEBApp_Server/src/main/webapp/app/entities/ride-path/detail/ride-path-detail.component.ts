import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRidePath } from '../ride-path.model';

@Component({
  selector: 'jhi-ride-path-detail',
  templateUrl: './ride-path-detail.component.html',
})
export class RidePathDetailComponent implements OnInit {
  ridePath: IRidePath | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ridePath }) => {
      this.ridePath = ridePath;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
