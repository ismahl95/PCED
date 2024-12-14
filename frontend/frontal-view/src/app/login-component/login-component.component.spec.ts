import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponentComponent } from './login-component.component';
import { FormsModule } from '@angular/forms';

describe('LoginComponentComponent', () => {
  let component: LoginComponentComponent;
  let fixture: ComponentFixture<LoginComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: 
      [
        LoginComponentComponent,
        FormsModule
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should return the correct values for username and password', () => {
    // Obtener los inputs de usuario y contraseña
    const usernameInput = fixture.nativeElement.querySelector('input[name="username"]');
    const passwordInput = fixture.nativeElement.querySelector('input[name="password"]');

    // Simular la entrada de valores
    usernameInput.value = 'testUser';
    passwordInput.value = 'testPassword';
    
    // Trigger event to update ngModel
    usernameInput.dispatchEvent(new Event('input'));
    passwordInput.dispatchEvent(new Event('input'));

    // Detectar cambios
    fixture.detectChanges();

    // Comprobar que los valores del componente se han actualizado correctamente
    expect(component.username).toBe('testUser');
    expect(component.password).toBe('testPassword');
  });

  it(`should have the 'login 'title`, () =>
  {
    const fixture = TestBed.createComponent(LoginComponentComponent);
    const app= fixture.componentInstance;
    expect(app.title).toEqual('login-view');
  });
});
