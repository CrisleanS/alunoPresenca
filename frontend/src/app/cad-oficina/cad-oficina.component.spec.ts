import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadOficinaComponent } from './cad-oficina.component';

describe('CadOficinaComponent', () => {
  let component: CadOficinaComponent;
  let fixture: ComponentFixture<CadOficinaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CadOficinaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CadOficinaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
