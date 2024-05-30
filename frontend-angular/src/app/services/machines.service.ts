import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Composant, HistoriqueComposant, Machine, Modules, TypeMachine} from "../model/composants.model";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MachinesService {

  constructor(private http:HttpClient) { }


  public getComposants(machineId : string):Observable<Array<Composant>>{
    return this.http.get<Array<Composant>>(`${environment.backendHost}/amal/composants/machine-details/${machineId}`);//8082
  }
  public getComposantsSetting(machineId : string):Observable<Array<Composant>>{
    return this.http.get<Array<Composant>>(`${environment.backendHost2}/amal/composants/machine-details/${machineId}`);//8082
  }
  deleteMachine(machineId: string) {
    return this.http.delete<void>(`${environment.backendHost2}/amal/machines/${machineId}`);
  }


  getMachines() {
    return this.http.get<Array<Machine>>(`${environment.backendHost2}/amal/machines`);
  }



  getComposantsMonitoring(machineId : string):Observable<Array<Composant>>{
    return this.http.get<Array<Composant>>(`${environment.backendHost}/amal/composants/machine-details/${machineId}`);//8082

  }

  getTypeMachines() {
    return this.http.get<Array<TypeMachine>>(`${environment.backendHost2}/amal/typemachines`);

  }
  partialUpdateTypeMachine(typeMachineId: string, typeMachine: Partial<TypeMachine>): Observable<TypeMachine> {
    return this.http.patch<TypeMachine>(`${environment.backendHost2}/amal/typemachines/${typeMachineId}`, typeMachine);
  }


  deleteTypeMachine(machinetypeId: string) {
    return this.http.delete<Array<TypeMachine>>(`${environment.backendHost2}/amal/typemachines/${machinetypeId}`);
  }

  getModules() {
    return this.http.get<Array<Modules>>(`${environment.backendHost2}/amal/Module`);
  }

  deleteModules(id: string) {
    return this.http.delete<Array<Modules>>(`${environment.backendHost2}/amal/Module/${id}`);
  }

  partialUpdateModules(id: string, module: Partial<Modules>): Observable<Modules> {
    return this.http.patch<Modules>(`${environment.backendHost2}/amal/Module/${id}`, module);
  }
}
