import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {Composant} from "../model/composants.model";
import {ComposantsService} from "../services/composants.service";

@Component({
  selector: 'app-historiques',
  templateUrl: './historiques.component.html',
  styleUrl: './historiques.component.css'
})
export class HistoriquesComponent implements OnInit{
  public historiques:any;
  public HistoriquesdataSource:any;
  public displayedColumns=['id','composantName','status','value','datetime']
  composantName: string = ''; // Initialisation de composantName


  @ViewChild(MatPaginator) paginator! :MatPaginator;
  @ViewChild(MatSort) sort! :MatSort;
  constructor(private http:HttpClient,
              private composantsService: ComposantsService
              ){}
  ngOnInit(): void {
    this.http.get("http://localhost:8082/amal/historiqueComposants")
      .subscribe({
        next: data => {
          this.historiques = data;
          this.HistoriquesdataSource = new MatTableDataSource(this.historiques);
          this.HistoriquesdataSource.paginator = this.paginator;
          this.HistoriquesdataSource.sort = this.sort;

          // Récupérer le nom du composant depuis le service ComposantsService
          if (this.historiques.length > 0) {
            const composantId = this.historiques[0].composantId; // Supposons que l'ID du composant est dans la première ligne
            this.composantsService.getComposantById(composantId).subscribe({
              next: (composant: Composant) => {
                this.composantName = composant.name;
              },
              error: err => {
                console.log(err);
              }
            });
          }
        },
        error: err => {
          console.log(err);
        }
      });
  }


  getIconForStatus(status: string): string {
    switch(status) {
      case 'WORKING':
        return 'check_circle';  // Par exemple, une icône de check pour WORKING
      case 'WARNING':
        return 'warning';  // Icône de warning pour WARNING
      case 'FAULTY':
        return 'error';  // Icône d'erreur pour FAULTY
      case 'UNKNOWN':
        return 'help';  // Icône de question pour UNKNOWN
      case 'DISCONNECTED':
        return 'link_off';  // Icône de déconnexion pour DISCONNECTED
      default:
        return 'help';  // Icône par défaut
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
        return 'icon-unknown';  // Classe par défaut
    }
  }

}
