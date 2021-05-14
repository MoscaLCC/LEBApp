jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TransporterService } from '../service/transporter.service';

import { TransporterDeleteDialogComponent } from './transporter-delete-dialog.component';

describe('Component Tests', () => {
  describe('Transporter Management Delete Component', () => {
    let comp: TransporterDeleteDialogComponent;
    let fixture: ComponentFixture<TransporterDeleteDialogComponent>;
    let service: TransporterService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TransporterDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(TransporterDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransporterDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TransporterService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
