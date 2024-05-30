import {Component, OnInit, ViewChild} from '@angular/core';
import {Status, TypeMachine} from "../model/composants.model";
import {MatTableDataSource} from "@angular/material/table";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {HttpClient} from "@angular/common/http";
import {MachinesService} from "../services/machines.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ComposantsService} from "../services/composants.service";

@Component({
  selector: 'app-manage-status',
  templateUrl: './manage-status.component.html',
  styleUrl: './manage-status.component.css'
})
export class ManageStatusComponent implements OnInit {
  public Status: Status[] = [];
  public StatusdataSource = new MatTableDataSource<Status>();
  public displayedColumns = [ 'value','description','actions'];
  public editForm: FormGroup;
  protected showNewStatusForm: boolean=false;
  public selectedStatus: Status | null = null;
  public newStatusForm: FormGroup;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private StatusService: ComposantsService,
              private snackBar: MatSnackBar) {
    this.editForm = this.fb.group({
      value: [''],
      description: ['']
    });

    this.newStatusForm = this.fb.group({
      value: [''],
      description: ['']
    });
  }

  ngOnInit(): void {
    this.loadStatus();
  }

  loadStatus() {
    this.StatusService.getStatus().subscribe({
      next: data => {
        this.Status = data;
        this.StatusdataSource.data = this.Status;

        this.StatusdataSource.paginator = this.paginator;
        this.StatusdataSource.sort = this.sort;
      },
      error: err => {
        console.log(err);
      }
    });
  }


  openEditForm(Status: Status) {
    this.selectedStatus = Status;
    this.editForm.patchValue(Status);
  }

  cancelEdit() {
    this.selectedStatus = null;
    this.editForm.reset();
  }

  onSubmit() {
    if (this.selectedStatus) {
      const updatedStatus = {...this.selectedStatus, ...this.editForm.value};
      this.StatusService. partialUpdateStatus(updatedStatus.id,updatedStatus).subscribe({
        next: () => {
          // Success notification
          this.snackBar.open('Status updated successfully', 'Close', {
            duration: 3000,
          });
          this.selectedStatus= null;
          this.loadStatus();
        },
        error: err => {
          console.log(err);
          // Error notification
          this.snackBar.open('Failed to update Status', 'Close', {
            duration: 3000,
          });
        }
      });
    }
  }



  //******************************************************


  newStatus() {
    // Définir la variable de statut pour afficher le formulaire
    this.selectedStatus = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewStatusForm = true; // Afficher le formulaire de création de nouvelle machine
  }
  cancelCreat() {
    this.selectedStatus = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewStatusForm = false;
  }
  onNewStatusSubmit() {
    if (this.newStatusForm.valid) { // Vérifiez si le formulaire est valide
      const newStatusData = this.newStatusForm.value;
      this.http.post('http://localhost:8082/amal/Status', newStatusData)
        .subscribe({
          next: (response: any) => {
            // Ajoutez la nouvelle machine à la liste existante
            this.Status.push(response);
            this.StatusdataSource.data = this.Status;
            this.newStatusForm.reset(); // Réinitialisez le formulaire après l'ajout
            this.snackBar.open('Status ajoutée avec succès', 'Fermer', {
              duration: 3000,
            });
            // Réinitialiser le statut pour masquer le formulaire
            this.showNewStatusForm = false;
          },
          error: err => {
            console.log(err);
            this.snackBar.open('Échec de l\'ajout de la Status', 'Fermer', {
              duration: 3000,
            });
          }
        });
    }
  }



  onCancelCreate() {
    this.selectedStatus = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewStatusForm = false;
  }


  deleteTypeMachine(Status: Status) {
    if (confirm(`Are you sure you want to delete the machine with Code ${Status.value}?`)) {
      this.StatusService.deleteStatus(Status.id).subscribe({
        next: () => {
          this.Status = this.Status.filter(m => m.id !== Status.id);
          this.StatusdataSource.data = this.Status;
          this.snackBar.open('Status supprimée avec succès', 'Fermer', {
            duration: 3000,
          });
        },
        error: err => {
          console.log(err);
          this.snackBar.open('Échec de la suppression de la Status', 'Fermer', {
            duration: 3000,
          });
        }
      });
    }
  }
}
