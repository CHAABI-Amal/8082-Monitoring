import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { Machine, Composant } from '../model/composants.model';
import { MachinesService } from '../services/machines.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, AfterViewInit {
  machines: Machine[] = [];

  constructor(private machineService: MachinesService) {
    Chart.register(...registerables);
  }

  ngOnInit(): void {
    this.loadMachinesData();
  }

  ngAfterViewInit(): void {
    this.createCharts();
  }

  loadMachinesData(): void {
    this.machineService.getMachines().subscribe(machines => {
      this.machines = machines;
      // Une fois les données chargées, nous créons les graphiques
      this.createCharts();
    });
  }

  createCharts(): void {
    this.machines.forEach((machine, index) => {
      // Pour chaque machine, nous récupérons ses composants et créons le graphique
      this.machineService.getComposantsMonitoring(machine.machineId).subscribe(composants => {
        this.createChart(machine.code, composants, index);
      });
    });
  }

  createChart(machineCode: string, composants: Composant[], index: number): void {
    const canvasId = `circularDashboard-${index}`;
    const ctx = document.getElementById(canvasId) as HTMLCanvasElement;

    if (ctx) {
      new Chart(ctx.getContext('2d')!, {
        type: 'doughnut',
        data: {
          labels: composants.map(composant => composant.code),
          datasets: [{
            data: composants.map(composant => parseFloat(composant.value)), // Convertir en nombre si nécessaire
            backgroundColor: [
              'rgba(255, 99, 132, 0.7)',
              'rgba(54, 162, 235, 0.7)',
              'rgba(255, 206, 86, 0.7)',
              'rgba(75, 192, 192, 0.7)',
              // Ajoutez plus de couleurs si nécessaire
            ]
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              position: 'top',
            },
            title: {
              display: true,
              text: `Composants de la ${machineCode}`
            }
          }
        }
      });
    } else {
      console.error(`Canvas element with ID '${canvasId}' not found.`);
    }
  }
}
