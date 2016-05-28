export interface TaskDispatcher{
    id: string;
    name: string;
    processingUnits : ProcessingUnit []
}
export interface ProcessingUnit{
    id: string;
    cores : number;
    efficiency : number;
    availableTypes : AvailableType[]
    tasks : Task[]
}
export interface AvailableType{
    name: string;
}
export interface Task{
    id: string;
    type: string;
    processed : number;
    totalWaitingTime : number;
    totalProcessingTime :number;
    processingCount : number;
    isProgress: boolean;
}