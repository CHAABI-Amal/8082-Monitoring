import { Component } from '@angular/core';
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-admin-template',
  templateUrl: './admin-template.component.html',
  styleUrl: './admin-template.component.css'
})
export class AdminTemplateComponent {
  isSettingExpanded: boolean = false;
  constructor(public authService: AuthService) {
  }
  logout() {
    this.authService.logout();
  }
  private openSubMenus: string[] = [];

  toggleSubMenu(subMenuName: string): void {
    const index = this.openSubMenus.indexOf(subMenuName);
    if (index === -1) {
      this.openSubMenus.push(subMenuName);
    } else {
      this.openSubMenus.splice(index, 1);
    }
  }

  isSubMenuOpen(subMenuName: string): boolean {
    return this.openSubMenus.includes(subMenuName);
  }
}
