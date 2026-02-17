import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-oauth-callback-component',
  standalone: false,
  templateUrl: './oauth-callback-component.html',
  styleUrl: './oauth-callback-component.scss',
})
export class OauthCallbackComponent {
  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    const token = this.route.snapshot.queryParamMap.get('token');
    console.log('callback');
    
    if (token) {
      localStorage.setItem('token', token);
      this.router.navigate(['/']);
    } else {
      this.router.navigate(['/auth/login']);
    }
  }
}
