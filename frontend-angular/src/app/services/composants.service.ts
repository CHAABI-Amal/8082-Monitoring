import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {HistoriquesComponent} from "../historiques/historiques.component";
import {Composant, HistoriqueComposant, Machine, Status, TypeMachine} from "../model/composants.model";

@Injectable({
  providedIn: 'root'
})
export class ComposantsService {

  constructor(private http:HttpClient) { }

  public getAllHistoriques():Observable<Array<HistoriqueComposant>>{
    return this.http.get<Array<HistoriqueComposant>>(`${environment.backendHost}/amal/historiqueComposants`);
  }
  public getAllComposants():Observable<Array<Composant>>{
    return this.http.get<Array<Composant>>(`${environment.backendHost}/amal/composants`);
  }

  public getComposantHistoriques(composantId : string):Observable<Array<HistoriqueComposant>>{
    return this.http.get<Array<HistoriqueComposant>>(`${environment.backendHost}/amal/historiqueComposants/historique/${composantId}`);
  }

  deleteComposant(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.backendHost}/amal/composants/${id}`);
  }

  saveComposant(composant: FormData): Observable<Composant> {
    return this.http.post<Composant>(`${environment.backendHost}/amal/composants`, composant);
  }

  public getAllMachines(): Observable<Array<Machine>> {
    return this.http.get<Array<Machine>>(`${environment.backendHost2}/amal/machines`);//8081
  }

  public getComposantById(composantId: string): Observable<Composant> {
    return this.http.get<Composant>(`${environment.backendHost}/amal/composants/${composantId}`);//8082
  }

  deleteStatus(id: string) {
    return this.http.delete<void>(`${environment.backendHost}/amal/Status/${id}`);
  }

  partialUpdateStatus(id: string, Status: Partial<Status>): Observable<Status> {
    return this.http.patch<Status>(`${environment.backendHost}/amal/Status/${id}`, Status);
  }


  getStatus() {
    return this.http.get<Array<Status>>(`${environment.backendHost}/amal/Status`);//8082
  }
}
