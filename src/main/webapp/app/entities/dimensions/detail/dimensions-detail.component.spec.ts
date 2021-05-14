import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DimensionsDetailComponent } from './dimensions-detail.component';

describe('Component Tests', () => {
  describe('Dimensions Management Detail Component', () => {
    let comp: DimensionsDetailComponent;
    let fixture: ComponentFixture<DimensionsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DimensionsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dimensions: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DimensionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DimensionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dimensions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dimensions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
