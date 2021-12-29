import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequest } from '../request.model';

@Component({
  selector: 'jhi-request-detail',
  templateUrl: './request-detail.component.html',
})
export class RequestDetailComponent implements OnInit {
  request: IRequest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ request }) => {
      request.expirationDate = request.expirationDate.split("T")[0];
      request.initDate = request.initDate.split("T")[0];
      this.request = request;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
