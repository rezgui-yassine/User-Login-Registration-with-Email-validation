import { Component } from '@angular/core';
import {SideNavToggle} from "../sidenav/sideNavTogle";

@Component({
  selector: 'app-main-dashboard',
  templateUrl: './main-dashboard.component.html',
  styleUrls: ['./main-dashboard.component.scss']
})
export class MainDashboardComponent {

  screenWidth = 0;
  isSideNavCollapsed = false;

  onToggleSideNav(data: SideNavToggle) {
    this.screenWidth = data.screenWidth;
    this.isSideNavCollapsed = data.collapsed;

  }
}
