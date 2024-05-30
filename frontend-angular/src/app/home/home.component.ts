import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { MachinesService } from "../services/machines.service";
import { MatTableDataSource } from "@angular/material/table";
import { Machine } from "../model/composants.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public MachinesdataSource = new MatTableDataSource<Machine>();

  constructor(private http: HttpClient,
              private machinesService: MachinesService,
              private router: Router) { }

  ngOnInit(): void {
    this.http.get<Machine[]>("http://localhost:8081/amal/machines")
      .subscribe({
        next: data => {
          this.MachinesdataSource.data = data;
        },
        error: err => {
          console.log(err);
        }
      });
  }

  getIconForStatus(status: string): string {
    switch (status) {
      case 'WORKING':
        return 'check_circle';
      case 'WARNING':
        return 'warning';
      case 'FAULTY':
        return 'error';
      case 'UNKNOWN':
        return 'help';
      case 'DISCONNECTED':
        return 'link_off';
      default:
        return 'help';
    }
  }

  getClassForStatus(status: string): string {
    switch (status) {
      case 'WORKING':
        return 'icon-working';
      case 'WARNING':
        return 'icon-warning';
      case 'FAULTY':
        return 'icon-faulty';
      case 'UNKNOWN':
        return 'icon-unknown';
      case 'DISCONNECTED':
        return 'icon-disconnected';
      default:
        return 'icon-unknown';
    }
  }

  viewDetails(machine: Machine): void {
    console.log('Voir détails pour', machine.name);
  }

  VoirDetails(machine: Machine) {

    this.router.navigateByUrl(`/admin/machines`);
  }

  Configurations(machine: Machine) {
    this.router.navigateByUrl(`/admin/machine-details/${machine.machineId}`);
  }
}
