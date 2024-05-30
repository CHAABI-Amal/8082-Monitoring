import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {HttpClient} from "@angular/common/http";
import {ComposantsService} from "../services/composants.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Modules} from "../model/composants.model";
import {MachinesService} from "../services/machines.service";

@Component({
  selector: 'app-manage-modules',
  templateUrl: './manage-modules.component.html',
  styleUrl: './manage-modules.component.css'
})
export class ManageModulesComponent implements OnInit {
  public Modules: Modules[] = [];
  public ModulesdataSource = new MatTableDataSource<Modules>();
  public displayedColumns = [ 'name','description','actions'];
  public editForm: FormGroup;
  protected showNewModulesForm: boolean=false;
  public selectedModules: Modules | null = null;
  public newModulesForm: FormGroup;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private ModulesService: MachinesService,
              private snackBar: MatSnackBar) {
    this.editForm = this.fb.group({
      name: [''],
      description: ['']
    });

    this.newModulesForm = this.fb.group({
      name: [''],
      description: ['']
    });
  }

  ngOnInit(): void {
    this.loadStatus();
  }

  loadStatus() {
    this.ModulesService.getModules().subscribe({
      next: data => {
        this.Modules = data;
        this.ModulesdataSource.data = this.Modules;

        this.ModulesdataSource.paginator = this.paginator;
        this.ModulesdataSource.sort = this.sort;
      },
      error: err => {
        console.log(err);
      }
    });
  }


  openEditForm(Modules: Modules) {
    this.selectedModules = Modules;
    this.editForm.patchValue(Modules);
  }

  cancelEdit() {
    this.selectedModules = null;
    this.editForm.reset();
  }

  onSubmit() {
    if (this.selectedModules) {
      const updatedStatus = {...this.selectedModules, ...this.editForm.value};
      this.ModulesService. partialUpdateModules(updatedStatus.id,updatedStatus).subscribe({
        next: () => {
          // Success notification
          this.snackBar.open('Status updated successfully', 'Close', {
            duration: 3000,
          });
          this.selectedModules= null;
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
    this.selectedModules = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewModulesForm = true; // Afficher le formulaire de création de nouvelle machine
  }
  cancelCreat() {
    this.selectedModules = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewModulesForm = false;
  }
  onNewStatusSubmit() {
    if (this.newModulesForm.valid) { // Vérifiez si le formulaire est valide
      const newStatusData = this.newModulesForm.value;
      this.http.post('http://localhost:8082/amal/Status', newStatusData)
        .subscribe({
          next: (response: any) => {
            // Ajoutez la nouvelle machine à la liste existante
            this.Modules.push(response);
            this.ModulesdataSource.data = this.Modules;
            this.newModulesForm.reset(); // Réinitialisez le formulaire après l'ajout
            this.snackBar.open('Status ajoutée avec succès', 'Fermer', {
              duration: 3000,
            });
            // Réinitialiser le statut pour masquer le formulaire
            this.showNewModulesForm = false;
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
    this.selectedModules = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewModulesForm = false;
  }


  deleteModules(Status: Modules) {
    if (confirm(`Are you sure you want to delete the machine with Code ${Status.name}?`)) {
      this.ModulesService.deleteModules(Status.id).subscribe({
        next: () => {
          this.Modules= this.Modules.filter(m => m.id !== Status.id);
          this.ModulesdataSource.data = this.Modules;
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
