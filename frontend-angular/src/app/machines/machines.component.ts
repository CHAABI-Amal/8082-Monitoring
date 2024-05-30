import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { HttpClient } from "@angular/common/http";
import { MatTableDataSource } from "@angular/material/table";
import {Machine, Modules, TypeMachine} from "../model/composants.model";
import { MachinesService } from "../services/machines.service";
import { Router } from "@angular/router";
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
  selector: 'app-machines',
  templateUrl: './machines.component.html',
  styleUrls: ['./machines.component.css']
})
export class MachinesComponent implements OnInit {
  public machines: Machine[] = [];
  public typeMachines: TypeMachine[] = [];
  public Modules:Modules[]=[];
  public MachinesdataSource = new MatTableDataSource<Machine>();
  public displayedColumns = ['code', 'name','currentStatus','type','module', 'isonline', 'description', 'ipAddress', 'composants', 'actions'];
  public editForm: FormGroup;
  public newMachineForm: FormGroup; // Ajout du formulaire pour la nouvelle machine
  public selectedMachine: Machine | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  protected showNewMachineForm: boolean=false;

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private machinesService: MachinesService,
              private router: Router,
              private snackBar: MatSnackBar) {
    this.editForm = this.fb.group({
      name: [''],
      code: [''],
      type: [''],
      module:[''],
      isonline: [false],
      description: [''],
      ipAddress: [''],
      currentStatus: ['']
    });

    // Initialisation du formulaire pour la nouvelle machine
    this.newMachineForm = this.fb.group({
      name: [''],
      code: [''],
      type: [''],
      module:[''],
      isonline: [false],
      description: [''],
      ipAddress: [''],
      currentStatus: ['']
    });
  }
//***********************************************************************


  ngOnInit(): void {
    this.loadMachines();
    this.loadTypeMachines();
    this.loadModules();
  }

  loadMachines() {
    this.machinesService.getMachines().subscribe({
      next: data => {
        this.machines = data;
        this.MachinesdataSource.data = this.machines;
        this.MachinesdataSource.paginator = this.paginator;
        this.MachinesdataSource.sort = this.sort;
      },
      error: err => {
        console.log(err);
      }
    });
  }

  loadTypeMachines() {
    this.machinesService.getTypeMachines().subscribe({
      next: data => {
        console.log('Type Machines:', data); // Ajoutez cette ligne pour déboguer
        this.typeMachines = data;
      },
      error: err => {
        console.log(err);
      }
    });
  }
  loadModules() {
    this.machinesService.getModules().subscribe({
      next: data => {
        console.log('Models Machines:', data); // Ajoutez cette ligne pour déboguer
        this.Modules = data;
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

  composantes(machine: Machine) {
    this.router.navigateByUrl(`/admin/machine-details/${machine.machineId}`);
  }

  openEditForm(machine: Machine) {
    this.selectedMachine = machine;
    this.editForm.patchValue(machine);
  }

  cancelEdit() {
    this.selectedMachine = null;
    this.editForm.reset();
  }

  onSubmit() {
    if (this.selectedMachine) {
      const updatedMachine = { ...this.selectedMachine, ...this.editForm.value };
      this.http.patch(`http://localhost:8081/amal/machines/${this.selectedMachine.machineId}`, updatedMachine)
        .subscribe({
          next: () => {
            const index = this.machines.findIndex(m => m.machineId === this.selectedMachine!.machineId);
            if (index !== -1) {
              this.machines[index] = updatedMachine;
              this.MachinesdataSource.data = this.machines;
            }
            this.selectedMachine = null;
            this.snackBar.open('Machine mise à jour avec succès', 'Fermer', {
              duration: 3000,
            });
          },
          error: err => {
            console.log(err);
            this.snackBar.open('Échec de la mise à jour de la machine', 'Fermer', {
              duration: 3000,
            });
          }
        });
    }
  }

  deleteComposant(machine: Machine) {
    if (confirm(`Are you sure you want to delete the machine with Code ${machine.code}?`)) {
      this.machinesService.deleteMachine(machine.machineId).subscribe({
        next: () => {
          this.machines = this.machines.filter(m => m.machineId !== machine.machineId);
          this.MachinesdataSource.data = this.machines;
          this.snackBar.open('Machine supprimée avec succès', 'Fermer', {
            duration: 3000,
          });
        },
        error: err => {
          console.log(err);
          this.snackBar.open('Échec de la suppression de la machine', 'Fermer', {
            duration: 3000,
          });
        }
      });
    }
  }

  filterMachines(event: Event) {
    let value = (event.target as HTMLInputElement).value;
    this.MachinesdataSource.filter = value;
  }
//**********************************************************

  newMachine() {
    // Définir la variable de statut pour afficher le formulaire
    this.selectedMachine = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewMachineForm = true; // Afficher le formulaire de création de nouvelle machine
  }
  cancelCreat() {
    this.selectedMachine = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewMachineForm = false;
  }
  onNewMachineSubmit() {
    if (this.newMachineForm.valid) { // Vérifiez si le formulaire est valide
      const newMachineData = this.newMachineForm.value;
      this.http.post('http://localhost:8081/amal/machines', newMachineData)
        .subscribe({
          next: (response: any) => {
            // Ajoutez la nouvelle machine à la liste existante
            this.machines.push(response);
            this.MachinesdataSource.data = this.machines;
            this.newMachineForm.reset(); // Réinitialisez le formulaire après l'ajout
            this.snackBar.open('Machine ajoutée avec succès', 'Fermer', {
              duration: 3000,
            });
            // Réinitialiser le statut pour masquer le formulaire
            this.showNewMachineForm = false;
          },
          error: err => {
            console.log(err);
            this.snackBar.open('Échec de l\'ajout de la machine', 'Fermer', {
              duration: 3000,
            });
          }
        });
    }
  }

  // Fonction pour annuler la création de nouvelle machine et réinitialiser le formulaire
  cancelNewMachine() {
    this.newMachineForm.reset();
  }
}
