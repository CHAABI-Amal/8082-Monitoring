import {Component, OnInit, ViewChild} from '@angular/core';
import {Machine, TypeMachine} from "../model/composants.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MachinesService} from "../services/machines.service";
import {MatTableDataSource} from "@angular/material/table";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-manage-type-machine',
  templateUrl: './manage-type-machine.component.html',
  styleUrl: './manage-type-machine.component.css'
})
export class ManageTypeMachineComponent implements OnInit {
  public typeMachines: TypeMachine[] = [];
  public TypeMachinesdataSource = new MatTableDataSource<TypeMachine>();
  public displayedColumns = [ 'code','name','actions'];
  public editForm: FormGroup;
  protected showNewTypeMachineForm: boolean=false;
  public selectedTypeMachine: TypeMachine | null = null;
  public newTypeMachineForm: FormGroup;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private typeMachinesService: MachinesService,
              private snackBar: MatSnackBar) {
    this.editForm = this.fb.group({
      name: [''],
      code: ['']
    });

    this.newTypeMachineForm = this.fb.group({
      name: [''],
      code: ['']
    });
  }

  ngOnInit(): void {
    this.loadTypeMachines();
  }

  loadTypeMachines() {
    this.typeMachinesService.getTypeMachines().subscribe({
      next: data => {
        this.typeMachines = data;
        this.TypeMachinesdataSource.data = this.typeMachines;

        this.TypeMachinesdataSource.paginator = this.paginator;
        this.TypeMachinesdataSource.sort = this.sort;
      },
      error: err => {
        console.log(err);
      }
    });
  }


  openEditForm(typeMachine: TypeMachine) {
    this.selectedTypeMachine = typeMachine;
    this.editForm.patchValue(typeMachine);
  }

  cancelEdit() {
    this.selectedTypeMachine = null;
    this.editForm.reset();
  }

  onSubmit() {
    if (this.selectedTypeMachine) {
      const updatedTypeMachine = {...this.selectedTypeMachine, ...this.editForm.value};
      this.typeMachinesService. partialUpdateTypeMachine(updatedTypeMachine.machinetypeId,updatedTypeMachine).subscribe({
        next: () => {
          // Success notification
          this.snackBar.open('Type Machine updated successfully', 'Close', {
            duration: 3000,
          });
          this.selectedTypeMachine = null;
          this.loadTypeMachines();
        },
        error: err => {
          console.log(err);
          // Error notification
          this.snackBar.open('Failed to update Type Machine', 'Close', {
            duration: 3000,
          });
        }
      });
    }
  }



  //******************************************************


  newTypeMachine() {
    // Définir la variable de statut pour afficher le formulaire
    this.selectedTypeMachine = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewTypeMachineForm = true; // Afficher le formulaire de création de nouvelle machine
  }
  cancelCreat() {
    this.selectedTypeMachine = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewTypeMachineForm = false;
  }
  onNewTypeMachineSubmit() {
    if (this.newTypeMachineForm.valid) { // Vérifiez si le formulaire est valide
      const newTypeMachineData = this.newTypeMachineForm.value;
      this.http.post('http://localhost:8081/amal/typemachines', newTypeMachineData)
        .subscribe({
          next: (response: any) => {
            // Ajoutez la nouvelle machine à la liste existante
            this.typeMachines.push(response);
            this.TypeMachinesdataSource.data = this.typeMachines;
            this.newTypeMachineForm.reset(); // Réinitialisez le formulaire après l'ajout
            this.snackBar.open('Type Machine ajoutée avec succès', 'Fermer', {
              duration: 3000,
            });
            // Réinitialiser le statut pour masquer le formulaire
            this.showNewTypeMachineForm = false;
          },
          error: err => {
            console.log(err);
            this.snackBar.open('Échec de l\'ajout de la Type machine', 'Fermer', {
              duration: 3000,
            });
          }
        });
    }
  }



  onCancelCreate() {
    this.selectedTypeMachine = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewTypeMachineForm = false;
  }


  deleteTypeMachine(typemachine: TypeMachine) {
      if (confirm(`Are you sure you want to delete the machine with Code ${typemachine.code}?`)) {
        this.typeMachinesService.deleteTypeMachine(typemachine.machinetypeId).subscribe({
          next: () => {
            this.typeMachines = this.typeMachines.filter(m => m.machinetypeId !== typemachine.machinetypeId);
            this.TypeMachinesdataSource.data = this.typeMachines;
            this.snackBar.open('type Machine supprimée avec succès', 'Fermer', {
              duration: 3000,
            });
          },
          error: err => {
            console.log(err);
            this.snackBar.open('Échec de la suppression de la type machine', 'Fermer', {
              duration: 3000,
            });
          }
        });
      }
    }
}
