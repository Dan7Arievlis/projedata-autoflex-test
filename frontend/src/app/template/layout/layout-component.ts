import { Component } from '@angular/core';
import { AuthService } from '../../auth/auth-service';
import { LayoutProps } from '../layout-props';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, map } from 'rxjs';

@Component({
  selector: 'app-layout-component',
  standalone: false,
  templateUrl: './layout.html',
  styleUrl: './layout.css',
})
export class LayoutComponent {
  props: LayoutProps = { title: '', subtitle: '', color: '' }
  
  constructor(
      private router: Router,
      private activatedRoute: ActivatedRoute,
      private auth: AuthService
  ) {}

  getLayoutProps(): LayoutProps {
    let childRoute = this.activatedRoute.firstChild;
    while(childRoute?.firstChild){
      childRoute = childRoute.firstChild;
    }

    return childRoute?.snapshot.data as LayoutProps;
  }
  
  logout() {
    this.auth.logout();
  }

  ngOnInit() {
    this.router.events.pipe(
      filter(() => this.activatedRoute.firstChild !== null),
      map(() => this.getLayoutProps())
    ).subscribe((props: LayoutProps) => this.props = props);
    this.props = this.getLayoutProps();
  }
}
