import {Component, OnInit, ViewChild} from '@angular/core';
import {Composant, Machine} from "../model/composants.model";
import {MatTableDataSource} from "@angular/material/table";
import {ActivatedRoute, Router} from "@angular/router";
import {MachinesService} from "../services/machines.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ComposantsService} from "../services/composants.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-machine-details',
  templateUrl: './machine-details.component.html',
  styleUrl: './machine-details.component.css'
})
export class MachineDetailsComponent implements OnInit{
  machineId! :string;
  composants!:Array<Composant>;
  machines!: Array<Machine>;
  public composantsDataSource!: MatTableDataSource<Composant>;
  public displayedColumns = [
    'code', 'name', 'machineName', 'lastStatus','currentStatus', 'lastStatusChangeTime', 'value', 'model', 'isDeleted',
    'composantCreatedDate', 'composantModifiedDate', 'historiques', 'actions'
  ];

  editForm: FormGroup;
  public selectedComposant: Composant | null = null;
  showNewComposantForm: boolean = false;
  newComposantForm!: FormGroup;

  @ViewChild(MatPaginator) paginator! :MatPaginator;
  @ViewChild(MatSort) sort! :MatSort;
  constructor(private activatedRoute:ActivatedRoute,
              private machinesService: MachinesService,
              private composantsService: ComposantsService,
              private router: Router,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder,
              private http: HttpClient) {
    this.editForm = this.formBuilder.group({
      name: ['', Validators.required],
      code: ['', Validators.required],
      lastStatus: ['', Validators.required],
      lastStatusChangeTime: ['', Validators.required],
      value: ['', Validators.required],
      model: ['', Validators.required],
      composantCreatedDate: ['', Validators.required],
      composantModifiedDate: ['', Validators.required],
      machineId: ['', Validators.required] // Ajout de machineId
    });
  }


  ngOnInit(){
    this.fetchData();
    this.machineId=this.activatedRoute.snapshot.params['machineId'];
    this.machinesService.getComposants(this.machineId).subscribe(
      {
        next:value => {
          this.composants=value;
          this.composantsDataSource=new MatTableDataSource<Composant>(this.composants);
          this.composantsDataSource.paginator=this.paginator;
          this.composantsDataSource.sort=this.sort;
        },
        error:err => {
          console.log(err);
        }
      }
    );
  }
  fetchData(): void {
    this.composantsService.getAllComposants().subscribe({
      next: (composants) => {
        this.composants = composants;
        this.composantsDataSource = new MatTableDataSource<Composant>(this.composants);
        this.composantsDataSource.paginator = this.paginator;
        this.composantsDataSource.sort = this.sort;
        this.initializeNewComposantForm();

        this.composantsService.getAllMachines().subscribe({
          next: (machines) => {
            this.machines = machines;
            this.mapMachineNames();
          },
          error: (err) => console.log(err)
        });
      },
      error: (err) => console.log(err)
    });
  }

  mapMachineNames(): void {
    const machineMap = new Map(this.machines.map(machine => [machine.machineId, machine.name]));
    this.composants.forEach(composant => {
      composant['machineName'] = machineMap.get(composant.machineId) || 'Unknown';
    });
    this.composantsDataSource.data = this.composants;
  }

  initializeNewComposantForm(): void {
    this.newComposantForm = this.formBuilder.group({
      name: ['', Validators.required],
      code: ['', Validators.required],
      lastStatus: ['', Validators.required],
      lastStatusChangeTime: ['', Validators.required],
      value: ['', Validators.required],
      model: ['', Validators.required],
      composantCreatedDate: ['', Validators.required],
      composantModifiedDate: ['', Validators.required],
      machineId: ['', Validators.required] // Ajout de machineId
    });
  }

  composantHistoriques(composant: Composant) {
    this.router.navigateByUrl(`/admin/composant-details/${composant.id}`);
  }

  filterComposants(event: Event) {
    let value = (event.target as HTMLInputElement).value;
    this.composantsDataSource.filter = value;
  }

  deleteComposant(composant: Composant) {
    if (confirm(`Are you sure you want to delete the component with Code ${composant.code}?`)) {
      this.composantsService.deleteComposant(composant.id).subscribe({
        next: () => {
          this.composants = this.composants.filter(c => c.id !== composant.id);
          this.composantsDataSource.data = this.composants;
          this.snackBar.open('Composant deleted successfully', 'Close', {
            duration: 3000,
          });
        },
        error: (err) => {
          console.log(err);
          this.snackBar.open('Failed to delete composant', 'Close', {
            duration: 3000,
          });
        }
      });
    }
  }

  getIconForStatus(status: string): string {
    switch(status) {
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
    switch(status) {
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

  onSubmit(): void {
    if (this.selectedComposant && this.editForm.valid) {
      const updatedComposant = { ...this.selectedComposant, ...this.editForm.value };
      this.http.patch(`http://localhost:8082/amal/composants/${this.selectedComposant.id}`, updatedComposant)
        .subscribe({
          next: () => {
            const index = this.composants.findIndex(m => m.id === this.selectedComposant!.id);
            if (index !== -1) {
              this.composants[index] = updatedComposant;
              this.composantsDataSource.data = this.composants;
            }
            this.selectedComposant = null;
            this.snackBar.open('Machine mise à jour avec succès', 'Fermer', {
              duration: 3000,
            });
          },
          error: err => {
            console.log(err);
            this.snackBar.open('Échec de la mise à jour de la Composant', 'Fermer', {
              duration: 3000,
            });
          }
        });
    }
  }


  // Fonction pour annuler l'édition
  cancelEdit() {
    this.selectedComposant = null;
    this.editForm.reset();
  }
  // Ajout de la fonction editComposant
  openEditForm(composant: Composant) {
    this.selectedComposant = composant;
    this.editForm.patchValue(composant);

  }
  //*********************************************************************************
  newComposant() {
    // Définir la variable de statut pour afficher le formulaire
    this.selectedComposant = null; // Assurez-vous que le formulaire d'édition est masqué
    this.showNewComposantForm = true; // Afficher le formulaire de création de nouvelle machine
  }
  onNewComposantSubmit(): void {
    if (this.newComposantForm.valid) {
      const newComposantData = this.newComposantForm.value;
      this.http.post('http://localhost:8082/amal/composants', newComposantData)
        .subscribe({
          next: (response: any) => {
            this.composants.push(response);
            this.composantsDataSource.data = this.composants;
            this.newComposantForm.reset();
            this.showNewComposantForm = false;
            this.snackBar.open('Composant ajouté avec succès', 'Fermer', {
              duration: 3000,
            });
          },
          error: err => {
            console.log(err);
            this.snackBar.open('Échec de l\'ajout du composant', 'Fermer', {
              duration: 3000,
            });
          }
        });
    }
  }


  cancelNewComposant(): void {
    // Réinitialiser le formulaire sans soumettre les données
    this.newComposantForm.reset();
    // Cacher le formulaire
    this.showNewComposantForm = false;
  }
  getMachineName(machineId: string): string {
    const machine = this.machines.find(m => m.machineId === machineId);
    return machine ? machine.name : '';
  }


}
