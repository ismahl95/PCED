import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './Services/auth.service';

@Component({
  selector: 'app-login-component',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login-component.component.html',
  styleUrl: './login-component.component.scss'
})
export class LoginComponentComponent {
  username: string = '';
  password: string = '';
  title = 'login-view'

  constructor(private router: Router, private authService: AuthService) {}

  onSubmit() {
    if (this.username && this.password) {
      this.authService.login(this.username, this.password).subscribe(
        (response: any) => {
          console.log('Token:', response.token);
          // Guarda el token en localStorage o un servicio compartido.
          localStorage.setItem('token', response.token);
          this.router.navigate(['/dashboard']); // Redirige tras el login.
        },
        (error: any) => {
          console.error('Login failed', error);
          alert('Login failed. Please check your credentials.');
        }
      );
    } else {
      alert('Please enter username and password!');
    }
  }
}
