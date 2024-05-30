export interface Machine {
  machineId: string; // Assuming UUID is represented as a string
  typeId: string; // TypeMachine Id
  type:string;
  moduleId: string;
  module:string;
  name: string;
  code: string;
  isonline: boolean;
  description: string;
  ipAddress: string;
  currentStatus: string;
  lastStatus: string;
  composants: Composant[];
}
export interface TypeMachine {
  machinetypeId: string; // Assuming UUID is represented as a string
  name: string;
  code:string;
}
export interface Modules {
  id: string; // Assuming UUID is represented as a string
  name: string;
  description:string;
}

export interface Status {
  id: string; // Assuming UUID is represented as a string
  value: string;
  description:string;
}



export interface Composant {
  isDeleted: boolean;
  id: string;
  name: string;
  statusId: number;
  value: string;
  lastStatusChangeTime: Date;
  lastStatus: string;
  currentStatus: string;
  code: string;
  machineId: string;
  componentTypeId: number;
  model: string;
  composantCreatedDate: Date;
  composantModifiedDate: Date;
  machineName: string; // Add the machineName property
}


export interface HistoriqueComposant {
  id: string;
  composantId: string; // UUIDs can be represented as strings in TypeScript
  status: string;
  value: string;
  datetime: Date; // LocalDateTime can be represented as Date in TypeScript
}

export enum TypeStatus {
  WORKING,
  WARNING,
  FAULTY,
  UNKNOWN,
  DISCONNECTED
}
