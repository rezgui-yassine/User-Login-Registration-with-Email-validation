import {Component, EventEmitter, Output} from '@angular/core';
import {navbarData} from "./nav-data";
import {SideNavToggle} from "./sideNavTogle";

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent {
  @Output() onToggleSideNav:EventEmitter<SideNavToggle>=new EventEmitter()
  collapsed = false;
  screenWidth = 0;
  navData = navbarData;



  toggleCollapse() {
    this.collapsed = !this.collapsed;
  //   Todo: Emit an event to the parent component
    this.onToggleSideNav.emit({collapsed: this.collapsed, screenWidth: this.screenWidth});

  }

  closeSidenav() {
    this.collapsed = false;
    this.onToggleSideNav.emit({collapsed: this.collapsed, screenWidth: this.screenWidth});
  }
}
