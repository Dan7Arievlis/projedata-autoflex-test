import { Directive, TemplateRef, ViewContainerRef } from "@angular/core";
import { AuthService } from "./auth-service";

@Directive({
  selector: '[appIfAdmin]'
})
export class IfAdminDirective {

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private auth: AuthService
  ) {
    if (this.auth.isAdmin()) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    }
  }
}