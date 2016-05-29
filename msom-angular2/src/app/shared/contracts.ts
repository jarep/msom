export interface ProcessingSystem{
    id: string;
    name: string;
    taskDispatchers : TaskDispatcher []
}
export interface TaskDispatcher{
    id: string;
    name: string;
    processingUnits : ProcessingUnit []
}
export interface ProcessingUnit{
    id: string;
    name: string;
    cores : number;
    efficiency : number;
    availableTypes : string[]
    tasks : Task[]
}
export interface Task{
    id: string;
    type: string;
    processed : number;
    totalWaitingTime : number;
    totalProcessingTime :number;
    processingCount : number;
    inProgress: boolean;
}