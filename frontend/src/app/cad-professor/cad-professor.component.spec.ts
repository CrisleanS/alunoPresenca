import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadProfessorComponent } from './cad-professor.component';

describe('CadProfessorComponent', () => {
  let component: CadProfessorComponent;
  let fixture: ComponentFixture<CadProfessorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CadProfessorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CadProfessorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
