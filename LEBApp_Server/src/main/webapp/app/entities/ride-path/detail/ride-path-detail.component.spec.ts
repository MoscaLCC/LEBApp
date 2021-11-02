import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RidePathDetailComponent } from './ride-path-detail.component';

describe('RidePath Management Detail Component', () => {
  let comp: RidePathDetailComponent;
  let fixture: ComponentFixture<RidePathDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RidePathDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ridePath: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RidePathDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RidePathDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ridePath on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ridePath).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
